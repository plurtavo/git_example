package mx.com.prosa.nabhi.misc.receipt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.ReceiptException;
import mx.com.prosa.nabhi.misc.model.receipt.ReceiptARM;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ReceiptDefinitionLoader {

    private final ObjectMapper mapper = new ObjectMapper( new YAMLFactory() );

    public ReceiptARM getTemplate( String file ) throws ReceiptException {
        try {
            return mapper.readValue( this.getClass().getResourceAsStream( file ), ReceiptARM.class );
        } catch ( IllegalArgumentException | IOException e ) {
            throw new ReceiptException( CatalogError.RECEIPT_ERROR_1 );
        }
    }

}