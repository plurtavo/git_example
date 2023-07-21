package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación del estilo de pantalla del banco" )
public class BankStyle implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "ID del estilo de banco", example = "1" )
    private String id;
    @ApiModelProperty( value = "Estilo CSS de los botones", example = "1" )
    private String buttons;
    @ApiModelProperty( value = "Estilo CSS de la pantalla", example = "1" )
    private String dashboard;
    @ApiModelProperty( value = "Nombre de la imagen de fondo", example = "1" )
    private String backgroundImage;

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getButtons() {
        return buttons;
    }

    public void setButtons( String buttons ) {
        this.buttons = buttons;
    }

    public String getDashboard() {
        return dashboard;
    }

    public void setDashboard( String dashboard ) {
        this.dashboard = dashboard;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage( String backgroundImage ) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
