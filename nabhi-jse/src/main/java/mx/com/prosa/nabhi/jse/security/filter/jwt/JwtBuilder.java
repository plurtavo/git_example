package mx.com.prosa.nabhi.jse.security.filter.jwt;

import io.jsonwebtoken.*;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.JWTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtBuilder {

    private static final Logger LOG = LoggerFactory.getLogger( JwtBuilder.class );
    private static final String ERROR_GENERATING_JWT_TOKEN = "Error generating JW Token";
    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS512;

    @Value( "${delt.key}" )
    private String privateKey;


    String buildJwtWithHS512( Map < String, Object > claims ) throws JWTException {
        try {
            return Jwts.builder()
                    .setClaims( claims )
                    .signWith( ALGORITHM, privateKey )
                    .compact();
        } catch ( SecurityException e ) {
            LOG.error( ERROR_GENERATING_JWT_TOKEN );
            throw new JWTException( CatalogError.TOKEN_BUILD_ERROR );
        }
    }

    UsernamePasswordAuthenticationToken getAuthentication( final String token, final String userDetails ) {
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( "Inicia autenticación de Token de Cajeros" );
        }
        final JwtParser jwtParser = Jwts.parser().setSigningKey( privateKey );
        final Jws < Claims > claimsJws = jwtParser.parseClaimsJws( token );
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( "Se obtiene autoridades Cajeros" );
        }
        final Claims claims = claimsJws.getBody();
        final Collection < SimpleGrantedAuthority > authorities =
                Arrays.stream( claims.get( "CLAIM_TOKEN" ).toString().split( "," ) )
                        .map( SimpleGrantedAuthority::new )
                        .collect( Collectors.toList() );
        return new UsernamePasswordAuthenticationToken( userDetails, "", authorities );
    }

    String getUserName( final String token ) {
        try {
            final JwtParser jwtParser = Jwts.parser().setSigningKey( privateKey );
            final Jws < Claims > claimsJws = jwtParser.parseClaimsJws( token );
            return claimsJws.getBody().getSubject();
        } catch ( SignatureException e ) {
            LOG.error( "La firma del token es invalida para cajeros" );
            return "";
        }

    }
}

