package mx.com.prosa.nabhi.misc.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtBuilder jwtBuilder;
    private static final Logger LOGGER = LoggerFactory.getLogger( JwtFilter.class );

    @Autowired
    public JwtFilter( JwtBuilder jwtBuilder ) {
        this.jwtBuilder = jwtBuilder;
    }

    @Override
    protected void doFilterInternal( HttpServletRequest httpServletRequest, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader( "Authorization" );
        if ( StringUtils.isEmpty( authorizationHeader ) || !authorizationHeader
                .startsWith( "Bearer" ) ) {
            filterChain.doFilter( httpServletRequest, response );
            return;
        }
        try {
            final String token = authorizationHeader.replace( "Bearer ", "" );
            String userName = jwtBuilder.getUserName( token );
            UsernamePasswordAuthenticationToken userToken = jwtBuilder.getAuthentication( token, userName );
            SecurityContextHolder.getContext().setAuthentication( userToken );
            filterChain.doFilter( httpServletRequest, response );
        }catch ( MalformedJwtException e ){
            SecurityContextHolder.getContext().setAuthentication( null );
            response.setStatus( FORBIDDEN.value() );
            response.setContentType( "application/json" );
            try {
                response.getWriter().append( tokenMalformed() );
            } catch ( IOException e1 ) {
                LOGGER.error( "Error Parse" );

            }
        }catch ( ExpiredJwtException e ){
            SecurityContextHolder.getContext().setAuthentication( null );
            response.setStatus( FORBIDDEN.value() );
            response.setContentType( "application/json" );
            try {
                response.getWriter().append( tokenExpired() );
            } catch ( IOException e1 ) {
                LOGGER.error( "Error Parse" );

            }
        }
    }

    private String tokenExpired() {
        long date = new Date().getTime();
        return "{\n" +
                "    \"message\": \"Token inválido\",\n" +
                "    \"errorTimestamp\": \"" + date + "\",\n" +
                "    \"errorCause\": \"Token Expirado\"\n" +
                "}";
    }

    private String tokenMalformed() {
        long date = new Date().getTime();
        return "{\n" +
                "    \"message\": \"Token inválido\",\n" +
                "    \"errorTimestamp\": \"" + date + "\",\n" +
                "    \"errorCause\": \"Token Corrupto\"\n" +
                "}";
    }
}
