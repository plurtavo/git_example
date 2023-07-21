package mx.com.prosa.nabhi.acv.security.filter.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final HttpStatus UNAUTHORIZED = HttpStatus.UNAUTHORIZED;
    private static final String TIME = "{\"timestamp\": ";
    private static final String PATH = "\"path\": \"/login\"}";
    private static final String STATUS = "\"status\": ";

    @Override
    public void onAuthenticationFailure( HttpServletRequest request, HttpServletResponse response, AuthenticationException e ) throws IOException {
        response.setStatus( UNAUTHORIZED.value() );
        response.setContentType( "application/json" );
        response.getWriter().append( jsonResponse() );
    }

    private String jsonResponse() {
        long date = new Date().getTime();
        return TIME + date + ", "
                + STATUS + UNAUTHORIZED.value() + ", "
                + "\"error\": \"Unauthorized\", "
                + "\"message\": \"Autenticación incorrecta : credenciales incorrectas\", "
                + PATH;
    }

    String jsonResponseLogin() {
        long date = new Date().getTime();
        return TIME + date + ", "
                + STATUS + UNAUTHORIZED.value() + ", "
                + "\"error\": \"Unauthorized\", "
                + "\"code\": \"L_DES_01\", "
                + "\"message\": \"Por favor contacta al administrador del sistema.\", "
                + PATH;
    }


    String jsonResponseLoginBadPassword() {
        long date = new Date().getTime();
        return TIME + date + ", "
                + STATUS + HttpStatus.FORBIDDEN.value() + ", "
                + "\"error\": \"Bad Credentials\", "
                + "\"message\": \"Por favor asegúrese de que su contraseña es correcta\", "
                + PATH;
    }

    String jsonResponsePrefix( String prefix ) {
        long date = new Date().getTime();
        return TIME + date + ", "
                + STATUS + HttpStatus.CONFLICT.value() + ", "
                + "\"error\": \" " + prefix + "\", "
                + "\"message\": \"Por favor contacta al administrador del sistema.\", "
                + PATH;
    }
}