package mx.com.prosa.nabhi.jse.core.adp.algorithm.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import mx.com.prosa.nabhi.misc.model.devices.cdm.Dispenser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class YAMLDefinitionLoader {


    private static final Logger LOGGER = LoggerFactory.getLogger( YAMLDefinitionLoader.class );

    private final ObjectMapper mapper = new ObjectMapper( new YAMLFactory() );

    public Dispenser getDispenser( String file ) {
        try {
            return mapper.readValue( this.getClass().getResourceAsStream( file ), Dispenser.class );
        } catch ( IllegalArgumentException | IOException e ) {
            LOGGER.error( e.getMessage() );
            return null;
        }
    }

}