//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
package mx.com.prosa.nabhi.dash.security.jwt;
//Cambio release/redcat

import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.dash.security.KeyAuth;
import mx.com.prosa.nabhi.dash.security.TokenUser;
import mx.com.prosa.nabhi.misc.domain.security.entity.MenuEntity;
import mx.com.prosa.nabhi.misc.domain.security.entity.RoleEntity;
import mx.com.prosa.nabhi.misc.domain.security.entity.RoleMenuEntity;
import mx.com.prosa.nabhi.misc.domain.security.repository.RoleMenuRepository;
import mx.com.prosa.nabhi.misc.domain.security.repository.RoleRepository;
import mx.com.prosa.nabhi.misc.exception.JWTException;
import mx.com.prosa.nabhi.misc.model.jdb.MenuTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static final String INVALID_DATA_FROM_TOKEN = "Invalid data from Token";
    private static final String TOKEN_THROUGH_THE_SERVLET = "Error returning the token through the Servlet";
    private static final Logger LOG = LoggerFactory.getLogger( JwtUtil.class );
    //Cambio release/eventos
    private final JwtBuilder jwtBuilder;
    private final RoleMenuRepository roleMenuRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public JwtUtil( JwtBuilder jwtBuilder, RoleMenuRepository roleMenuRepository, RoleRepository roleRepository ) {
        this.jwtBuilder = jwtBuilder;
        this.roleMenuRepository = roleMenuRepository;
        this.roleRepository = roleRepository;
    }

    //Cambio release/eventos

    void addAuthentication( HttpServletResponse response, TokenUser tokenUser, Authentication authentication ) {
        final String authorities = authentication.getAuthorities().stream()
                .map( GrantedAuthority::getAuthority )
                .collect( Collectors.joining( "," ) );
        final long currentTimestamp = new Date().getTime() / 1000;
        try {
            //Cambio release/eventos
            List < MenuTransfer > menus = getMenus( authorities );
            //Cambio release/eventos
            Map < String, Object > claims = new LinkedHashMap <>();
            claims.put( "exp", new Date( currentTimestamp + tokenUser.getExpirationTokenTime() ) );
            claims.put( "sub", authentication.getName() );
            claims.put( "finger", tokenUser );
            claims.put( "CLAIM_TOKEN", authorities );
            String jwt = jwtBuilder.buildJwtWithHS512( claims );
            response.setCharacterEncoding( "UTF-8" );
            response.addHeader( "Content-Type", "application/json" );
            KeyAuth k = new KeyAuth();
            //Cambio release/eventos
            k.setMenu( menus );
            //Cambio release/eventos
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

    //Cambio release/eventos
    private List < MenuTransfer > getMenus( String authorities ) {
        List < MenuTransfer > menus = new ArrayList <>();
        List < MenuEntity > entities = new ArrayList <>();
        Optional < RoleMenuEntity > optional;
        List < RoleEntity > roles = roleRepository.findAll();
        for ( RoleEntity role : roles ) {
            if ( authorities.contains( role.getName() ) ) {
                optional = roleMenuRepository.findById( role.getName() );
                optional.ifPresent( roleMenuEntity -> entities.addAll( roleMenuEntity.getMenus() ) );
            }
        }
        for ( MenuEntity entity : entities ) {
            menus.add( new MenuTransfer( entity.getId(), entity.getMenu(), entity.getParams() ) );
        }
        menus= menus.stream().distinct().sorted().collect( Collectors.toList() );
        return menus;
    }
    //Cambio release/eventos
}
//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End
