package mx.com.prosa.nabhi.jse.core.i8583.misc;

import mx.com.prosa.nabhi.misc.constants.EntryMode;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.TransactionException;
import mx.com.prosa.nabhi.misc.model.iso.ATMRequestModel;
import mx.com.prosa.nabhi.misc.model.jdb.APC;
import mx.com.prosa.nabhi.misc.model.jdb.personalized.ATDTransactional;
import mx.com.prosa.nabhi.misc.model.jse.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.gonet.iso8583.constants.atm.FromAccount;
import us.gonet.iso8583.constants.atm.ToAccount;
import us.gonet.iso8583.constants.atm.TranCodes;
import us.gonet.utils.Utilities;

import java.util.Map;

@Component
public class ATMRequestBuilder {

    private final ISOFormatUtils isoFormatUtils;

    @Autowired
    public ATMRequestBuilder( ISOFormatUtils isoFormatUtils ) {
        this.isoFormatUtils = isoFormatUtils;
    }

    public ATMRequestModel build( Generic e, ATDTransactional atd, APC apc, String process ) throws TransactionException, ATMException {
        ATMRequestModel request = new ATMRequestModel();
        request.setTranCode( process );
        request.setFromAccount( e.getTipoCuenta() );
        request.setToAccount( ToAccount.NO_ACCOUNT.getValue() );
        if ( TranCodes.getValue(process) == TranCodes.PIN_CHANGE ){
            request.setFromAccount( FromAccount.NO_ACCOUNT.getValue() );
        }
        switch ( TranCodes.getValue( process ) ){
            case WITHDRAWAL:
                request.setAmount( ( ( CashWithdrawalModel ) e ).getCashWithAmount() );
                break;
            case GENERIC_SALE:
                request.setAmount( ( ( GenericSaleModel ) e ).getCashWithAmount() );
                break;
            case BALANCE_INQUIRY:
            case STATEMENT_PRINT:
            case PIN_CHANGE:
                request.setAmount( "00" );
                break;
            case PAYMENT_ACCOUNTS:
            case CARDHOLDER_ACCOUNTS_TRANSFER:
                request.setAmount( ( ( GenericWithToAccount ) e ).getCashWithAmount() );
                request.setToAccountP103( ( ( GenericWithToAccount ) e ).getToAccountP103() );
                request.setToAccount( ( ( GenericWithToAccount ) e ).getToAccount() );
                break;
            default:
                break;
        }
        request.setSurcharge( isoFormatUtils.surchargeValue( e.getIp(), e.getTrack(), process, e.getTermId() ) );
        request.setTrack2( e.getTrack() );
        atd.setSequenceNumber( atd.getSequenceNumber() + 1 );
        String sequenceNumber = Utilities.leftPadding( "0", 12, String.valueOf( atd.getSequenceNumber() ) );
        request.setSequenceNumber( sequenceNumber );
        request.setTermId( Utilities.rightPadding( " ", 16, atd.getTerminalId() ) );
        request.setTermOwnerName( Utilities.rightPadding( " ", 22, atd.getIdf().getName() ) );
        request.setTermCity( Utilities.rightPadding( " ", 13, atd.getCounty().getCountyName() ) );
        request.setTermState( Utilities.rightPadding( " ", 3, atd.getCounty().getState().getStateShortName() ) );
        request.setTermCountry( atd.getIdf().getCountry().getAlpha2() );
        request.setGroupAllow( isoFormatUtils.allowGroup( atd, apc ) );
        request.setCurrencyCode( atd.getIdf().getCountry().getCountryCode() );
        request.setPinBlock( e.getNip() );
        if ( process.equals( TranCodes.PIN_CHANGE.getValue() ) ) {
            request.setNewPinBlock( ( ( ChangeNipModel ) e ).getNewPin() );
            request.setNewPinBlock2( ( ( ChangeNipModel ) e ).getConfirmNewPin() );
        }
        request.setTermFiid( atd.getIdf().getFiid() );
        request.setlNet( atd.getIdf().getLogicalNet() );
        request.setTimeOffSet( isoFormatUtils.verifyUTCDifference( atd.getCounty().getState().getZone() ) );
        String discretionary = e.getTrack().substring( e.getTrack().indexOf( '=' ) + 1 );
        request.setEntryMode( entryMode( discretionary, e.getEmv() ) + "1" );
        request.setEmv( e.getEmv() );
        request.setTermType( atd.getDeviceType() );
        if ( process.equals( TranCodes.GENERIC_SALE.getValue() ) ) {
            request.setCompany( ( ( GenericSaleModel ) e ).getCompany() );
            request.setPhoneNumber( ( ( GenericSaleModel ) e ).getTelefono() );
            request.setPhoneNumber2( ( ( GenericSaleModel ) e ).getTelefono() );
        }
        request.setNodeIso( atd.getNode().getNodeName() );
        request.setNodeExchange( atd.getNodeX().getNodeName() );
        request.setAcquiringId( atd.getIdf().getAcquiringId() );
        return request;
    }


    private String entryMode( String discretionary, Map < String, String > emv ) {
        String capabilities = discretionary.substring( 4, 5 );
        if ( capabilities.equals( "2" ) || capabilities.equals( "6" ) ) {
            if ( emv != null && emv.size() > 1 ) {
                return EntryMode.CHIP.getValue();
            } else {
                return EntryMode.CHIP_ERROR.getValue();
            }

        } else {
            return EntryMode.MAGNETIC_STRIPE.getValue();
        }
    }
}
