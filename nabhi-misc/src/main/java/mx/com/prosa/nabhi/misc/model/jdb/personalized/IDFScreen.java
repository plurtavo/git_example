package mx.com.prosa.nabhi.misc.model.jdb.personalized;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.jdb.ScreenGroup;

import java.io.Serializable;
import java.util.List;

@ApiModel( description = "Json DTO representación de un IDF (Para consultar grupos de pantalla)" )
public class IDFScreen implements Serializable {

    @ApiModelProperty( value = "ID del FIID", example = "B000" )
    private String fiid;
    @ApiModelProperty( value = "Grupos de pantallas pertenecientes a la institución", example = "B000_TEST_01" )
    private List < ScreenGroup > screens;

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    public List < ScreenGroup > getScreens() {
        return screens;
    }

    public void setScreens( List < ScreenGroup > screens ) {
        this.screens = screens;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }

}
