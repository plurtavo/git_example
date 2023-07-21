package mx.com.prosa.nabhi.misc.model.jdb.composite;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de un ID de comisión" )
public class SurchargeId implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "FIID adquriente", example = "B000" )
    private String fiidAcquirer;
    @ApiModelProperty( value = "FIID emisor", example = "B001" )
    private String fiidIssuing;
    @ApiModelProperty( value = "Código de transacción", example = "01" )
    private String tranCode;

    public SurchargeId( String fiidAcquirer, String fiidIssuing, String tranCode ) {
        this.fiidAcquirer = fiidAcquirer;
        this.fiidIssuing = fiidIssuing;
        this.tranCode = tranCode;
    }

    public SurchargeId() {
    }

    public String getFiidAcquirer() {
        return fiidAcquirer;
    }

    public void setFiidAcquirer( String fiidAcquirer ) {
        this.fiidAcquirer = fiidAcquirer;
    }

    public String getFiidIssuing() {
        return fiidIssuing;
    }

    public void setFiidIssuing( String fiidIssuing ) {
        this.fiidIssuing = fiidIssuing;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode( String tranCode ) {
        this.tranCode = tranCode;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
