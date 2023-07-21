package mx.com.prosa.nabhi.acv.security.filter.auth;

import mx.com.prosa.nabhi.acv.security.filter.DomainSecurityAccess;
import mx.com.prosa.nabhi.misc.exception.acv.UserAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuth implements UserDetailsService {

    private final ISecurityAccess securityAccess;

    @Autowired
    public UserAuth( DomainSecurityAccess securityAccess ) {
        this.securityAccess = securityAccess;
    }

    @Override
    public UserDetails loadUserByUsername( String name ) {
        try {
            return securityAccess.findUserDetail( name );
        } catch ( UserAccessException e ) {
            throw new UsernameNotFoundException( "User not found" );
        }
    }

}