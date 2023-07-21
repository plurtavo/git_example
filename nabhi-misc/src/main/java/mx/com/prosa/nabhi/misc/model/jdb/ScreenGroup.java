package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import us.gonet.nabhi.misc.model.jdb.blob.ButtonsAllowed;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de un grupo de pantallas " )
public class ScreenGroup implements Serializable {

    private static final long serialVersionUID = 1111L;

    @ApiModelProperty( value = "Nombre que identifica al grupo de pantallas", example = "B000_TEST_01" )
    private String groupId;
    @ApiModelProperty( value = "Nombre de la imagen de publicidad que mostrarán los cajeros asociados a este grupo", example = "Publicity_B000" )
    private String publicityName;
    @ApiModelProperty( value = "Nombre de la imagen de fondo que mostrarán los cajeros asociados a este grupo", example = "Background_B000" )
    private String backGround;
    @ApiModelProperty( value = "Estilo de los botones para los cajeros asociados a este grupo", example = "{\"style\":{\"color\":\"#000001\",\"backgroundColor\":\"transparent\",\"width\":\"90%\",\"height\":\"100px\",\"fontFamily\":\"Verdana\",\"borderRadius\":\"8px\",\"fontSize\":\"2.0rem\",\"border\":\"hidden 5px blue\",\"cursor\":\"Pointer\"},\"styleHover\":{\"color\":\"#000001\",\"backgroundColor\":\"transparent\",\"width\":\"90%\",\"height\":\"100px\",\"fontFamily\":\"Verdana\",\"borderRadius\":\"8px\",\"fontSize\":\"2.0rem\",\"border\":\"hidden 5px blue\",\"cursor\":\"Pointer\"},\"styleDisabled\":{\"color\":\"#000001\",\"backgroundColor\":\"transparent\",\"width\":\"90%\",\"height\":\"100px\",\"fontFamily\":\"Verdana\",\"borderRadius\":\"8px\",\"fontSize\":\"2.0rem\",\"border\":\"hidden 5px blue\",\"cursor\":\"Pointer\"}}" )
    private String buttonsStyle;
    @ApiModelProperty( value = "Estilo de la pantalla y fuentes para los cajeros asocaidos a este grupo", example = "{\"fontFamily\":\"Arial\",\"color\":\"#000000\",\"size\":0}" )
    private String bodyStyle;
    @ApiModelProperty( value = "Botones habilitados para cada pantalla", example = "" )
    private ButtonsAllowed buttonsAllowed;
    @ApiModelProperty( value = "ID de la Institución dueña del script", example = "B000" )
    private String fiid;

    public ScreenGroup() {
    }

    public ScreenGroup( String groupId ) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId( String groupId ) {
        this.groupId = groupId;
    }

    public String getPublicityName() {
        return publicityName;
    }

    public void setPublicityName( String publicityName ) {
        this.publicityName = publicityName;
    }

    public String getBackGround() {
        return backGround;
    }

    public void setBackGround( String backGround ) {
        this.backGround = backGround;
    }

    public String getButtonsStyle() {
        return buttonsStyle;
    }

    public void setButtonsStyle( String buttonsStyle ) {
        this.buttonsStyle = buttonsStyle;
    }

    public String getBodyStyle() {
        return bodyStyle;
    }

    public void setBodyStyle( String bodyStyle ) {
        this.bodyStyle = bodyStyle;
    }

    public ButtonsAllowed getButtonsAllowed() {
        return buttonsAllowed;
    }

    public void setButtonsAllowed( ButtonsAllowed buttonsAllowed ) {
        this.buttonsAllowed = buttonsAllowed;
    }

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
