package mx.com.prosa.nabhi.misc.model.jdb;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de la configuración del menu de la consola" )
public class MenuTransfer implements Serializable, Comparable < MenuTransfer > {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Id del menu", example = "1" )
    private int id;
    @ApiModelProperty( value = "Nombre de la sección de la consola Nabhi", example = "Cajeros" )
    private String menu;
    @ApiModelProperty( value = "Parametros de confiruación de la sección de la consola Nabhi", example = "[{interval:60}]" )
    private String params;

    public MenuTransfer() {
    }

    public MenuTransfer( int id, String menu, String params ) {
        this.id = id;
        this.menu = menu;
        this.params = params;
        if ( params == null ){
            this.params = "";
        }
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
    public int compareTo( MenuTransfer o ) {
        return ( this.id - o.getId() );
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MenuTransfer other = (MenuTransfer) obj;
        return id == other.id;
    }

}
