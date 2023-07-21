package mx.com.prosa.nabhi.iso.core.builder;

import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.iso.ISO8583Exception;
import mx.com.prosa.nabhi.misc.model.iso.ATMResponseModel;
import org.springframework.stereotype.Component;
import us.gonet.serializable.data.ISO;

@Component
public class ResponseISOBuilder2 {


    ISO8583Exception responseReceivedToLate() {
       return new ISO8583Exception( CatalogError.ISSUER_INOPERATIVE_TIMEOUT );
    }


    ATMResponseModel genericResponse( ISO r0210 ) {
        ATMResponseModel response = new ATMResponseModel();
        response.setMessage( r0210 );
        return response;
    }
}
