package mx.com.prosa.nabhi.misc.model.devices.ptr;
//Cambio release/redcat: Cambio de paquete

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.devices.IDevice;
import mx.com.prosa.nabhi.misc.model.devices.constants.ptr.*;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación del dispositivo de impresora del cajero" )
public class Printer implements IDevice, Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Indica si la impresora se encuentra disponible", example = "0" )
    private int status;
    @ApiModelProperty( value = "Estado del papel de la impresora", example = "FULL" )
    private PaperStatus paper;
    @ApiModelProperty( value = "Estado del toner de la impresora", example = "LOW" )
    private TonerStatus toner;
    @ApiModelProperty( value = "Estado del dispositivo según el estandar XFS", example = "ONLINE" )
    private PTRDeviceState deviceState;
    @ApiModelProperty( value = "Indica el error ocurrido en el dispositivo según el estandar XFS en caso de haber uno", example = "SHUTTERFAIL" )
    private PTRError error;
    @ApiModelProperty( value = "Estado del dispensador de la impresora", example = "PAPERLOWER" )
    private SupplyReplenish supply;


    @Override
    public int getStatus() {
        return status;
    }

    public void setStatus( int status ) {
        this.status = status;
    }

    public String getPaper() {
        return paper.name();
    }

    public void setPaper( String paper ) {
        this.paper = PaperStatus.valueOfCompose( paper );
    }

    public String getToner() {
        return toner.name();
    }

    public void setToner( String toner ) {
        this.toner = TonerStatus.valueOfCompose( toner );
    }

    public String getDeviceState() {
        return deviceState.name();
    }

    public void setDeviceState( String deviceState ) {
        this.deviceState = PTRDeviceState.valueOfCompose( deviceState );
    }

    public String getError() {
        return error.name();
    }

    public void setError( String error ) {
        this.error = PTRError.valueOfCompose( error );
    }

    public String getSupply() {
        return supply.name();
    }

    public void setSupply( String supply ) {
        this.supply = SupplyReplenish.valueOfCompose( supply );
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
