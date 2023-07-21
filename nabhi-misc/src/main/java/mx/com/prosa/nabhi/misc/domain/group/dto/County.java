package mx.com.prosa.nabhi.misc.domain.group.dto;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de un municipio" )
public class County implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "ID del municipio", example = "183" )
    private int countyCodeId;
    @ApiModelProperty( value = "Código ANSI del municipio", example = "123" )
    private String countyCode;
    @ApiModelProperty( value = "Nombre del municipio", example = "MIGUEL HIDALGO" )
    private String countyName;
    @ApiModelProperty( value = "Estado de la republica al cual pertenece el municipio" )
    private State state;

    public int getCountyCodeId() {
        return countyCodeId;
    }

    public void setCountyCodeId( int countyCodeId ) {
        this.countyCodeId = countyCodeId;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode( String countyCode ) {
        this.countyCode = countyCode;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName( String countyName ) {
        this.countyName = countyName;
    }

    public State getState() {
        return state;
    }

    public void setState( State state ) {
        this.state = state;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
