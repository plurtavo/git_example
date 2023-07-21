package mx.com.prosa.nabhi.misc.model.adp;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representación del modelo de datos de retiro" )
public class BillsModel {

    @ApiModelProperty( value = "Buffer que indican la cantidad de billetes a dispensar", example = "0001000203" )
    private String bills;
    @ApiModelProperty( value = "Indica si hay cargo por uso de línea de crédito", example = "true" )
    private boolean feeCreditLine;
    @ApiModelProperty( value = "Indica el banco emisor", example = "B001" )
    private String issuing;
    @ApiModelProperty( value = "Muestra el cargo por uso de línea de crédito", example = "10.05" )
    private String fee;

    public String getBills() {
        return bills;
    }

    public void setBills( String bills ) {
        this.bills = bills;
    }

    public boolean getFeeCreditLine() {
        return feeCreditLine;
    }

    public void setFeeCreditLine( boolean feeCreditLine ) {
        this.feeCreditLine = feeCreditLine;
    }

    public String getIssuing() {
        return issuing;
    }

    public void setIssuing( String issuing ) {
        this.issuing = issuing;
    }

    public String getFee() {
        return fee;
    }

    public void setFee( String fee ) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
