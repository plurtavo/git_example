package mx.com.prosa.nabhi.dash.batch.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.GsonBuilder;

import java.io.Serializable;


@JsonInclude( JsonInclude.Include.NON_NULL )
@JsonPropertyOrder( {
        "id",
        "código de transacción",
        "id institución",
        "tipo cuenta origen",
        "tipo cuenta destino",
        "grupo de enrutamiento adquiriente",
        "grupo de compartimiento adquiriente",
        "nivel de autorización"

} )
public class AcquirerProfile implements Serializable {

    @JsonProperty( value = "id" )
    private String id;
    @JsonProperty( required = true, value = "código de transacción" )
    private String tranCode;
    @JsonProperty( required = true, value = "id institución" )
    private String fiid;
    @JsonProperty( required = true, value = "tipo cuenta origen" )
    private String formAcct;
    @JsonProperty( value = "tipo cuenta destino" )
    private String toAcct;
    @JsonProperty( required = true, value = "grupo de enrutamiento adquiriente" )
    private String routingGroup;
    @JsonProperty( required = true, value = "grupo de compartimiento adquiriente" )
    private String sharingGroup;
    @JsonProperty( value = "nivel de autorización" )
    private String allowedCode;

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode( String tranCode ) {
        this.tranCode = tranCode;
    }

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
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

    public String getAllowedCode() {
        return allowedCode;
    }

    public void setAllowedCode( String allowedCode ) {
        this.allowedCode = allowedCode;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}