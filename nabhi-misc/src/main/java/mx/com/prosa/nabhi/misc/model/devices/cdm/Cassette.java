package mx.com.prosa.nabhi.misc.model.devices.cdm;
//Cambio release/redcat: Cambio de paquete

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.devices.constants.cdm.cassette.CassetteType;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de una casetera del cajero" )
public class Cassette implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Indice de la casetera", example = "1" )
    private int cassetteIndex;
    @ApiModelProperty( value = "Tipo de moneda según estandar ISO-4217", example = "MXN" )
    private String currency;
    @ApiModelProperty( value = "Denominación de la casetera", example = "200" )
    private int denomination;
    @ApiModelProperty( value = "Tipo de casetera según estandar XFS", example = "BILLCASSETTE" )
    private CassetteType type;
    //Cambio release/redcat
    @ApiModelProperty( value = "Casetera logica", example = "" )
    private Logical logical;
    @ApiModelProperty( value = "Casetera fisica", example = "" )
    private Physical physical;
    //Cambio release/redcat

    public Cassette() {
    }

    public Cassette( int def, Logical logical, Physical physical ) {
        this.cassetteIndex = def;
        this.currency = "MXN";
        this.denomination = def;
        this.type = CassetteType.NA;
        this.logical = logical;
        this.physical = physical;
    }

    public int getCassetteIndex() {
        return cassetteIndex;
    }

    public void setCassetteIndex( int cassetteIndex ) {
        this.cassetteIndex = cassetteIndex;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency( String currency ) {
        this.currency = currency;
    }

    public int getDenomination() {
        return denomination;
    }

    public void setDenomination( int denomination ) {
        this.denomination = denomination;
    }

    public String getType() {
        return type.name();
    }

    public void setType( String type ) {
        this.type = CassetteType.valueOfCompose( type );
    }

    public Logical getLogical() {
        return logical;
    }

    public void setLogical( Logical logical ) {
        this.logical = logical;
    }

    public Physical getPhysical() {
        return physical;
    }

    public void setPhysical( Physical physical ) {
        this.physical = physical;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
