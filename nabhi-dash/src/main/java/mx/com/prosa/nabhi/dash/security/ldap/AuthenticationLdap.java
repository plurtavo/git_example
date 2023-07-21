//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
package mx.com.prosa.nabhi.dash.security.ldap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationLdap implements AuthenticationManager {

    private final LDAPService ldapService;

    @Autowired
    public AuthenticationLdap( LDAPService ldapService ) {
        this.ldapService = ldapService;
    }

    @Override
    public Authentication authenticate( Authentication authentication ) {
        try {
            UserDetails userDetails = ldapService.findUserDetail( ( String ) authentication.getPrincipal(), ( String ) authentication.getCredentials() );
            return createSuccessAuthentication( authentication.getPrincipal(), authentication, userDetails );
        } catch ( LDAPException e ) {
            throw new AuthenticationLdapException( e.getMessage() );
        }
    }


    private Authentication createSuccessAuthentication( Object principal, Authentication authentication, UserDetails user ) {
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken( principal, authentication.getCredentials(), user.getAuthorities() );
        result.setDetails( authentication.getDetails() );
        return result;
    }
}
//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End