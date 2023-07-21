package mx.com.prosa.nabhi.jse.core.i8583.reversal;

import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.iso.ISO8583Exception;
import mx.com.prosa.nabhi.misc.exception.rest.RestExchangeException;
import mx.com.prosa.nabhi.misc.model.iso.ATMResponseModel;
import mx.com.prosa.nabhi.misc.model.reversal.ATMReversalModel;
import mx.com.prosa.nabhi.misc.rest.iso.ISORequester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.gonet.iso8583.constants.MessageTypes;
import us.gonet.iso8583.constants.ReversalCodes;
import us.gonet.iso8583.constants.atm.ResponseCodes;
import us.gonet.iso8583.constants.atm.TranCodes;
import us.gonet.iso8583.message.Reversal0420;
import us.gonet.serializable.data.ISO;
import us.gonet.utils.DecodeISO8583;


@Component
public class ReversalHandler {

    private static final MessageTypes response = MessageTypes.FINALCIAL_RESPONSE_MSG;
    private static final TranCodes withdrawal = TranCodes.WITHDRAWAL;
    private static final ResponseCodes approved = ResponseCodes.APPROVED;
    private final ISORequester isoRequester;

    @Autowired
    public ReversalHandler( ISORequester isoRequester ) {
        this.isoRequester = isoRequester;
    }

    ATMResponseModel sendMessage( ATMReversalModel arm, String nodeName ) throws ISO8583Exception {
        try {
            ISO i = new DecodeISO8583( arm.getMessage() ).getIso();
            if ( !i.getHeader().get( 6 ).getContentField().equals( response.getValue() ) ) {
                throw new ISO8583Exception( CatalogError.REVERSAL_TYPE_MESSAGE );
            }
            TranCodes tranCode = TranCodes.getValue( i.getDataElements().get( 2 ).getContentField().substring( 0, 2 ) );
            if ( tranCode != withdrawal ) {
                throw new ISO8583Exception( CatalogError.REVERSAL_TRAN_CODE );
            }
            if ( !approved.getValue().equals( i.getDataElements().get( 38 ).getContentField() ) ) {
                throw new ISO8583Exception( CatalogError.REVERSAL_AP );
            }
            ReversalCodes reversalCodes = ReversalCodes.RESERVED_U4;
            for ( ReversalCodes r : ReversalCodes.values() ) {
                if ( r.getValue().equals( arm.getReversalCode() ) ) {
                    reversalCodes = r;
                }
            }
            ISO r0420 = new Reversal0420( i, reversalCodes, arm.getDispensedAmount() ).getIso();
            ISO r0430 = isoRequester.sendReversal( r0420, "/sendReversal/{nodeName}", nodeName );
            if ( !r0430.isTimeOut() ) {
                return handlerResponse( r0430 );
            } else {
                throw new ISO8583Exception( CatalogError.RESPONSE_TIMEOUT );
            }
        } catch ( RestExchangeException e ) {
            throw new ISO8583Exception( e.getError() );
        }
    }

    private ATMResponseModel handlerResponse( ISO r0430 ) {
        ATMResponseModel model = new ATMResponseModel();
        model.setMessage( r0430 );
        return model;
    }
}
