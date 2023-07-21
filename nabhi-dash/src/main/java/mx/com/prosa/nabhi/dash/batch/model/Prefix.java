package mx.com.prosa.nabhi.dash.batch.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.GsonBuilder;


@JsonInclude( JsonInclude.Include.NON_NULL )
@JsonPropertyOrder( {
        "id",
        "bin",
        "longitud del bin",
        "longitud del pan",
        "id institución",
        "descripción",


} )
public class Prefix {

    @JsonProperty( value = "id" )
    private String id;
    @JsonProperty( required = true, value = "bin" )
    private String bin;
    @JsonProperty( required = true, value = "longitud del bin" )
    private int binLen;
    @JsonProperty( required = true, value = "longitud del pan" )
    private int panLen;
    @JsonProperty( required = true, value = "id institución" )
    private String fiid;
    @JsonProperty( required = true, value = "descripción" )
    private String description;

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getBin() {
        return bin;
    }

    public void setBin( String bin ) {
        this.bin = bin;
    }

    public int getBinLen() {
        return binLen;
    }

    public void setBinLen( int binLen ) {
        this.binLen = binLen;
    }

    public int getPanLen() {
        return panLen;
    }

    public void setPanLen( int panLen ) {
        this.panLen = panLen;
    }

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}