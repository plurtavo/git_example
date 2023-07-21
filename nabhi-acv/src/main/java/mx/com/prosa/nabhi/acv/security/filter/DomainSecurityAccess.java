package mx.com.prosa.nabhi.acv.security.filter;

import mx.com.prosa.nabhi.acv.security.filter.auth.ISecurityAccess;
import mx.com.prosa.nabhi.misc.domain.acv.entity.PrivilegeEntity;
import mx.com.prosa.nabhi.misc.domain.acv.entity.RoleEntity;
import mx.com.prosa.nabhi.misc.domain.acv.entity.UserEntity;
import mx.com.prosa.nabhi.misc.domain.acv.repository.UserACVRepository;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.acv.UserAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class DomainSecurityAccess implements ISecurityAccess {

    private final UserACVRepository userRepository;

    @Autowired
    public DomainSecurityAccess( UserACVRepository userRepository ) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails findUserDetail( String name ) throws UserAccessException {
        Optional< UserEntity > optional = userRepository.findById( name );
        if ( optional.isPresent() ){
            UserEntity entity = optional.get();
            return new org.springframework.security.core.userdetails.User(
                    entity.getId(), entity.getPassword(), true, true, true,
                    true, getAuthorities( entity.getRoles() ) );
        }
        throw new UserAccessException( CatalogError.ATM_NOT_ALLOW );
    }


    private Collection < ? extends GrantedAuthority > getAuthorities( final Collection < RoleEntity > roles ) {
        List < String > rol = new ArrayList <>();
        for ( RoleEntity roleEntity : roles ) {
            rol.add( roleEntity.getName() );
        }
        rol.addAll( getPrivileges( roles ) );
        return getGrantedAuthorities( rol );
    }

    private List < String > getPrivileges( final Collection < RoleEntity > roles ) {
        final List < String > privileges = new ArrayList <>();
        final List < PrivilegeEntity > collection = new ArrayList <>();
        for ( final RoleEntity role : roles ) {
            collection.addAll( role.getPrivileges() );
        }
        for ( final PrivilegeEntity item : collection ) {
            privileges.add( item.getName() );
        }

        return privileges;
    }

    private List < GrantedAuthority > getGrantedAuthorities( final List < String > privileges ) {
        final List < GrantedAuthority > authorities = new ArrayList <>();
        for ( final String privilege : privileges ) {
            authorities.add( new SimpleGrantedAuthority( privilege ) );
        }
        return authorities;
    }
}
