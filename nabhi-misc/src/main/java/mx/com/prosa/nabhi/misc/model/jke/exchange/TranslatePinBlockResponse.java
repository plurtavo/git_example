package mx.com.prosa.nabhi.misc.model.jke.exchange;

import com.google.gson.Gson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representación de una respuesta de solcitud de traslado de pinblock" )
public class TranslatePinBlockResponse implements KeyResponse {

    @ApiModelProperty( value = "Classe de solicitud", example = "9" )
    private String clazz;
    @ApiModelProperty( value = "Código de error", example = "0" )
    private String error;
    @ApiModelProperty( value = "Nuevo Pinblock", example = "0C59E751F2C6228A" )
    private String pinBlock;
    @ApiModelProperty( value = "Valor del IPK", example = "000000000000" )
    private String ipk;
    @ApiModelProperty( value = "Número de secuencia de solicitud", example = "3" )
    private String sequence;
    @ApiModelProperty( value = "Modo de traslado", example = "" )
    private String mode;

    public TranslatePinBlockResponse() {
    }

    public TranslatePinBlockResponse( String clazz, String error, String pinBlock, String ipk, String sequence ) {
        this.clazz = clazz;
        this.error = error;
        this.pinBlock = pinBlock;
        this.ipk = ipk;
        this.sequence = sequence;
    }

    public String getClazz() {
        return clazz;
    }

    public String getTPK() {
        return ipk;
    }

    @Override
    public String getMODE() {
        return mode;
    }

    @Override
    public String getBLOCK() {
        return pinBlock;
    }

    @Override
    public void setError( String error ) {
        this.error = error;
    }

    @Override
    public String getSequence() {
        return sequence;
    }

    public String gerError() {
        return error;
    }

    @Override
    public String toString() {
        return new Gson().toJson( this );
    }
}
