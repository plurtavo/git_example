//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
package mx.com.prosa.nabhi.dash.security.ldap;

import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

public class EntryWrapper {

    private String identity;
    private Map < String, List < Object > > attributes;

    String getIdentity() {
        return identity;
    }

    void setIdentity( String identity ) {
        this.identity = identity;
    }

    Map < String, List < Object > > getAttributes() {
        return attributes;
    }

    void setAttributes( Map < String, List < Object > > attributes ) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End