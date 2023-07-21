package mx.com.prosa.nabhi.jse.core.i8583.manager;

import mx.com.prosa.nabhi.jse.core.database.JournalOperation;
import mx.com.prosa.nabhi.jse.core.database.memory.atm.ATMSearch;
import mx.com.prosa.nabhi.jse.core.database.memory.bin.BinarySearch;
import mx.com.prosa.nabhi.jse.core.i8583.misc.ATMRequestBuilder;
import mx.com.prosa.nabhi.jse.core.i8583.misc.ProcessingTransaction;
import mx.com.prosa.nabhi.jse.core.i8583.misc.ResponseCode;
import mx.com.prosa.nabhi.misc.domain.complete.entity.APCEntity;
import mx.com.prosa.nabhi.misc.domain.complete.repository.APCRepository;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.ErrorCode;
import mx.com.prosa.nabhi.misc.exception.TransactionException;
import mx.com.prosa.nabhi.misc.exception.iso.ISO8583Exception;
import mx.com.prosa.nabhi.misc.model.iso.ATMRequestModel;
import mx.com.prosa.nabhi.misc.model.iso.ATMResponseModel;
import mx.com.prosa.nabhi.misc.model.jdb.APC;
import mx.com.prosa.nabhi.misc.model.jdb.personalized.ATDTransactional;
import mx.com.prosa.nabhi.misc.model.jse.request.Generic;
import mx.com.prosa.nabhi.misc.model.jse.request.GenericWithToAccount;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.gonet.iso8583.constants.atm.TranCodes;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class TransactionManagement {

    private final ProcessingTransaction processingTransaction;
    private final ATMRequestBuilder atmRequestBuilder;
    private final ATMSearch atmSearch;
    private final APCRepository apcRepository;
    private final ModelMapper mapper;
    private final BinarySearch binarySearch;
    private final JournalOperation journalOperation;
    private static final Logger LOG = LoggerFactory.getLogger( TransactionManagement.class );

    @Autowired
    public TransactionManagement( ProcessingTransaction processingTransaction, ATMRequestBuilder atmRequestBuilder, ATMSearch atmSearch, APCRepository apcRepository, ModelMapper mapper, BinarySearch binarySearch, JournalOperation journalOperation ) {
        this.processingTransaction = processingTransaction;
        this.atmRequestBuilder = atmRequestBuilder;
        this.atmSearch = atmSearch;
        this.apcRepository = apcRepository;
        this.mapper = mapper;
        this.binarySearch = binarySearch;
        this.journalOperation = journalOperation;
    }

    public ATMResponseModel performTransaction( ATDTransactional atd, Generic e, TranCodes tranCode ) throws TransactionException {
        try {
            final String ON_US = "onUsFlag";
            String onUsFlag = "1";
            if ( !atd.getIdf().getFiid().equals( "PROS" ) ) {
                onUsFlag = isOnUs( e.getTrack(), atd.getIdf().getFiid(), tranCode );
            }
            APC apc = findAPC( atd, tranCode, e, toAccount( e ) );
            ATMRequestModel atmRequestModel = atmRequestBuilder.build( e, atd, apc, tranCode.getValue() );
            Map < String, String > extra;
            if ( atmRequestModel.getExtra() != null ) {
                extra = atmRequestModel.getExtra();
            } else {
                extra = new LinkedHashMap <>();
            }
            extra.put( ON_US, onUsFlag );
            atmRequestModel.setExtra( extra );
            ATMResponseModel response = processingTransaction.sendTransaction( atmRequestModel );
            if ( response.getMessage().getDataElements().get( 38 ).getContentField().equals( "00" ) ) {
                saveAuthorizationInfo( atd, response );
                return response;
            } else {
                String code = response.getMessage().getDataElements().get( 38 ).getContentField();
                atd.setLastTrx( "" );
                atd.setReceipt( "" );
                atmSearch.saveTransactional( atd );
                journalOperation.write( atd.getTerminalId(), "Código de rechazo: " + ResponseCode.geValueFromCode( code ) );
                String reason = ResponseCode.getMessageFromCode( code );
                if ( LOG.isDebugEnabled() ) {
                    LOG.debug( String.format( "Transacción %s declinada para %s razón %s", tranCode.name(), atd.getTerminalId(), reason ) );
                }
                CatalogError errors = CatalogError.NO_ERROR;
                ErrorCode errorIso = errors.findByCode( ( 1000 + Integer.parseInt( code ) ) );
                throw new TransactionException( errorIso );
            }
        } catch ( ISO8583Exception | ATMException ex ) {
            throw new TransactionException( ex.getError() );
        }
    }

    public ATMRequestModel buildRequestModel( ATDTransactional atd, Generic e, TranCodes tranCode ) throws TransactionException, ATMException {
        APC apc = findAPC( atd, tranCode, e, toAccount( e ) );
        return atmRequestBuilder.build( e, atd, apc, tranCode.getValue() );
    }

    private APC findAPC( ATDTransactional atd, TranCodes code, Generic e, String toAccount ) throws TransactionException {
        Optional < APCEntity > oa = apcRepository.findByFiidAndTranCodeAndFormAcctAndToAcct( atd.getIdf().getFiid(), code.getValue(), e.getTipoCuenta(), toAccount );
        if ( oa.isPresent() ) {
            return mapper.map( oa.get(), APC.class );
        } else {
            if ( LOG.isErrorEnabled() ) {
                LOG.info( String.format( "No se encuentra APC para esta transacción %s %s %s %s", atd.getIdf().getFiid(), code.getValue(), e.getTipoCuenta(), toAccount ) );
            }
            throw new TransactionException( CatalogError.APC_NOT_FOUND );
        }
    }

    public void saveAuthorizationInfo( ATDTransactional atd, ATMResponseModel atmResponseModel ) {
        atd.setLastTrx( atmResponseModel.getMessage().getMessage() );
        atd.setReceipt( atmResponseModel.getReceipt() );
        atmSearch.saveTransactional( atd );
    }

    private String isOnUs( String track2, String acquirerFiid, TranCodes tranCode ) throws TransactionException {
        String pan = track2.substring( 0, track2.indexOf( '=' ) );
        String issuingFiid = binarySearch.search( pan.length(), 11, pan );
        switch ( tranCode ) {
            case WITHDRAWAL:
            case BALANCE_INQUIRY:
            case GENERIC_SALE:
                if ( issuingFiid.equals( acquirerFiid ) ) {
                    return "1";
                } else {
                    return "0";
                }
            case PAYMENT_ACCOUNTS:
            case PAYMENT_SERVICE:
            case CARDHOLDER_ACCOUNTS_TRANSFER:
            case STATEMENT_PRINT:
            case PIN_CHANGE:
                if ( !issuingFiid.equals( acquirerFiid ) ) {
                    if ( LOG.isErrorEnabled() ) {
                        LOG.error( String.format( "Transacción no permitida %s %s", issuingFiid, acquirerFiid ) );
                    }
                    throw new TransactionException( CatalogError.ONLY_ON_US );
                }
                return "1";
            default:
                if ( LOG.isErrorEnabled() ) {
                    LOG.error( String.format( "Transacción no soportada %s", tranCode.name() ) );
                }
                throw new TransactionException( CatalogError.INVALID_TRAN_CODE );
        }
    }

    private String toAccount( Generic e ) {
        if ( e instanceof GenericWithToAccount ) {
            return ( ( GenericWithToAccount ) e ).getToAccount();
        } else {
            return "00";
        }
    }
}
