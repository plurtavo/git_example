//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
package mx.com.prosa.nabhi.dash.security.ldap;


import org.springframework.security.core.AuthenticationException;

class AuthenticationLdapException extends AuthenticationException {

    AuthenticationLdapException( String errorMessage ) {
        super( errorMessage );
    }
}
//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End
