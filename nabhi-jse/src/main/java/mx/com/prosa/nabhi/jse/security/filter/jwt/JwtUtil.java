package mx.com.prosa.nabhi.jse.security.filter.jwt;

import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.jse.security.KeyAuth;
import mx.com.prosa.nabhi.jse.security.TokenUser;
import mx.com.prosa.nabhi.misc.exception.JWTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private JwtBuilder jwtBuilder;
    private static final Logger LOG = LoggerFactory.getLogger( JwtUtil.class );

    @Autowired
    public JwtUtil( JwtBuilder jwtBuilder ) {
        this.jwtBuilder = jwtBuilder;
    }

    void addAuthentication( HttpServletResponse response, TokenUser tokenUser, Authentication authentication ) {
        final String authorities = authentication.getAuthorities().stream()
                .map( GrantedAuthority::getAuthority )
                .collect( Collectors.joining( "," ) );
        final long currentTimestamp = new Date().getTime() / 1000;
        try {
            Map < String, Object > claims = new LinkedHashMap <>();
            claims.put( "exp", new Date( currentTimestamp + tokenUser.getExpirationTokenTime() ) );
            claims.put( "sub", authentication.getName() );
            claims.put( "finger", tokenUser );
            claims.put( "CLAIM_TOKEN", authorities );
            String jwt = jwtBuilder.buildJwtWithHS512( claims );
            response.setCharacterEncoding( "UTF-8" );
            response.addHeader( "Content-Type", "application/json" );
            KeyAuth k = new KeyAuth();
            k.setAccessToken( jwt );
            k.setExpiresIn( new Date( ( currentTimestamp + tokenUser.getExpirationTokenTime() ) * 1000L ).toString() );
            k.setJwtExpires( tokenUser.getExpirationTokenTime() );
            String responseComplete = new GsonBuilder().create().toJson( k );
            PrintWriter out = response.getWriter();
            out.print( responseComplete );
            out.flush();
            if ( LOG.isDebugEnabled() ) {
                LOG.debug( String.format( "Successful key request: %s", jwt ) );
            }
        } catch ( IOException e ) {
            LOG.error( TOKEN_THROUGH_THE_SERVLET );
        } catch ( JWTException e ) {
            LOG.error( INVALID_DATA_FROM_TOKEN );
        }
    }

}