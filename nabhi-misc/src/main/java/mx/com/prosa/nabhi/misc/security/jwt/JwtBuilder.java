package mx.com.prosa.nabhi.misc.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class JwtBuilder {

    private static final Logger LOG = LoggerFactory.getLogger( JwtBuilder.class );

    @Value( "${delt.key}" )
    private String privateKey;


    UsernamePasswordAuthenticationToken getAuthentication( final String token, final String userDetails ) {
        if ( LOG.isDebugEnabled() ){
            LOG.debug( "Inicia autenticación de Token" );
        }
        final JwtParser jwtParser = Jwts.parser().setSigningKey( privateKey );
        final Jws < Claims > claimsJws = jwtParser.parseClaimsJws( token );
        if ( LOG.isDebugEnabled() ){
            LOG.debug( "Se obtiene autoridades" );
        }
        final Claims claims = claimsJws.getBody();
        final Collection < SimpleGrantedAuthority > authorities =
                Arrays.stream( claims.get( "CLAIM_TOKEN" ).toString().split( "," ) )
                        .map( SimpleGrantedAuthority::new )
                        .collect( Collectors.toList() );
        return new UsernamePasswordAuthenticationToken( userDetails, "", authorities );
    }

    String getUserName( final String token ) {
        try{
            final JwtParser jwtParser = Jwts.parser().setSigningKey( privateKey );
            final Jws < Claims > claimsJws = jwtParser.parseClaimsJws( token );
            return claimsJws.getBody().getSubject();
        }catch ( SignatureException e ){
            LOG.error( "La firma del token es invalida" );
            return "";
        }

    }
}
