package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de la configuración del menu de la consola" )
public class MenuObject implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "ID de la sección de la consola Nabhi", example = "1" )
    private int id;
    @ApiModelProperty( value = "Nombre de la sección de la consola Nabhi", example = "Cajeros" )
    private String menu;
    @ApiModelProperty( value = "Parametros de confiruación de la sección de la consola Nabhi", example = "[{interval:60}]" )
    private String params;

    public MenuObject() {
    }

    public MenuObject( int id, String menu, String params ) {
        this.id = id;
        this.menu = menu;
        this.params = params;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu( String menu ) {
        this.menu = menu;
    }

    public String getParams() {
        return params;
    }

    public void setParams( String params ) {
        this.params = params;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
