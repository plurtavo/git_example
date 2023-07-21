package mx.com.prosa.nabhi.misc.model.jke.exchange;

import com.google.gson.Gson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representación de una respuesta de solcitud de llave" )
public class KeyExchangeResponse implements KeyResponse {

    @ApiModelProperty( value = "Clase de solictud", example = "9" )
    private String clazz;
    @ApiModelProperty( value = "Código de error", example = "0" )
    private String error;
    @ApiModelProperty( value = "Tipo de encriptado", example = "0" )
    private String encryptType;
    @ApiModelProperty( value = "Modo de traslado", example = "" )
    private String mode;
    @ApiModelProperty( value = "Número de secuencia de solicitud", example = "" )
    private String sequence;
    @ApiModelProperty( value = "", example = "" )
    private String block;

    public KeyExchangeResponse() {
    }

    public KeyExchangeResponse( String clazz, String error, String encryptType, String mode, String sequence ) {
        this.clazz = clazz;
        this.error = error;
        this.encryptType = encryptType;
        this.mode = mode;
        this.sequence = sequence;
    }

    public String getClazz() {
        return clazz;
    }

    public String getEncryptType() {
        return encryptType;
    }

    @Override
    public String getSequence() {
        return sequence;
    }

    @Override
    public String getMODE() {
        return mode;
    }

    public String getError() {
        return error;
    }

    @Override
    public String getBLOCK() {
        return block;
    }

    @Override
    public void setError( String error ) {
        this.error = error;
    }

    @Override
    public String toString() {
        return new Gson().toJson( this );
    }

}
