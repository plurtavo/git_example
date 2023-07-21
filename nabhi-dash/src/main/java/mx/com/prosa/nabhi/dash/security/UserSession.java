package mx.com.prosa.nabhi.dash.security;
//Cambio release/redcat

import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.domain.single.entity.UserSingleEntity;
import mx.com.prosa.nabhi.misc.domain.single.repository.UserSingleRepository;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.dash.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserSession {

    private final UserSingleRepository userRepository;

    @Autowired
    public UserSession( UserSingleRepository userRepository ) {
        this.userRepository = userRepository;
    }

    public IDFEntityKey getIDFForCurrentUser() throws UserNotFoundException {
        Optional< UserSingleEntity > optional = userRepository.findById( getPrincipal() );
        if ( optional.isPresent() ){
            return optional.get().getOwner();
        }else {
            throw new UserNotFoundException( CatalogError.USER_NOT_FOUND );
        }
    }

    public String getPrincipal(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
