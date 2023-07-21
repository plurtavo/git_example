package mx.com.prosa.nabhi.misc.model.jdb.onlykeys;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de una institución" )
public class IDFKey implements Serializable {

    @ApiModelProperty( value = "FIID código de la institución", example = "B000" )
    private String fiid;
    @ApiModelProperty( value = "Nombre de la institución", example = "B000" )
    private String name;

    public IDFKey() {
    }

    public IDFKey( String fiid ) {
        this.fiid = fiid;
    }

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

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }

}
