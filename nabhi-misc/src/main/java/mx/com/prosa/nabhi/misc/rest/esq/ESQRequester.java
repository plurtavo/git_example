package mx.com.prosa.nabhi.misc.rest.esq;

import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.rest.RestExchangeException;
import mx.com.prosa.nabhi.misc.model.esq.XFSMessage;
import mx.com.prosa.nabhi.misc.model.esq.XFSResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.Collections;

import static org.springframework.http.HttpMethod.POST;

@Component
public class ESQRequester {

    private final ModelMapper modelMapper;

    @Autowired
    public ESQRequester( ModelMapper modelMapper ) {
        this.modelMapper = modelMapper;
    }

    public XFSResponse exchange( XFSMessage xfsMessage, String uri ) throws RestExchangeException {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity< ? > httpEntity = new HttpEntity <>( xfsMessage, buildHttpHeaders() );
        try {
            ResponseEntity< ? > response = restTemplate.exchange( buildURLForAnyService( uri + "/reporteMensajeATmXfs/xfs" ), POST, httpEntity, XFSResponse.class );
            if ( response.getStatusCode() == HttpStatus.OK ) {
                assert response.getBody() != null;
                return modelMapper.map( response.getBody(), XFSResponse.class );
            } else {
                throw new RestExchangeException( CatalogError.HTTP_SERVER_ERROR_2, "ESQ" );
            }
        } catch ( ResourceAccessException e ) {
            throw new RestExchangeException( CatalogError.RESOURCE_ACCESS, String.format( "Causa %s", e.getMostSpecificCause().getMessage() ) );
        } catch ( HttpClientErrorException e ) {
            throw new RestExchangeException( CatalogError.HTTP_CLIENT_ERROR, String.format( "Estatus HTTP %s, causa %s", e.getStatusCode(), e.getMostSpecificCause().getMessage() ) );
        } catch ( RestClientException e ) {
            throw new RestExchangeException( CatalogError.HTTP_CLIENT_ERROR, String.format( "Causa %s", e.getMostSpecificCause().getMessage() ) );
        }
    }

    private String buildURLForAnyService( String url, Object... paths ) throws RestExchangeException {
        UriTemplate template = new UriTemplate( url );
        try {
            return checkList( template.expand( paths ) );
        } catch ( IllegalArgumentException e ) {
            throw new RestExchangeException( CatalogError.INVALID_URL );
        }
    }

    private String checkList( URI url ) {
        return String.valueOf( url ).replace( "%20", " " );
    }

    private HttpHeaders buildHttpHeaders() {
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.setAccept( Collections.singletonList( MediaType.APPLICATION_JSON ) );
        customHeader.add( "Content-Type", "application/json" );
        return customHeader;
    }

}
