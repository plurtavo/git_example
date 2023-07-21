package mx.com.prosa.nabhi.misc.model.jdb;


import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel( description = "Json DTO representación de un estado" )
public class State implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Código numerico de estado", example = "09" )
    private String stateCode;
    @ApiModelProperty( value = "Nombre del estado", example = "CIUDAD DE MÉXICO" )
    private String stateName;
    @ApiModelProperty( value = "Nombre corto del estado", example = "DIF" )
    private String stateShortName;
    @ApiModelProperty( value = "Zona horaria", example = "America/Mexico_City" )
    private String zone;
    @ApiModelProperty( value = "Lista de municipios o delegaciones ", example = "{COYOACA, XOCHIMILCO}" )
    private List < County > counties;

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode( String stateCode ) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName( String stateName ) {
        this.stateName = stateName;
    }

    public String getStateShortName() {
        return stateShortName;
    }

    public void setStateShortName( String stateShortName ) {
        this.stateShortName = stateShortName;
    }

    public String getZone() {
        return zone;
    }

    public void setZone( String zone ) {
        this.zone = zone;
    }

    public List < County > getCounties() {
        return counties;
    }

    public void setCounties( List < County > counties ) {
        this.counties = counties;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
