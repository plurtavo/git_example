package mx.com.prosa.nabhi.misc.model.billing;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel( description = "Json DTO representación de un reporte de cajero" )
public class ATMReport {

    @ApiModelProperty( value = "Modelo que contiene todos los detalles del banco", example = "" )
    private List < BankDetail > bankDetails;

    @ApiModelProperty( value = "", example = "" )
    public List < BankDetail > getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails( List < BankDetail > bankDetails ) {
        this.bankDetails = bankDetails;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
