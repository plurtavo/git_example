package mx.com.prosa.nabhi.jse.business.impl;

import mx.com.prosa.nabhi.jse.business.ITransactionalService;
import mx.com.prosa.nabhi.jse.core.adp.Dispensed;
import mx.com.prosa.nabhi.jse.core.database.JournalOperation;
import mx.com.prosa.nabhi.jse.core.database.memory.atm.ATMSearch;
import mx.com.prosa.nabhi.jse.core.i8583.manager.TransactionManagement;
import mx.com.prosa.nabhi.jse.core.i8583.misc.ISOFormatUtils;
import mx.com.prosa.nabhi.jse.core.i8583.reversal.Reversal;
import mx.com.prosa.nabhi.jse.core.srh.SurchargeFinder;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.GeneralException;
import mx.com.prosa.nabhi.misc.exception.TransactionException;
import mx.com.prosa.nabhi.misc.exception.sanitize.SanitazeException;
import mx.com.prosa.nabhi.misc.model.adp.BillsModel;
import mx.com.prosa.nabhi.misc.model.iso.ATMResponseModel;
import mx.com.prosa.nabhi.misc.model.jdb.Surcharge;
import mx.com.prosa.nabhi.misc.model.jdb.personalized.ATDTransactional;
import mx.com.prosa.nabhi.misc.model.jse.request.*;
import mx.com.prosa.nabhi.misc.model.jse.response.GenericProcess;
import mx.com.prosa.nabhi.misc.model.srh.RequestSurcharge;
import mx.com.prosa.nabhi.misc.util.Sanitizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.gonet.iso8583.constants.ReversalCodes;
import us.gonet.iso8583.constants.atm.FromAccount;
import us.gonet.iso8583.constants.atm.TranCodes;
import us.gonet.token.Token;
import us.gonet.token.Tokens;
import us.gonet.utils.STMTDecoder;
import us.gonet.utils.TokenDecoder;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TransactionalService implements ITransactionalService {

    private static final Logger LOG = LoggerFactory.getLogger( TransactionalService.class );

    private static final String WITHD_ERROR = "Error en la autorizacion del retiro";
    private static final String INQ_ERROR = "Error en la autorizacion de la consulta de Saldo";
    private static final String LTRX_ERROR = "Error en la autorizacion de la consulta de Movimientos";
    private static final String NCH_ERROR = "Error en la autorizacion del cambio de NIP";
    private static final String GES_ERROR = "Error en la autorizacion de la recarga de saldo";
    private static final String PAY_TDC_ERROR = "Error en la autorizacion del pago de TDC";
    private static final String TRANSFER_ERROR = "Error en la autorizacion de la transferencia";
    private static final String COMMI_ERROR = "Error al obtener la comision";

    private final TransactionManagement transactionManagement;
    private final ISOFormatUtils isoUtils;
    private final JournalOperation journalOperation;
    private final SurchargeFinder finder;
    private final Dispensed dispensed;
    private final Sanitizer sanitizer;
    private final ATMSearch atmSearch;
    private Reversal reversal;

    @Autowired
    public TransactionalService( TransactionManagement transactionManagement, ISOFormatUtils isoUtils, JournalOperation journalOperation, SurchargeFinder finder, Dispensed dispensed, Sanitizer sanitizer, ATMSearch atmSearch ) {
        this.transactionManagement = transactionManagement;
        this.isoUtils = isoUtils;
        this.journalOperation = journalOperation;
        this.finder = finder;
        this.dispensed = dispensed;
        this.sanitizer = sanitizer;
        this.atmSearch = atmSearch;
    }

    @Autowired
    public void setReversal( Reversal reversal ) {
        this.reversal = reversal;
    }

    @Override
    public BillsModel cashWithAuth( CashWithdrawalModel generic ) throws TransactionException {
        try {
            ATDTransactional atd = atmSearch.searchTransactional( generic.getTermId() );
            journalOperation.writeAcceptSurcharge( atd, generic, TranCodes.WITHDRAWAL );
            journalOperation.waitAuth( atd, TranCodes.WITHDRAWAL );
            ATMResponseModel response = transactionManagement.performTransaction( atd, generic, TranCodes.WITHDRAWAL );
            BillsModel billsModel = isFeeUseCreditLine( response, generic.getTipoCuenta(), dispensed.dispenseFourUnits( generic.getTermId(), Integer.parseInt( generic.getCashWithAmount() ) ).getBills() );
            journalOperation.succesfulAuth( atd, TranCodes.WITHDRAWAL, billsModel.getBills() );
            return billsModel;
        } catch ( ATMException | TransactionException e ) {
            writeErrorJournal( e, generic, WITHD_ERROR );
            throw new TransactionException( e.getError() );
        }
    }

    @Override
    public void feeUseCreditDecline( CashWithdrawalModel generic ) {
        reversal.makeReversal( generic.getTermId(), 0, ReversalCodes.CUSTOMER_CANCELLATION );
    }

    @Override
    public Generic getCommission( AtmInfo generic ) throws TransactionException, ATMException {
        try {
            generic.setIp( sanitizer.sanitize( generic.getIp() ) );
            generic.setTrack( sanitizer.sanitize( generic.getTrack() ) );
            generic.setTransactionCode( sanitizer.sanitize( generic.getTransactionCode() ) );
            RequestSurcharge srh = new RequestSurcharge();
            srh.setIp( generic.getIp() );
            srh.setTermId( generic.getTermId() );
            srh.setTrack( generic.getTrack() );
            srh.setTransactionCode( generic.getTransactionCode() );
            Surcharge surcharge = finder.getSurcharge( srh );
            Generic surchargeResponse = new Generic();
            surchargeResponse.setTxCommission( surcharge.getSurcharges() );
            return surchargeResponse;
        } catch ( SanitazeException e ) {
            LOG.error( e.getMessage() );
            LOG.error( COMMI_ERROR );
            Generic surchargeResponse = new Generic();
            surchargeResponse.setTxCommission( "00.00" );
            return surchargeResponse;
        }
    }


    @Override
    public Map < String, String > balInquiryAuth( Generic generic ) throws TransactionException {
        try {
            ATDTransactional atd = atmSearch.searchTransactional( generic.getTermId() );
            journalOperation.writeAcceptSurcharge( atd, generic, TranCodes.BALANCE_INQUIRY );
            journalOperation.waitAuth( atd, TranCodes.BALANCE_INQUIRY );
            ATMResponseModel atmResponseModel = transactionManagement.performTransaction( atd, generic, TranCodes.BALANCE_INQUIRY );
            double oldBalance = Double.parseDouble( atmResponseModel.getMessage().getDataElements().get( 43 ).getContentField().substring( 13, 25 ) ) / 100;
            DecimalFormat f = new DecimalFormat( "$#,###,##0.00" );
            String cuenta = atmResponseModel.getMessage().getDataElements().get( 101 ).getContentField();
            String monto = f.format( oldBalance );
            SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy" );
            String date = sdf.format( new Date() );
            Map < String, String > datos = new HashMap <>();
            String numCuenta = isoUtils.obfuscateCardNumber( cuenta );
            datos.put( "monto", monto );
            datos.put( "fecha", date );
            datos.put( "numCuenta", numCuenta );
            journalOperation.succesfulAuth( atd, TranCodes.BALANCE_INQUIRY, "" );
            return datos;
        } catch ( ATMException | TransactionException e ) {
            writeErrorJournal( e, generic, INQ_ERROR );
            throw new TransactionException( e.getError() );
        }
    }


    @Override
    public String listTrx( Generic generic ) throws TransactionException {
        try {
            ATDTransactional atd = atmSearch.searchTransactional( generic.getTermId() );
            journalOperation.writeAcceptSurcharge( atd, generic, TranCodes.STATEMENT_PRINT );
            journalOperation.waitAuth( atd, TranCodes.STATEMENT_PRINT );
            ATMResponseModel atmResponseModel = transactionManagement.performTransaction( atd, generic, TranCodes.STATEMENT_PRINT );
            List < String > trxList = new STMTDecoder().decode( atmResponseModel.getMessage().getDataElements().get( 124 ).getContentField() );
            journalOperation.succesfulAuth( atd, TranCodes.STATEMENT_PRINT, "" );
            return trxList.toString();
        } catch ( ATMException | TransactionException e ) {
            writeErrorJournal( e, generic, LTRX_ERROR );
            throw new TransactionException( e.getError() );
        }
    }


    @Override
    public GenericProcess changePin( ChangeNipModel generic ) throws TransactionException {
        try {
            ATDTransactional atd = atmSearch.searchTransactional( generic.getTermId() );
            journalOperation.writeAcceptSurcharge( atd, generic, TranCodes.PIN_CHANGE );
            journalOperation.waitAuth( atd, TranCodes.PIN_CHANGE );
            transactionManagement.performTransaction( atd, generic, TranCodes.PIN_CHANGE );
            journalOperation.succesfulAuth( atd, TranCodes.PIN_CHANGE, "" );
            return new GenericProcess();
        } catch ( ATMException | TransactionException e ) {
            writeErrorJournal( e, generic, NCH_ERROR );
            throw new TransactionException( e.getError() );
        }
    }

    @Override
    public GenericProcess genericSale( GenericSaleModel generic ) throws TransactionException {
        try {
            ATDTransactional atd = atmSearch.searchTransactional( generic.getTermId() );
            journalOperation.writeAcceptSurcharge( atd, generic, TranCodes.GENERIC_SALE );
            journalOperation.waitAuth( atd, TranCodes.GENERIC_SALE );
            transactionManagement.performTransaction( atd, generic, TranCodes.GENERIC_SALE );
            journalOperation.succesfulAuth( atd, TranCodes.GENERIC_SALE, "" );
            return new GenericProcess();
        } catch ( ATMException | TransactionException e ) {
            writeErrorJournal( e, generic, GES_ERROR );
            throw new TransactionException( e.getError() );
        }
    }

    @Override
    public GenericProcess paymentOfTDC( GenericWithToAccount toAccount ) throws TransactionException {
        try {
            ATDTransactional atd = atmSearch.searchTransactional( toAccount.getTermId() );
            journalOperation.writeAcceptSurcharge( atd, toAccount, TranCodes.PAYMENT_ACCOUNTS );
            journalOperation.waitAuth( atd, TranCodes.PAYMENT_ACCOUNTS );
            transactionManagement.performTransaction( atd, toAccount, TranCodes.PAYMENT_ACCOUNTS );
            journalOperation.succesfulAuth( atd, TranCodes.PAYMENT_ACCOUNTS, "" );
            return new GenericProcess();
        } catch ( ATMException | TransactionException e ) {
            writeErrorJournal( e, toAccount, PAY_TDC_ERROR );
            throw new TransactionException( e.getError() );
        }
    }

    @Override
    public GenericProcess transfer( GenericWithToAccount toAccount ) throws TransactionException {
        try {
            ATDTransactional atd = atmSearch.searchTransactional( toAccount.getTermId() );
            journalOperation.writeAcceptSurcharge( atd, toAccount, TranCodes.CARDHOLDER_ACCOUNTS_TRANSFER );
            journalOperation.waitAuth( atd, TranCodes.CARDHOLDER_ACCOUNTS_TRANSFER );
            transactionManagement.performTransaction( atd, toAccount, TranCodes.CARDHOLDER_ACCOUNTS_TRANSFER );
            journalOperation.succesfulAuth( atd, TranCodes.CARDHOLDER_ACCOUNTS_TRANSFER, "" );
            return new GenericProcess();
        } catch ( ATMException | TransactionException e ) {
            writeErrorJournal( e, toAccount, TRANSFER_ERROR );
            throw new TransactionException( e.getError() );
        }
    }


    private void writeErrorJournal( GeneralException e, Generic generic, String operation ) {
        journalOperation.write( generic.getTermId(), operation + " : " + e.getMessage() );
    }


    private BillsModel isFeeUseCreditLine( ATMResponseModel response, String formAccount, String bills ) {
        BillsModel billsModel = new BillsModel();
        billsModel.setBills( bills );
        FromAccount credit = FromAccount.CREDIT_ACCOUNT;
        if ( credit.getValue().equals( formAccount ) ) {
            TokenDecoder tokenDecoder = new TokenDecoder( response.getMessage().getDataElements().get( 125 ).getContentField().getBytes() );
            Tokens tokens = tokenDecoder.getTokens();
            Token token30 = tokens.getToken( "30" );
            Token tokenB1 = tokens.getToken( "B1" );
            if ( token30 != null && tokenB1 != null ) {
                billsModel.setFeeCreditLine( true );
                billsModel.setFee( String.format( "%.2f", ( Double.parseDouble( token30.getToken().get( 1 ).getContentField().substring( 40, 59 ) ) ) / 100 ) );
                int b1l = Integer.parseInt( tokenB1.getToken().get( 1 ).getContentField().substring( 0, 3 ) ) - 5;
                billsModel.setIssuing( tokenB1.getToken().get( 1 ).getContentField().substring( 8, b1l ) );
            }
        } else {
            billsModel.setFeeCreditLine( false );
        }
        return billsModel;
    }
}
