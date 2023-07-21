package mx.com.prosa.nabhi.misc.model.jse.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representación una respuesta de botones activos por pantalla" )
public class ScreenCapabilities {

    @ApiModelProperty( value = "Nombre de pantalla", example = "SELECTOPERATION" )
    private String screen;
    @ApiModelProperty( value = "FDKs activos para esta pantalla", example = "11101000" )
    private String activeFDKs;

    public String getScreen() {
        return screen;
    }

    public void setScreen( String screen ) {
        this.screen = screen;
    }

    public String getActiveFDKs() {
        return activeFDKs;
    }

    public void setActiveFDKs( String activeFDKs ) {
        this.activeFDKs = activeFDKs;
    }


    public String toString() {
        return "Screeen : " + screen + "   ActiveFDKs : " + activeFDKs;
    }
}
