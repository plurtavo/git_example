package mx.com.prosa.nabhi.jse.security.filter.auth;

import mx.com.prosa.nabhi.jse.security.TokenUser;
import mx.com.prosa.nabhi.misc.domain.security.entity.PrivilegeEntity;
import mx.com.prosa.nabhi.misc.domain.security.entity.RoleEntity;
import mx.com.prosa.nabhi.misc.domain.security.entity.UserEntity;
import mx.com.prosa.nabhi.misc.domain.security.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserAuth implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public UserAuth( UserRepository userRepository, ModelMapper modelMapper ) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername( String name ) {
        Optional < UserEntity > user = userRepository.findById( name );
        if ( !user.isPresent() ) {
            throw new UsernameNotFoundException( "Invalid username or password" );
        }
        validateUser( user.get() );
        return new org.springframework.security.core.userdetails.User(
                user.get().getId(), user.get().getPassword(), user.get().isEnabled(), true, true,
                true, getAuthorities( user.get().getRoles() ) );
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

    public TokenUser loadUserById( String name ) {
        Optional < UserEntity > entity = userRepository.findById( name );
        if ( entity.isPresent() ) {
            return modelMapper.map( entity.get(), TokenUser.class );
        } else {
            return modelMapper.map( entity, TokenUser.class );
        }
    }

    public void validateUser( UserEntity userEntity ) {
        if ( !userEntity.isEnabled() ) {
            throw new UsernameNotFoundException( "User no enable" );
        }
        if ( userEntity.isTokenExpired() ) {
            throw new UsernameNotFoundException( "User expired" );
        }
        if ( new Date().getTime() / 1000 > userEntity.getExpirationTime() ) {
            userEntity.setTokenExpired( true );
            userEntity.setEnabled( false );
            throw new UsernameNotFoundException( "User expired" );
        }
        userEntity.setLastUsage( new Date().getTime() / 1000 );
    }

}