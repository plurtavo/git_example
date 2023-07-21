//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
package mx.com.prosa.nabhi.dash.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.model.ResponsePayload;
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

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
public class JwtFilter extends OncePerRequestFilter {
    //Cambio release/eventos
    private final JwtBuilder jwtBuilder;
    private static final Logger LOGGER = LoggerFactory.getLogger( JwtFilter.class );

    //Cambio release/eventos
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
        } catch ( MalformedJwtException e ) {
            SecurityContextHolder.getContext().setAuthentication( null );
            response.setStatus( FORBIDDEN.value() );
            response.setContentType( "application/json" );
            try {
                response.getWriter().append( tokenMalformed() );
            } catch ( IOException e1 ) {
                LOGGER.error( "Error al convertir respuesta JSON JWT malformed" );

            }
        } catch ( ExpiredJwtException e ) {
            SecurityContextHolder.getContext().setAuthentication( null );
            response.setStatus( FORBIDDEN.value() );
            response.setContentType( "application/json" );
            try {
                response.getWriter().append( tokenExpired() );
            } catch ( IOException e1 ) {
                LOGGER.error( "Error al convertir respuesta JSON JWT expired" );

            }
        }
    }

    private String tokenExpired() {
        return ResponsePayload.setError( CatalogError.JWT_EXPIRED.getMessage(), CatalogError.JWT_EXPIRED.getCode() ).toString();
    }

    private String tokenMalformed() {
        return ResponsePayload.setError( CatalogError.JWT_MALFORMED.getMessage(), CatalogError.JWT_MALFORMED.getCode() ).toString();
    }
//Cambio release/eventos
}
//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End