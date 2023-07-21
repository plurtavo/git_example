package mx.com.prosa.nabhi.misc.model.jse;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.jdb.ScreenGroup;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación una respuesta para solicitar el grupo de pantallas del cajero" )
public class Screen implements Serializable {

    @ApiModelProperty( value = "Nemotecnico del cajero", example = "ABCCAP" )
    private String terminalId;
    @ApiModelProperty( value = "Tipo de pantalla del cajero", example = "TOUCH" )
    private String screenType;
    @ApiModelProperty( value = "Grupo de pantallas del cajero", example = "" )
    private ScreenGroup screenGroup;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType( String screenType ) {
        this.screenType = screenType;
    }

    public ScreenGroup getScreenGroup() {
        return screenGroup;
    }

    public void setScreenGroup( ScreenGroup screenGroup ) {
        this.screenGroup = screenGroup;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
