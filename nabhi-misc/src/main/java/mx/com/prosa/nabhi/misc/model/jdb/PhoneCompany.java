package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de una compañia telefonica" )
public class PhoneCompany implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Nombre de la compañia", example = "MOVISTAR" )
    private String name;
    @ApiModelProperty( value = "Cargo por venta de tiempo aire", example = "10.00" )
    private double surcharge;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public double getSurcharge() {
        return surcharge;
    }

    public void setSurcharge( double surcharge ) {
        this.surcharge = surcharge;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
