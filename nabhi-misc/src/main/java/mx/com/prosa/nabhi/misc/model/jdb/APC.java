package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de APCs" )
public class APC implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Llave primaria", example = "01" )
    private int id;
    @ApiModelProperty( value = "Código de transacción", example = "01" )
    private String tranCode;
    @ApiModelProperty( value = "Tipo de cuenta origen", example = "10" )
    private String formAcct;
    @ApiModelProperty( value = "Tipo de cuenta destino", example = "30" )
    private String toAcct;
    @ApiModelProperty( value = "Fiid Adquiriente", example = "B000" )
    private String fiid;
    @ApiModelProperty( value = "GROUP Adquiriente", example = "00000000139" )
    private String routingGroup;
    @ApiModelProperty( value = "GROUP Adquiriente", example = "APC           " )
    private String sharingGroup;
    @ApiModelProperty( value = "Allowed Code de la transacción", example = "4" )
    private TranAllowed allowedCode;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode( String tranCode ) {
        this.tranCode = tranCode;
    }

    public String getFormAcct() {
        return formAcct;
    }

    public void setFormAcct( String formAcct ) {
        this.formAcct = formAcct;
    }

    public String getToAcct() {
        return toAcct;
    }

    public void setToAcct( String toAcct ) {
        this.toAcct = toAcct;
    }

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    public String getRoutingGroup() {
        return routingGroup;
    }

    public void setRoutingGroup( String routingGroup ) {
        this.routingGroup = routingGroup;
    }

    public String getSharingGroup() {
        return sharingGroup;
    }

    public void setSharingGroup( String sharingGroup ) {
        this.sharingGroup = sharingGroup;
    }

    public TranAllowed getAllowedCode() {
        return allowedCode;
    }

    public void setAllowedCode( TranAllowed allowedCode ) {
        this.allowedCode = allowedCode;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
