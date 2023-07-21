package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel( description = "Json DTO representación de un rol de Nabhi " )
public class Role implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    public Role() {
    }

    public Role( String name ) {
        this.name = name;
    }

    @ApiModelProperty( value = "Nombre del rol", example = "SEGURIDAD" )
    private String name;

    @ApiModelProperty( value = "Lista de privielgios que tiene este rol", example = "{user-security,read-idf,read-type}" )
    private List < Privilege > privileges;
    //Cambio release/eventos
    @ApiModelProperty( value = "Lista de secciones de la consola que puede visualizar", example = "{IDF, Control de accesos}" )
    private List< MenuObject > menus;
    //Cambio release/eventos

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public List < Privilege > getPrivileges() {
        return privileges;
    }

    public void setPrivileges( List < Privilege > privileges ) {
        this.privileges = privileges;
    }

    //Cambio release/eventos
    public List < MenuObject > getMenus() {
        return menus;
    }

    public void setMenus( List < MenuObject > menus ) {
        this.menus = menus;
    }
    //Cambio release/eventos
    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
