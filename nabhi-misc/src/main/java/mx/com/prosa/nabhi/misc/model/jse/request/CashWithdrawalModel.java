package mx.com.prosa.nabhi.misc.model.jse.request;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representación de una solicitud de retiro" )
public class CashWithdrawalModel extends Generic {

    @ApiModelProperty( value = "Monto a retirar", example = "200" )
    private String cashWithAmount;

    public String getCashWithAmount() {
        return cashWithAmount;
    }

    public void setCashWithAmount( String cashWithAmount ) {
        this.cashWithAmount = cashWithAmount;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
