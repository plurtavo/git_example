package mx.com.prosa.nabhi.misc.model.jse;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representación una respuesta para solicitar el estilo de la pantalla y botones del cajero" )
public class StylesBank {

    @ApiModelProperty( value = "Id del estilo del banco", example = "1" )
    private String id;
    @ApiModelProperty( value = "Estilo CSS para los botones", example = "" )
    private String buttons;
    @ApiModelProperty( value = "Estilo CSS para el fondo", example = "" )
    private String dashboard;
    @ApiModelProperty( value = "Nombre de la imagen de fondo", example = "Background" )
    private String backgroundImage;
    @ApiModelProperty( value = "Cantidad de pantallas que puede mostrar el cajero", example = "{RETIRO,VENTA DE TIEMPO AIRE, CAMBO DE NIP}" )
    private String sections;

    public StylesBank( String buttons, String dashboard, String backgroundImage, String sections ) {
        this.buttons = buttons;
        this.dashboard = dashboard;
        this.backgroundImage = backgroundImage;
        this.sections = sections;
    }

    public StylesBank() {
    }

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

    public String getSections() {
        return sections;
    }

    public void setSections( String sections ) {
        this.sections = sections;
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
