package mx.com.prosa.nabhi.misc.model.jse.request;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representación de una solicitud de cambio de NIP" )
public class ChangeNipModel extends Generic {

    @ApiModelProperty( value = "Nuevo nip encriptado", example = "2915BEDAA4B8A6B8" )
    private String newPin;
    @ApiModelProperty( value = "Confirmación de nuevo nip encriptado", example = "2915BEDAA4B8A6B8" )
    private String confirmNewPin;

    public String getNewPin() {
        return newPin;
    }

    public void setNewPin( String newPin ) {
        this.newPin = newPin;
    }

    public String getConfirmNewPin() {
        return confirmNewPin;
    }

    public void setConfirmNewPin( String confirmNewPin ) {
        this.confirmNewPin = confirmNewPin;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
