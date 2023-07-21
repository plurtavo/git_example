package mx.com.prosa.nabhi.misc.util;

import mx.com.prosa.nabhi.misc.model.jke.exchange.KeyExchangeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KeyResponseBuilder {

    private ISOUtils utilities;

    @Autowired
    public KeyResponseBuilder( ISOUtils utilities ) {
        this.utilities = utilities;
    }

    public KeyExchangeResponse build( String message ) {
        String seq;
        String clazz = utilities.extract( message );
        message = utilities.scanFieldSeparator( message );
        String err = utilities.extract( message );
        message = utilities.scanFieldSeparator( message );
        if ( err.equals( "0" ) ) {
            String encryptType = utilities.extract( message );
            message = utilities.scanFieldSeparator( message );
            String tpkLength = utilities.extract( message ).substring( 0, 3 );
            String tpk = utilities.extract( message ).substring( 3 );
            tpk = utilities.formatKey( tpk, tpkLength );
            message = utilities.scanFieldSeparator( message );
            seq = utilities.extract( message );
            return new KeyExchangeResponse( clazz, err, encryptType, tpk, seq );
        } else {
            message = utilities.scanFieldSeparator( message );
            message = utilities.scanFieldSeparator( message );
            seq = utilities.extract( message );
            return new KeyExchangeResponse( clazz, err, "", "", seq );
        }

    }
}