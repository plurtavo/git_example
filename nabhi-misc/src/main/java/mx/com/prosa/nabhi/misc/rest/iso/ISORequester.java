package mx.com.prosa.nabhi.misc.rest.iso;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.rest.RestExchangeException;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import mx.com.prosa.nabhi.misc.model.iso.ATMRequestModel;
import mx.com.prosa.nabhi.misc.model.iso.ATMResponseModel;
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
import us.gonet.serializable.data.ISO;

import java.net.URI;

@Component
public class ISORequester {

    private final RequestFactory< ? > isoRest;
    private final ModelMapper mapper;
    private final Sanitizer sanitizer;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value( "${nabhi.iso.url}" )
    private String urlBase;
    private static final Logger LOG = LoggerFactory.getLogger( ISORequester.class );

    @Autowired
    public ISORequester( ModelMapper mapper, Sanitizer sanitizer ) {
        this.mapper = mapper;
        this.sanitizer = sanitizer;
        isoRest = new RequestFactory <>();
    }

    public ATMResponseModel sendMessage( ATMRequestModel request, String url, String nodeName ) throws RestExchangeException {
        if ( LOG.isDebugEnabled() ){
            LOG.debug( String.format( "Enviado mensaje de autorización a Base 24 por el nodo %s del servicio ISO", nodeName ) );
        }
        ResponsePayload < ? > response = ( ResponsePayload < ? > ) isoRest.request( HttpMethod.POST, buildURLForAnyService( url, nodeName ), ResponsePayload.class, request );
        return objectMapper.convertValue( response.getBody(), ATMResponseModel.class );
    }

    public ISO sendReversal( ISO reversal, String url, String nodeName ) throws RestExchangeException {
        if ( LOG.isDebugEnabled() ){
            LOG.debug( String.format( "Enviado mensaje de reverso a Base 24 por el nodo %s del servicio ISO", nodeName ) );
        }
        ResponsePayload < ? > response = ( ResponsePayload < ? > ) isoRest.request( HttpMethod.POST, buildURLForAnyService( url, nodeName ), ResponsePayload.class, reversal );
        return objectMapper.convertValue( response.getBody(), ISO.class );
    }

    public String socket( String url, String op, String node ) throws RestExchangeException {
        if ( LOG.isDebugEnabled() ){
            LOG.debug( String.format( "Enviado operación %s al socket %s tipo ISO", op, node ) );
        }
        ResponsePayload< ? > response = ( ResponsePayload < ? > ) isoRest.request( HttpMethod.GET, buildURLForAnyService( url, op, node ), ResponsePayload.class );
        return mapper.map( response.getBody(), String.class );
    }



    private String buildURLForAnyService( String service, Object... params ) throws RestExchangeException {
        UriTemplate template = new UriTemplate( urlBase + service );
        return checkList( template.expand( params ) );
    }

    private String checkList( URI url ) throws RestExchangeException {
        if ( sanitizer.urlWhiteList( url ) ) {
            String uri = String.valueOf( url );
            uri = uri.replace( "%5E", "^" );
            return uri.replace( "%20", " " );
        } else {
            throw new RestExchangeException( CatalogError.INVALID_URL );
        }
    }

}