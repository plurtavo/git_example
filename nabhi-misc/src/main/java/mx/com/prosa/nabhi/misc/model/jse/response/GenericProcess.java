package mx.com.prosa.nabhi.misc.model.jse.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representación una respuesta de autorizción de transacción" )
public class GenericProcess {

    @ApiModelProperty( value = "Código de respuesta", example = "200" )
    private String status;
    @ApiModelProperty( value = "Respuesta de autoriación", example = "Transacción autorizada" )
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "GenericProcess{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
