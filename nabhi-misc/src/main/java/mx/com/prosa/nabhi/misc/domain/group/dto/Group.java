package mx.com.prosa.nabhi.misc.domain.group.dto;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel( description = "Json DTO representación de grupos de cajeros" )
public class Group implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "ID del grupo", example = "1" )
    private int id;
    @ApiModelProperty( value = "Nombre del grupo", example = "Cajeros del Norte" )
    private String name;
    @ApiModelProperty( value = "Descripción del grupo", example = "Cajero VIP de la región norte" )
    private String description;
    @ApiModelProperty( value = "IDF dueña del grupo", example = "B000" )
    private IDF idf;
    @ApiModelProperty( value = "Lista de Cajero pertenecientes al grupo" )
    private List < ATD > atds;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public IDF getIdf() {
        return idf;
    }

    public void setIdf( IDF idf ) {
        this.idf = idf;
    }

    public List < ATD > getAtds() {
        return atds;
    }

    public void setAtds( List < ATD > atds ) {
        this.atds = atds;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
