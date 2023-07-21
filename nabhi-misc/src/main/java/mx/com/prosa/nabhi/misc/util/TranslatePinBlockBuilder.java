package mx.com.prosa.nabhi.misc.util;

import mx.com.prosa.nabhi.misc.model.jke.exchange.TranslatePinBlockResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TranslatePinBlockBuilder {

    private ISOUtils utilities;
    private static final Logger LOG = LoggerFactory.getLogger( TranslatePinBlockBuilder.class );

    @Autowired
    public TranslatePinBlockBuilder( ISOUtils utilities ) {
        this.utilities = utilities;
    }

    public TranslatePinBlockResponse build( String message ) {
        String seq;
        String ipk = "";
        String clazz = utilities.extract( message );
        message = utilities.scanFieldSeparator( message );
        String err = utilities.extract( message );
        if ( LOG.isTraceEnabled() ) {
            LOG.trace( err );
        }
        message = utilities.scanFieldSeparator( message );
        if ( err.equals( "0" ) ) {
            String pinBlockLen = utilities.extract( message ).substring( 0, 3 );
            if ( LOG.isTraceEnabled() ) {
                LOG.trace( pinBlockLen );
            }
            String pinBlock = utilities.extract( message ).substring( 3 );
            pinBlock = utilities.formatKey( pinBlock, pinBlockLen );
            message = utilities.scanFieldSeparator( message );

            if ( !utilities.extract( message ).equals( "" ) ) {
                String ipkLength = utilities.extract( message ).substring( 0, 3 );
                ipk = utilities.extract( message ).substring( 3 );
                ipk = utilities.formatKey( ipk, ipkLength );
            }
            message = utilities.scanFieldSeparator( message );
            seq = utilities.extract( message );
            return new TranslatePinBlockResponse( clazz, err, pinBlock, ipk, seq );
        } else {
            message = utilities.scanFieldSeparator( message );
            message = utilities.scanFieldSeparator( message );
            seq = utilities.extract( message );
            return new TranslatePinBlockResponse( clazz, err, "", "", seq );
        }

    }

}