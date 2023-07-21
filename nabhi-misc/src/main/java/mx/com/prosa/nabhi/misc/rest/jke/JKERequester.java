package mx.com.prosa.nabhi.misc.rest.jke;

import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.rest.RestExchangeException;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import mx.com.prosa.nabhi.misc.model.jke.KeyRequest;
import mx.com.prosa.nabhi.misc.rest.RequestFactory;
import mx.com.prosa.nabhi.misc.util.Sanitizer;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

@Component
public class JKERequester {

    private final RequestFactory< ? > jkeRest;
    private final ModelMapper mapper;
    private final Sanitizer sanitize;
    @Value( "${nabhi.jke.url}" )
    private String urlBase;
    private static final Logger LOG = LoggerFactory.getLogger( JKERequester.class );

    @Autowired
    public JKERequester( ModelMapper mapper, Sanitizer sanitize ) {
        this.mapper = mapper;
        this.sanitize = sanitize;
        jkeRest = new RequestFactory <>();
    }

    public String translatePinBlock( KeyRequest zpk, String url, String nodeName ) throws RestExchangeException {
        if ( LOG.isDebugEnabled() ){
            LOG.debug( String.format( "Enviado solicitud de traslado de pin block al nodo %s del servicio JKE", nodeName ) );
        }
        ResponsePayload< ? > response = ( ResponsePayload < ? > ) jkeRest.request( HttpMethod.POST, buildURLForAnyService( url, nodeName ), ResponsePayload.class, zpk );
        return mapper.map( response.getBody(), String.class );
    }

    public String exchangeTPK( KeyRequest tpk, String url, String nodeName ) throws RestExchangeException {
        if ( LOG.isDebugEnabled() ){
            LOG.debug( String.format( "Enviado solicitud de intercambio de llaves 3DES al nodo %s del servicio JKE para el cajero %s", nodeName, tpk.getAtmRemote() ) );
        }
        ResponsePayload< ? > response = ( ResponsePayload < ? > ) jkeRest.request( HttpMethod.POST, buildURLForAnyService( url, nodeName ), ResponsePayload.class, tpk );
        return mapper.map( response.getBody(), String.class );
    }

    public String socket( String url, String op, String node ) throws RestExchangeException {
        if ( LOG.isDebugEnabled() ){
            LOG.debug( String.format( "Enviado operación %s al socket %s tipo JKE", op, node ) );
        }
        ResponsePayload< ? > response = ( ResponsePayload < ? > ) jkeRest.request( HttpMethod.GET, buildURLForAnyService( url, op, node ), ResponsePayload.class );
        return mapper.map( response.getBody(), String.class );
    }

    private String buildURLForAnyService( String service, Object... params ) throws RestExchangeException {
        UriTemplate template = new UriTemplate( urlBase + service );
        return checkList( template.expand( params ) );
    }

    private String checkList( URI url ) throws RestExchangeException {
        if ( sanitize.urlWhiteList( url ) ) {
            String uri = String.valueOf( url );
            uri = uri.replace( "%5E", "^" );
            return uri.replace( "%20", " " );
        } else {
            throw new RestExchangeException( CatalogError.INVALID_URL );
        }
    }

}
