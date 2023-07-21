package mx.com.prosa.nabhi.dash.security;

import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.misc.model.jdb.MenuTransfer;

import java.util.List;

//Cambio release/eventos
public class KeyAuth {

    private String accessToken;
    private String expiresIn;
    private long jwtExpires;
    //Cambio release/eventos
    private List < MenuTransfer > menu;
    //Cambio release/eventos

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken( String accessToken ) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn( String expiresIn ) {
        this.expiresIn = expiresIn;
    }

    public long getJwtExpires() {
        return jwtExpires;
    }

    public void setJwtExpires( long jwtExpires ) {
        this.jwtExpires = jwtExpires;
    }

    //Cambio release/eventos
    public List < MenuTransfer > getMenu() {
        return menu;
    }

    public void setMenu( List < MenuTransfer > menu ) {
        this.menu = menu;
    }

    //Cambio release/eventos
    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
