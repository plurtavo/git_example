//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
package mx.com.prosa.nabhi.dash.security.jwt;
//Cambio release/redcat

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.com.prosa.nabhi.dash.security.User;
import mx.com.prosa.nabhi.dash.security.UserAuthLdap;
import mx.com.prosa.nabhi.misc.exception.sanitize.SanitazeException;
import mx.com.prosa.nabhi.misc.util.Sanitizer;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.CannotCreateTransactionException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger LOG = LoggerFactory.getLogger( LoginFilter.class );
    private JwtUtil jwtUtil;
    private UserAuthLdap userAuth;
    private ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;
    private JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private Sanitizer sanitizer;


    public LoginFilter( AuthenticationManager authenticationManager ) {
        this.authenticationManager = authenticationManager;

    }

    public void init( JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler, JwtUtil jwtUtil, UserAuthLdap userAuth, ObjectMapper objectMapper, Sanitizer sanitizer ) {
        this.jwtUtil = jwtUtil;
        this.userAuth = userAuth;
        this.objectMapper = objectMapper;
        this.jwtAuthenticationFailureHandler = jwtAuthenticationFailureHandler;
        this.sanitizer = sanitizer;
        setAuthenticationFailureHandler( jwtAuthenticationFailureHandler );

    }

    @Override
    public Authentication attemptAuthentication( HttpServletRequest req, HttpServletResponse res ) {
        try {
            User user = objectMapper.readValue( req.getInputStream(), User.class );
            user.setUserName( sanitizer.sanitize( user.getUserName() ) );
            user.setPassword( sanitizer.sanitize( user.getPassword() ) );
            return authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(
                    user.getUserName(), user.getPassword() ) );
        } catch ( IllegalArgumentException | SanitazeException | IOException | AuthenticationException e ) {
            res.setStatus( FORBIDDEN.value() );
            res.setContentType( "application/json" );
            try {
                res.getWriter().append( jwtAuthenticationFailureHandler.jsonResponse( e.getMessage() ) );
            } catch ( IOException e1 ) {
                LOG.error( e1.getMessage() );
            }
            //Cambio release/monitoreo
        } catch ( CannotCreateTransactionException | JDBCConnectionException e ) {
            LOG.error( "No se puede adquirir la conexión via JDBC con la Base de Datos" );
            //Cambio release/monitoreo
        }
        return null;
    }

    @Override
    public void successfulAuthentication( HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth ) {
        jwtUtil.addAuthentication( res, userAuth.loadUserById( auth.getName() ), auth );
    }

    @Override
    protected void unsuccessfulAuthentication( HttpServletRequest request, HttpServletResponse response, AuthenticationException failed ) {
        response.setStatus( FORBIDDEN.value() );
        response.setContentType( "application/json" );
        try {
            response.getWriter().append( jwtAuthenticationFailureHandler.jsonResponse( failed.getMessage() ) );
        } catch ( IOException e1 ) {
            LOG.error( "Error Parse" );
        }
    }
}
//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End
