//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
package mx.com.prosa.nabhi.dash.security.jwt;

import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final HttpStatus UNAUTHORIZED = HttpStatus.UNAUTHORIZED;

    @Override
    public void onAuthenticationFailure( HttpServletRequest request, HttpServletResponse response, AuthenticationException e ) throws IOException {
        response.setStatus( UNAUTHORIZED.value() );
        response.setContentType( "application/json" );
        response.getWriter().append( jsonResponse( e.getMessage() ) );
    }

    String jsonResponse( String error ) {
        return ResponsePayload.setError( error, CatalogError.LOGIN_ERROR.getCode() ).toString();
    }

}
//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End