package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel( description = "Json DTO representación de de un país" )
public class Country implements Serializable {

    private static final long serialVersionUID = 23453246534L;
    @ApiModelProperty( value = "Código númerico de pais en estandar  ISO 3166-1", example = "484" )
    private String countryCode;
    @ApiModelProperty( value = "Código de pais de 2 letras en estandar  ISO 3166-1", example = "MX" )
    private String alpha2;
    @ApiModelProperty( value = "Código de pais de 3 letras en estandar  ISO 3166-1", example = "MXN" )
    private String alpha3;
    @ApiModelProperty( value = "Nombre del país", example = "México" )
    private String name;
    @ApiModelProperty( value = "Simbolo de moneda del país", example = "$" )
    private String symbols;
    @ApiModelProperty( value = "Lista de estados del país", example = "MORELOS" )
    private List < State > states;

    public Country( String countryCode ) {
        this.countryCode = countryCode;
    }

    public Country() {
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode( String countryCode ) {
        this.countryCode = countryCode;
    }

    public String getAlpha2() {
        return alpha2;
    }

    public void setAlpha2( String alpha2 ) {
        this.alpha2 = alpha2;
    }

    public String getAlpha3() {
        return alpha3;
    }

    public void setAlpha3( String alpha3 ) {
        this.alpha3 = alpha3;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getSymbols() {
        return symbols;
    }

    public void setSymbols( String symbols ) {
        this.symbols = symbols;
    }

    public List < State > getStates() {
        return states;
    }

    public void setStates( List < State > states ) {
        this.states = states;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
