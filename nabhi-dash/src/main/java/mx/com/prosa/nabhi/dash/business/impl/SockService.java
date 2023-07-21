package mx.com.prosa.nabhi.dash.business.impl;
//Cambio release/redcat

import mx.com.prosa.nabhi.dash.business.ISockService;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.rest.RestExchangeException;
import mx.com.prosa.nabhi.misc.exception.socket.SocketException;
import mx.com.prosa.nabhi.misc.rest.iso.ISORequester;
import mx.com.prosa.nabhi.misc.rest.jke.JKERequester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SockService implements ISockService {

    private static final Logger LOG = LoggerFactory.getLogger( SockService.class );
    private final ISORequester isoRequester;
    private final JKERequester jkeRequester;

    @Autowired
    public SockService( ISORequester isoRequester, JKERequester jkeRequester ) {
        this.isoRequester = isoRequester;
        this.jkeRequester = jkeRequester;
    }

    @Override
    public String sock( String app, String op, String node ) throws SocketException {
        String uri = "/client/{op}/{nodeName}";
        try {
            if ( app.equals( "ISO" ) ) {
                return isoRequester.socket( uri, op, node );
            } else if ( app.equals( "JKE" ) ) {
                return jkeRequester.socket( uri, op, node );
            } else {
                throw new SocketException( CatalogError.JKE_ISO_NO_APP );
            }
        } catch ( RestExchangeException e ) {
            if ( LOG.isErrorEnabled() ) {
                LOG.error( e.getMessage() );
            }
            throw new SocketException( e.getError() );
        }
    }
}
