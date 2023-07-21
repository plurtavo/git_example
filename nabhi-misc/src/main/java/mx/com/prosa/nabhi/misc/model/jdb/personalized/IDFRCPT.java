package mx.com.prosa.nabhi.misc.model.jdb.personalized;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.jdb.RCPT;

import java.io.Serializable;
import java.util.List;

@ApiModel( description = "Json DTO representación de un IDF (Para consultar APC)" )
public class IDFRCPT implements Serializable {

    @ApiModelProperty( value = "ID del FIID", example = "B000" )
    private String fiid;
    @ApiModelProperty( value = "Nombre de la institución", example = "Prosa" )
    private String name;
    @ApiModelProperty( value = "Estilos de recibos pertenecientes a la institución", example = "" )
    private List < RCPT > rcpt;


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

    public List < RCPT > getRcpt() {
        return rcpt;
    }

    public void setRcpt( List < RCPT > rcpt ) {
        this.rcpt = rcpt;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }

}
