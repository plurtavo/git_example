package mx.com.prosa.nabhi.acv.security.filter.jwt;

import mx.com.prosa.nabhi.acv.security.filter.User;
import org.codehaus.jackson.map.ObjectMapper;
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

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger LOG = LoggerFactory.getLogger( LoginFilter.class );
    private JwtUtil jwtUtil;
    private ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;
    private JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;


    public LoginFilter( AuthenticationManager authenticationManager ) {
        this.authenticationManager = authenticationManager;

    }

    public void init( JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler, JwtUtil jwtUtil, ObjectMapper objectMapper ) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        this.jwtAuthenticationFailureHandler = jwtAuthenticationFailureHandler;
        setAuthenticationFailureHandler( jwtAuthenticationFailureHandler );
    }

    @Override
    public Authentication attemptAuthentication( HttpServletRequest req, HttpServletResponse res ) {
        final String content = "application/json";
        try {
            User user = objectMapper.readValue( req.getInputStream(), User.class );
            return authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(
                    user.getUserName(), user.getPassword() ) );
        } catch ( UsernameNotFoundException | IOException e ) {
            try {
                res.setStatus( UNAUTHORIZED.value() );
                res.setContentType( content );
                res.getWriter().append( jwtAuthenticationFailureHandler.jsonResponseLogin() );
            } catch ( IOException e1 ) {
                LOG.warn( "Invalid User Data" );
            }
        } catch ( IllegalArgumentException e ) {
            try {
                res.setStatus( HttpStatus.NOT_FOUND.value() );
                res.setContentType( content );
                res.getWriter().append( jwtAuthenticationFailureHandler.jsonResponsePrefix( e.getMessage() ) );
            } catch ( IOException e1 ) {
                LOG.warn( "Invalid Prefix" );
            }
        } catch ( BadCredentialsException e ) {
            try {
                res.setStatus( FORBIDDEN.value() );
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
        jwtUtil.addAuthentication( res, auth );
    }

    @Override
    protected void unsuccessfulAuthentication( HttpServletRequest request, HttpServletResponse response, AuthenticationException failed ) throws IOException {
        if ( LOG.isWarnEnabled() ) {
            LOG.warn( "Bad Credentials" );
        }
        response.sendError( HttpServletResponse.SC_FORBIDDEN, "Authentication Failed" );
    }

}
