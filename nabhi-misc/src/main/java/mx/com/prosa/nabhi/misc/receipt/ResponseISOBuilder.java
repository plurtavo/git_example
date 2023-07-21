package mx.com.prosa.nabhi.misc.receipt;

import mx.com.prosa.nabhi.misc.exception.ReceiptException;
import mx.com.prosa.nabhi.misc.model.iso.ATMRequestModel;
import mx.com.prosa.nabhi.misc.model.iso.ATMResponseModel;
import org.springframework.stereotype.Component;
import us.gonet.iso8583.constants.atm.ResponseCodes;
import us.gonet.serializable.data.ISO;

@Component
public class ResponseISOBuilder {


    public ATMResponseModel genericResponse( ATMRequestModel wm, ISO r0210, IReceipt receipt ) {
        ATMResponseModel response = new ATMResponseModel();
        response.setMessage( r0210 );
        if ( r0210.getDataElements().get( 38 ).getContentField().equals( ResponseCodes.APPROVED.getValue() ) ) {
            try {
                receipt.createReceipt( wm, r0210, false );
                response.setReceipt( receipt.createReceipt( wm, r0210, true ).getTicket() );
            } catch ( ReceiptException e ) {
                response.setReceipt( "ERROR: " + e.getMessage() );
            }
        }
        return response;
    }
}
