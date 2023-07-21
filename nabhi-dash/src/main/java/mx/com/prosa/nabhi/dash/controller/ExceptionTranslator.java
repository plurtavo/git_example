package mx.com.prosa.nabhi.dash.controller;

import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionTranslator {

    private static final Logger LOGGER = LoggerFactory.getLogger( ExceptionTranslator.class );

    @ExceptionHandler( AccessDeniedException.class )
    @ResponseStatus( HttpStatus.FORBIDDEN )
    public ResponseEntity < ResponsePayload < String > > accessDenied( AccessDeniedException e ) {
        if ( LOGGER.isErrorEnabled() ){
            LOGGER.error( e.getMessage() );
        }
        return new ResponseEntity <>( ResponsePayload.setError( CatalogError.JWT_NO_PRIVILEGE.getMessage(), CatalogError.JWT_NO_PRIVILEGE.getCode() ), HttpStatus.FORBIDDEN );
    }

    @ExceptionHandler( Exception.class )
    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
    public ResponseEntity < ResponsePayload < String > > processRuntimeException( Exception e ) {
        if ( LOGGER.isErrorEnabled() ) {
            LOGGER.error( "Ocurrió un error severo en el API" );
            LOGGER.error( String.format( "Causa %s", e.getMessage() ) );
            LOGGER.error( String.format( "Detalle %s", e.getLocalizedMessage() != null ? e.getMessage() : "No existe detalle" ) );
        }
        return new ResponseEntity <>( ResponsePayload.setError( CatalogError.SERVER_ERROR.getMessage(), CatalogError.SERVER_ERROR.getCode() ), HttpStatus.INTERNAL_SERVER_ERROR );
    }

}