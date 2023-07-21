package mx.com.prosa.nabhi.misc.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.ErrorCode;
import mx.com.prosa.nabhi.misc.exception.rest.RestExchangeException;
import mx.com.prosa.nabhi.misc.model.ResponseError;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@Service
public class RequestFactory< T > {

    private static final ModelMapper mapper = new ModelMapper();

    public T request( HttpMethod method, String url, Class < ? > valueType ) throws RestExchangeException {
        ResponseEntity < ? > response = exchange( url, method, valueType, null );
        return verifyAndResolvedSingleResponse( valueType, response );
    }

    public T request( HttpMethod method, String url, Class < ? > valueType, Map < String, Object > params ) throws RestExchangeException {
        ResponseEntity < ? > response = exchange( buildUri( url, params ), method, valueType, null );
        return verifyAndResolvedSingleResponse( valueType, response );
    }

    public T request( HttpMethod method, String url, Class < ? > valueType, Map < String, Object > params, Object save ) throws RestExchangeException {
        ResponseEntity < ? > response = exchange( buildUri( url, params ), method, valueType, new HttpEntity <>( save, buildHttpHeaders() ) );
        return verifyAndResolvedSingleResponse( valueType, response );
    }

    public T request( HttpMethod method, String url, Class < ? > valueType, Object save ) throws RestExchangeException {
        ResponseEntity < ? > response = exchange( url, method, valueType, new HttpEntity <>( save, buildHttpHeaders() ) );
        return verifyAndResolvedSingleResponse( valueType, response );
    }

    private RestTemplate getRestTemplate() {
        RestTemplate rt = new RestTemplate();
        SimpleClientHttpRequestFactory rf =
                ( SimpleClientHttpRequestFactory ) rt.getRequestFactory();
        rf.setReadTimeout( 40000 );
        rf.setConnectTimeout( 40000 );
        return rt;
    }

    private ResponseEntity < ? > exchange( String url, HttpMethod method, Class < ? > valueType, HttpEntity < ? > overEntity ) throws RestExchangeException {
        HttpEntity < ? > httpEntity = new HttpEntity <>( null, buildHttpHeaders() );
        if ( overEntity != null ) {
            httpEntity = overEntity;
        }
        try {
            return getRestTemplate().exchange( url, method,
                    httpEntity, valueType );
        } catch ( ResourceAccessException e ) {
            throw new RestExchangeException( CatalogError.RESOURCE_ACCESS, String.format( "Causa %s", e.getMostSpecificCause().getMessage() ) );
        } catch ( HttpClientErrorException e ) {
            throw new RestExchangeException( CatalogError.HTTP_CLIENT_ERROR, String.format( "Estatus HTTP %s", e.getStatusCode() ) );
        } catch ( HttpServerErrorException e ) {
            throw resolveError( e.getResponseBodyAsString() );
        } catch ( RestClientException e ) {
            throw new RestExchangeException( CatalogError.HTTP_CLIENT_ERROR, String.format( "Causa %s", e.getMostSpecificCause().getMessage() ) );
        }
    }

    private RestExchangeException resolveError( String error ) {
        CatalogError noError = CatalogError.NO_ERROR;
        Gson gson = new GsonBuilder().create();
        ResponseError errorServer = gson.fromJson( error, ResponseError.class );
        ErrorCode errorCode = noError.findByCode( errorServer.getErrorCode() );
        if ( errorCode == noError ) {
            errorCode = CatalogError.SYSTEM_ERROR_SERVICE;
        }
        return new RestExchangeException( errorCode );
    }

    @SuppressWarnings( "unchecked" )
    private T verifyAndResolvedSingleResponse( Class < ? > valueType, ResponseEntity < ? > response ) throws RestExchangeException {
        Object body = response.getBody();
        if ( response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.NO_CONTENT || response.getStatusCode() == HttpStatus.CREATED ) {
            assert body != null;
            return ( T ) mapper.map( body, valueType );
        } else {
            HttpStatus status = HttpStatus.valueOf( response.getStatusCodeValue() );
            throw new RestExchangeException( CatalogError.HTTP_CLIENT_ERROR, String.format( "Estatus HTTP %s", status.value() ) );
        }
    }

    private HttpHeaders buildHttpHeaders() {
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.setAccept( Collections.singletonList( MediaType.APPLICATION_JSON ) );
        customHeader.add( "Content-Type", "application/json" );
        customHeader.add( HttpHeaders.AUTHORIZATION, getToken() );
        return customHeader;
    }

    private String buildUri( String url, Map < String, Object > params ) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl( url );
        params.forEach( builder::queryParam );
        return builder.build().encode().toUri().toString();
    }

    private String getToken() {
        HttpServletRequest request = ( ( ServletRequestAttributes ) RequestContextHolder.currentRequestAttributes() ).getRequest();
        return request.getHeader( "Authorization" );
    }
}