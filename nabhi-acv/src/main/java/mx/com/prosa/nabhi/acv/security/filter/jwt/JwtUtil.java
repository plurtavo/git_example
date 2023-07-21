package mx.com.prosa.nabhi.acv.security.filter.jwt;

import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.misc.exception.acv.UserAccessException;
import mx.com.prosa.nabhi.misc.model.sec.KeyAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static final String INVALID_DATA_FROM_TOKEN = "Invalid data from Token";
    private static final String TOKEN_THROUGH_THE_SERVLET = "Error returning the token through the Servlet";
    private static final Logger LOG = LoggerFactory.getLogger( JwtUtil.class );
    private final JwtBuilder jwtBuilder;

    @Value( "${nabhi.websocket.token.expiration}" )
    private long tokenExpiration;

    public JwtUtil( JwtBuilder jwtBuilder ) {
        this.jwtBuilder = jwtBuilder;
    }

    void addAuthentication( HttpServletResponse response, Authentication authentication ) {
        final String authorities = authentication.getAuthorities().stream()
                .map( GrantedAuthority::getAuthority )
                .collect( Collectors.joining( "," ) );
        final long currentTimestamp = new Date().getTime() / 1000;
        try {
            Map < String, Object > claims = new LinkedHashMap <>();
            if ( !authorities.equals( "WEB-SOCKET-ATM" ) ) {
                claims.put( "exp", new Date( currentTimestamp + tokenExpiration ) );
            }
            claims.put( "sub", authentication.getName() );
            claims.put( "CLAIM_TOKEN", authorities );
            String jwt = jwtBuilder.buildJwtWithHS512( claims );
            response.setCharacterEncoding( "UTF-8" );
            response.addHeader( "Content-Type", "application/json" );
            KeyAuth k = new KeyAuth();
            k.setAccessToken( jwt );
            k.setExpiresIn( new Date( ( currentTimestamp + tokenExpiration ) * 1000L ).toString() );
            String responseComplete = new GsonBuilder().create().toJson( k );
            PrintWriter out = response.getWriter();
            out.print( responseComplete );
            out.flush();
            if ( LOG.isDebugEnabled() ) {
                LOG.debug( String.format( "Successful key request: %s", jwt ) );
            }
        } catch ( IOException e ) {
            LOG.error( TOKEN_THROUGH_THE_SERVLET );
        } catch ( UserAccessException e ) {
            LOG.error( INVALID_DATA_FROM_TOKEN );
        }
    }

}