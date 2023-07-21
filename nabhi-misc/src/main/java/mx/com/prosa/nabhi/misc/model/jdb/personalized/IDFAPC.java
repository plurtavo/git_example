package mx.com.prosa.nabhi.misc.model.jdb.personalized;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.jdb.APC;

import java.io.Serializable;
import java.util.List;

@ApiModel( description = "Json DTO representación de un IDF (Para consultar APC)" )
public class IDFAPC implements Serializable {

    @ApiModelProperty( value = "ID del FIID", example = "B000" )
    private String fiid;
    @ApiModelProperty( value = "Nombre de la institución", example = "Prosa" )
    private String name;
    @ApiModelProperty( value = "GROUP Adquiriente de la institución", example = "" )
    private List < APC > apcs;

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

    public List < APC > getApcs() {
        return apcs;
    }

    public void setApcs( List < APC > apcs ) {
        this.apcs = apcs;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }

}
