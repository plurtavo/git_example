package mx.com.prosa.nabhi.misc.model.reversal;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representación de una solicitud de reverso" )
public class ATMReversalModel {

    @ApiModelProperty( value = "Cadena ISO de la transacción a reversar", example = "ISO0160000500210B23062300003139375470464958812131=1703201000008030000000000000000499494100ABCCAP!" )
    private String message;
    @ApiModelProperty( value = "Código de reverso", example = "NO_ACTION_TAKEN" )
    private String reversalCode;
    @ApiModelProperty( value = "Monto a reversar", example = "250.00" )
    private int dispensedAmount;

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public String getReversalCode() {
        return reversalCode;
    }

    public void setReversalCode( String reversalCode ) {
        this.reversalCode = reversalCode;
    }

    public int getDispensedAmount() {
        return dispensedAmount;
    }

    public void setDispensedAmount( int dispensedAmount ) {
        this.dispensedAmount = dispensedAmount;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
