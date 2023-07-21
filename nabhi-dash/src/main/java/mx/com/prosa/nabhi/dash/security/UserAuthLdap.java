package mx.com.prosa.nabhi.dash.security;
//Cambio release/redcat

import mx.com.prosa.nabhi.dash.security.ldap.LDAPService;
import mx.com.prosa.nabhi.misc.domain.security.entity.UserEntity;
import mx.com.prosa.nabhi.misc.domain.security.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End

@Service
@Transactional
public class UserAuthLdap implements UserDetailsService {
    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
    private final LDAPService userService;
    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
    public UserAuthLdap( LDAPService userService, UserRepository userRepository, ModelMapper modelMapper ) {
        //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End
        this.userService = userService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
    @Override
    public UserDetails loadUserByUsername( String name ) {
        return userService.findUserDetail( name );
    }
    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End

    public TokenUser loadUserById( String name ) {
        Optional < UserEntity > entity = userRepository.findById( name );
        if ( entity.isPresent() ) {
            return modelMapper.map( entity.get(), TokenUser.class );
        } else {
            return modelMapper.map( entity, TokenUser.class );
        }
    }
}
