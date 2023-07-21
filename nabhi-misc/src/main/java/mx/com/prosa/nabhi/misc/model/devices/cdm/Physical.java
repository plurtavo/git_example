package mx.com.prosa.nabhi.misc.model.devices.cdm;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.devices.constants.cdm.cassette.CassetteStatus;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de una casetera fisica" )
public class Physical implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Cuenta inicial de unidades luego de dotar", example = "3000" )
    private int initialCount;
    @ApiModelProperty( value = "Cantidad de billetes retirados", example = "200" )
    private int decrement;
    @ApiModelProperty( value = "Cantidad de unidades actuales en la casetera", example = "2500" )
    private int current;
    @ApiModelProperty( value = "Cantidad de unidades dispensadas", example = "500" )
    private int dispensed;
    @ApiModelProperty( value = "Cantidad de unidades depositadas", example = "0" )
    private int deposited;
    @ApiModelProperty( value = "Cantidad de unidades enviadas a la casetera de rechazos", example = "50" )
    private int rejected;
    @ApiModelProperty( value = "Cantidad de unidades retraidas del shutter a casetera", example = "10" )
    private int retracted;
    @ApiModelProperty( value = "Cantidad de unidades entregadas al usuario", example = "440" )
    private int presented;
    @ApiModelProperty( value = "Estado de la casetera según el estandar XFS", example = "LOW" )
    private CassetteStatus status;

    public Physical() {
    }

    public Physical( int def ) {
        this.initialCount = def;
        this.decrement = def;
        this.current = def;
        this.dispensed = def;
        this.deposited = def;
        this.rejected = def;
        this.retracted = def;
        this.status = CassetteStatus.MISSING;
    }

    public int getInitialCount() {
        return initialCount;
    }

    public void setInitialCount( int initialCount ) {
        this.initialCount = initialCount;
    }

    public int getDecrement() {
        return decrement;
    }

    public void setDecrement( int decrement ) {
        this.decrement = decrement;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent( int current ) {
        this.current = current;
    }

    public int getDispensed() {
        return dispensed;
    }

    public void setDispensed( int dispensed ) {
        this.dispensed = dispensed;
    }

    public int getDeposited() {
        return deposited;
    }

    public void setDeposited( int deposited ) {
        this.deposited = deposited;
    }

    public int getRejected() {
        return rejected;
    }

    public void setRejected( int rejected ) {
        this.rejected = rejected;
    }

    public int getRetracted() {
        return retracted;
    }

    public void setRetracted( int retracted ) {
        this.retracted = retracted;
    }

    public String getStatus() {
        return status.name();
    }

    public void setStatus( String status ) {
        this.status = CassetteStatus.valueOfCompose( status );
    }

    public int getPresented() {
        return presented;
    }

    public void setPresented( int presented ) {
        this.presented = presented;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
