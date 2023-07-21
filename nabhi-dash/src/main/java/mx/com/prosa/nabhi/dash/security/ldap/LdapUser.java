//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
package mx.com.prosa.nabhi.dash.security.ldap;

import com.google.gson.GsonBuilder;

import java.util.List;

public class LdapUser extends LdapEntry {


    private List < String > roles;

    LdapUser( LdapEntry entry ) {
        super( entry.getIdentity(), entry.getAttributes() );
    }

    public List < String > getRoles() {
        return roles;
    }

    void setRoles( List < String > roles ) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End