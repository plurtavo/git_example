package mx.com.prosa.nabhi.iso.core.builder;

import mx.com.prosa.nabhi.iso.core.builder.token.TokenID;
import mx.com.prosa.nabhi.iso.core.sockets.IISOController;
import mx.com.prosa.nabhi.misc.constants.EntryMode;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.iso.ISO8583Exception;
import mx.com.prosa.nabhi.misc.model.iso.ATMRequestModel;
import mx.com.prosa.nabhi.misc.model.iso.ATMResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.gonet.iso8583.constants.atm.TranCodes;
import us.gonet.iso8583.message.Request0200;
import us.gonet.serializable.data.ISO;
import us.gonet.token.Tokens;
import us.gonet.token.emv.data.DataB4;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProcessingTransaction {

    private final ISOBuilder isoBuilder;
    private final ResponseISOBuilder2 responseISOBuilder2;
    private static final Logger LOG = LoggerFactory.getLogger( ProcessingTransaction.class );

    @Autowired
    public ProcessingTransaction( ISOBuilder isoBuilder, ResponseISOBuilder2 responseISOBuilder2 ) {
        this.isoBuilder = isoBuilder;
        this.responseISOBuilder2 = responseISOBuilder2;
    }

    public ATMResponseModel sendTransaction( TranCodes tranCode, int traceNumber, String exchangeNode, ATMRequestModel atmRequestModel, IISOController transactionController, String ipk ) throws ISO8583Exception {
        List < TokenID > tokenIDS = new ArrayList <>();
        if ( atmRequestModel.getEmv() == null ) {
            atmRequestModel.setEmv( new LinkedHashMap <>() );
        }
        validateB4Data( atmRequestModel.getEmv(), atmRequestModel.getEntryMode() );
        if ( atmRequestModel.getEmv() != null && atmRequestModel.getEntryMode().startsWith( EntryMode.CHIP.getValue() ) ) {
            tokenIDS.add( TokenID.TOKEN_B2 );
            tokenIDS.add( TokenID.TOKEN_B3 );
        }
        tokenIDS.add( TokenID.TOKEN_B4 );
        switch ( tranCode ) {
            case WITHDRAWAL:
            case STATEMENT_PRINT:
            case BALANCE_INQUIRY:
            case PAYMENT_ACCOUNTS:
            case CARDHOLDER_ACCOUNTS_TRANSFER:
                break;
            case GENERIC_SALE:
                tokenIDS.add( TokenID.TOKEN_P1 );
                tokenIDS.add( TokenID.TOKEN_QV );
                break;
            case PIN_CHANGE:
                tokenIDS.add( TokenID.TOKEN_06 );
                break;
            case PAYMENT_SERVICE:
                tokenIDS.add( TokenID.TOKEN_RC );
                break;
            default:
                throw new ISO8583Exception( CatalogError.INVALID_TRANSACTION );
        }
        Map < String, String > dataElements = isoBuilder.buildDataElement( tranCode, atmRequestModel, traceNumber, exchangeNode, ipk );
        dataElements = isoBuilder.addTokens( dataElements, new Tokens(), atmRequestModel, tokenIDS );
        ISO r0200 = new Request0200( dataElements ).getIso();
        ISO r0210 = transactionController.send( r0200 );
        if ( !r0210.isTimeOut() ) {
            return responseISOBuilder2.genericResponse( r0210 );
        } else {
            if ( LOG.isDebugEnabled() ) {
                LOG.debug( String.format( "Timeout para la transacción con secuencia %s", r0200.getDataElements().get( 10 ) ) );
            }
            throw responseISOBuilder2.responseReceivedToLate();
        }
    }


    private void validateB4Data( Map < String, String > emv, String entryMode ) {
        emv.put( DataB4.PT_SRV_ENTRY_MDE.getTag(), entryMode );
        emv.put( DataB4.TERM_ENTRY_CAP.getTag(), "5" );
        emv.put( DataB4.LAST_EMV_STAT.getTag(), ( entryMode.equals( "051" ) ) ? "1" : "0" );
        emv.put( DataB4.DATA_SUSPECT.getTag(), "1" );
        emv.put( DataB4.APPL_PAN_SEQ_NUM.getTag(), ( emv.get( "5F34" ) != null ) ? emv.get( "5F34" ) : "00" );
        emv.put( DataB4.APPRVD_RC.getTag(), "00" );
        emv.put( DataB4.UNUSED.getTag(), "0000" );
        emv.put( DataB4.RSN_ONL_CDE.getTag(), "1508" );
        emv.put( DataB4.ISO_RC_IND.getTag(), " " );
    }
}
