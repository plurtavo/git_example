package mx.com.prosa.nabhi.jse.security.filter.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
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
    public void onAuthenticationFailure( HttpServletRequest request, HttpServletResponse response, AuthenticationException e ) throws IOException, ServletException {
        response.setStatus( UNAUTHORIZED.value() );
        response.setContentType( "application/json" );
        response.getWriter().append( jsonResponse() );
    }

    private String jsonResponse() {
        long date = new Date().getTime();
        return TIME + date + ", "
                + STATUS + UNAUTHORIZED.value() + ", "
                + "\"error\": \"Unauthorized\", "
                + "\"message\": \"Authentication failed: bad credentials\", "
                + PATH;
    }

    String jsonResponseLogin() {
        long date = new Date().getTime();
        return TIME + date + ", "
                + STATUS+ UNAUTHORIZED.value() + ", "
                + "\"error\": \"Unauthorized\", "
                + "\"message\": \"Please contact your system admin\", "
                + PATH;
    }


    String jsonResponseLoginBadPassword() {
        long date = new Date().getTime();
        return TIME + date + ", "
                + STATUS + HttpStatus.FORBIDDEN.value() + ", "
                + "\"error\": \"Bad Credentials\", "
                + "\"message\": \"Please check your password\", "
                + PATH;
    }

    String jsonResponsePrefix( String prefix ) {
        long date = new Date().getTime();
        return TIME+ date + ", "
                + STATUS + HttpStatus.CONFLICT.value() + ", "
                + "\"error\": \" " + prefix + "\", "
                + "\"message\": \"Please contact your system admin\", "
                + PATH;
    }
}