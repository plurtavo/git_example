package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de un BIN" )
public class Prefix implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Llave primaria", example = "1" )
    private int id;
    @ApiModelProperty( value = "Prefijo", example = "491566" )
    private String bin;
    @ApiModelProperty( value = "Longitud del PAN", example = "16" )
    private int panLen;
    @ApiModelProperty( value = "Longitud del Prefijo", example = "6" )
    private int binLen;
    @ApiModelProperty( value = "IDF dueño del Prefijo", example = "B000" )
    private String fiid;
    @ApiModelProperty( value = "Descripción del Prefijo", example = "Debito Banorte Visa Electron" )
    private String description;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getBin() {
        return bin;
    }

    public void setBin( String bin ) {
        this.bin = bin;
    }

    public int getPanLen() {
        return panLen;
    }

    public void setPanLen( int panLen ) {
        this.panLen = panLen;
    }

    public int getBinLen() {
        return binLen;
    }

    public void setBinLen( int binLen ) {
        this.binLen = binLen;
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
