//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
package mx.com.prosa.nabhi.dash.security.ldap;

import com.google.gson.GsonBuilder;

import java.util.List;

public class LdapApp {

    private String name;
    private List < LdapEntry > roles;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    List < LdapEntry > getRoles() {
        return roles;
    }

    void setRoles( List < LdapEntry > roles ) {
        this.roles = roles;
    }

    @Override
    public String toString() {

        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End