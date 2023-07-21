package mx.com.prosa.nabhi.acv.security.filter.jwt;

import io.jsonwebtoken.*;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.acv.UserAccessException;
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

    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS512;

    @Value( "${nabhi.websocket.token.secret}" )
    private String privateKey;

    String buildJwtWithHS512( Map < String, Object > claims ) throws UserAccessException {
        try {
            return Jwts.builder()
                    .setClaims( claims )
                    .signWith( ALGORITHM, privateKey )
                    .compact();
        } catch ( SecurityException e ) {
            throw new UserAccessException( CatalogError.ATM_NOT_GENERATE_TOKEN );
        }
    }

    UsernamePasswordAuthenticationToken getAuthentication( final String token, final String username ) {
        final JwtParser jwtParser = Jwts.parser().setSigningKey( privateKey );
        final Jws < Claims > claimsJws = jwtParser.parseClaimsJws( token );
        final Claims claims = claimsJws.getBody();
        final Collection < SimpleGrantedAuthority > authorities =
                Arrays.stream( claims.get( "CLAIM_TOKEN" ).toString().split( "," ) )
                        .map( SimpleGrantedAuthority::new )
                        .collect( Collectors.toList() );
        return new UsernamePasswordAuthenticationToken( username, null, authorities );
    }

    String getUserName( final String token ) {
        final JwtParser jwtParser = Jwts.parser().setSigningKey( privateKey );
        final Jws < Claims > claimsJws = jwtParser.parseClaimsJws( token );
        return claimsJws.getBody().getSubject();

    }
}

