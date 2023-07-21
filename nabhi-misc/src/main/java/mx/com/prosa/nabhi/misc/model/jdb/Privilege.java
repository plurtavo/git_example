package mx.com.prosa.nabhi.misc.model.jdb;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de un privilegio de usuario" )
public class Privilege implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    public Privilege() {
    }

    public Privilege( String name ) {
        this.name = name;
    }

    @ApiModelProperty( value = "Nombre del privilegio", example = "create-atd" )
    private String name;
    //Cambio release/eventos
    @ApiModelProperty( value = "Descripción del privelegio que indica los permisos que tiene", example = "Permite crear un cajero" )
    private String description;
    @ApiModelProperty( value = "Indica si el privilegio es para usuarios de consola o para cajeros: Falso es para consola y Verdadero para Cajero", example = "true" )
    private boolean app;
    //Cambio release/eventos

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    //Cambio release/eventos
    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public boolean isApp() {
        return app;
    }

    public void setApp( boolean app ) {
        this.app = app;
    }
    //Cambio release/eventos
}
