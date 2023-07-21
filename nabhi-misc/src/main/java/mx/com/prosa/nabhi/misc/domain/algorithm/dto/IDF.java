package mx.com.prosa.nabhi.misc.domain.algorithm.dto;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de una institución" )
public class IDF implements Serializable {

    @ApiModelProperty( value = "FIID de la institución", example = "B000" )
    private String fiid;

    public IDF() {
    }

    public IDF( String fiid ) {
        this.fiid = fiid;
    }

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }

}
