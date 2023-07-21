package mx.com.prosa.nabhi.dash.batch.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

@JsonInclude( JsonInclude.Include.NON_NULL )
@JsonPropertyOrder( {
        "id institución",
        "nombre del banco",
        "código adquiriente",
        "razón social",
        "código pais",

} )
public class Institution implements Serializable {

    @JsonProperty( required = true, value = "id institución" )
    private String fiid;
    @JsonProperty( required = true, value = "nombre del banco" )
    private String name;
    @JsonProperty( required = true, value = "código adquiriente" )
    private String acquiringId;
    @JsonProperty( required = true, value = "razón social" )
    private String nameLong;
    @JsonProperty( value = "código pais" )
    private String countyCode;
    @JsonProperty( value = "red lógica" )
    private String logicalNet;

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getAcquiringId() {
        return acquiringId;
    }

    public void setAcquiringId( String acquiringId ) {
        this.acquiringId = acquiringId;
    }

    public String getNameLong() {
        return nameLong;
    }

    public void setNameLong( String nameLong ) {
        this.nameLong = nameLong;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode( String countyCode ) {
        this.countyCode = countyCode;
    }

    public String getLogicalNet() {
        return logicalNet;
    }

    public void setLogicalNet( String logicalNet ) {
        this.logicalNet = logicalNet;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }

}
