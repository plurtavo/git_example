package mx.com.prosa.nabhi.misc.model.jse.request;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representación de una solicitud de traspaso" )
public class GenericWithToAccount extends CashWithdrawalModel{

    @ApiModelProperty( value = "Cuenta destino", example = "20" )
    private String toAccount;
    @ApiModelProperty( value = "Número de cuenta", example = "123465789123465789" )
    private String toAccountP103;

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount( String toAccount ) {
        this.toAccount = toAccount;
    }

    public String getToAccountP103() {
        return toAccountP103;
    }

    public void setToAccountP103( String toAccountP103 ) {
        this.toAccountP103 = toAccountP103;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
