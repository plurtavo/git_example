package mx.com.prosa.nabhi.misc.model.sockets;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representaciónde la soclitur de información de un Host" )
public class HostServiceInfo {

    private static final long serialVersionUID = 8799656478674716638L;

    @ApiModelProperty( value = "Código de respuesta", example = "200" )
    private String code;
    @ApiModelProperty( value = "Mensaje de respuesta de info", example = "" )
    private String message;

    public HostServiceInfo() {
    }

    public HostServiceInfo( String code, String message ) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
