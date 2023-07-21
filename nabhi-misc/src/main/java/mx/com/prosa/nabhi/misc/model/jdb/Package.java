package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel( description = "Json DTO representación de un paquete de negocio de Nabhi" )
public class Package implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Nombre del paquete", example = "BASICO" )
    private String name;
    @ApiModelProperty( value = "Lista de transacciónes disponibles para el paquete", example = "{RETIRO,VENTA GENERICA}" )
    private List < Component > components;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public List < Component > getComponents() {
        return components;
    }

    public void setComponents( List < Component > components ) {
        this.components = components;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
