package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.jdb.onlykeys.StateKey;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de un municipio" )
public class County implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "ID autoincrementable ", example = "1" )
    private int countyCodeId;
    @ApiModelProperty( value = "ID númerico del municipio", example = "003" )
    private String countyCode;
    @ApiModelProperty( value = "Nombre del municipio", example = "COYOACAN" )
    private String countyName;
    @ApiModelProperty( value = "ID númerico del estado a donde pertenece el municipio", example = "09" )
    private StateKey state;

    public County( int countyCodeId ) {
        this.countyCodeId = countyCodeId;
    }

    public County( String countyCode ) {
        this.countyCode = countyCode;
    }

    public County() {
    }

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

    public StateKey getState() {
        return state;
    }

    public void setState( StateKey state ) {
        this.state = state;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
