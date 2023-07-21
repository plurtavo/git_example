package mx.com.prosa.nabhi.jse.security.filter.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.com.prosa.nabhi.jse.security.User;
import mx.com.prosa.nabhi.jse.security.filter.auth.UserAuth;
import mx.com.prosa.nabhi.misc.exception.sanitize.SanitazeException;
import mx.com.prosa.nabhi.misc.util.Sanitizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger LOG = LoggerFactory.getLogger( LoginFilter.class );
    private JwtUtil jwtUtil;
    private UserAuth userAuth;
    private ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;
    private JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private Sanitizer sanitizer;


    public LoginFilter( AuthenticationManager authenticationManager ) {
        this.authenticationManager = authenticationManager;

    }

    public void init( JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler, JwtUtil jwtUtil, UserAuth userAuth, ObjectMapper objectMapper, Sanitizer sanitizer ) {
        this.jwtUtil = jwtUtil;
        this.userAuth = userAuth;
        this.objectMapper = objectMapper;
        this.jwtAuthenticationFailureHandler = jwtAuthenticationFailureHandler;
        this.sanitizer = sanitizer;
        setAuthenticationFailureHandler( jwtAuthenticationFailureHandler );

    }

    @Override
    public Authentication attemptAuthentication( HttpServletRequest req, HttpServletResponse res ) {
        final String content = "application/json";
        try {
            User user = objectMapper.readValue( req.getInputStream(), User.class );
            user.setUserName( sanitizer.sanitize( user.getUserName() ) );
            user.setPassword( sanitizer.sanitize( user.getPassword() ) );
            return authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(
                    user.getUserName(), user.getPassword() ) );
        } catch ( SanitazeException | UsernameNotFoundException | IOException e ) {
            try {
                res.setStatus( UNAUTHORIZED.value() );
                res.setContentType( content );
                res.getWriter().append( jwtAuthenticationFailureHandler.jsonResponseLogin() );
            } catch ( IOException e1 ) {
                LOG.warn( "Invalid User Model" );
            }
        }catch ( IllegalArgumentException e ){
            try {
                res.setStatus( HttpStatus.CONFLICT.value() );
                res.setContentType( content );
                res.getWriter().append( jwtAuthenticationFailureHandler.jsonResponsePrefix( e.getMessage() ) );
            } catch ( IOException e1 ) {
                LOG.warn( "Invalid Prefix" );
            }
        }catch ( BadCredentialsException e ) {
            try {
                res.setStatus( UNAUTHORIZED.value() );
                res.setContentType( content );
                res.getWriter().append( jwtAuthenticationFailureHandler.jsonResponseLoginBadPassword() );
            } catch ( IOException e1 ) {
                LOG.warn( "Invalid Prefix" );
            }
        }
        return null;
    }

    @Override
    public void successfulAuthentication( HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth ) {
        jwtUtil.addAuthentication( res, userAuth.loadUserById( auth.getName() ), auth );
    }

    @Override
    protected void unsuccessfulAuthentication( HttpServletRequest request, HttpServletResponse response, AuthenticationException failed ) throws IOException {
        if ( LOG.isWarnEnabled() ) {
            LOG.warn( "Bad Credentials" );
        }
        response.sendError( HttpServletResponse.SC_FORBIDDEN, "Authentication Failed" );
    }


}
