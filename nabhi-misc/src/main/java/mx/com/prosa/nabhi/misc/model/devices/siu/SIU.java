package mx.com.prosa.nabhi.misc.model.devices.siu;
//Cambio release/redcat: Cambio de paquete

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.devices.IDevice;
import mx.com.prosa.nabhi.misc.model.devices.constants.pin.DeviceState;
import mx.com.prosa.nabhi.misc.model.devices.constants.siu.CabinetDoor;
import mx.com.prosa.nabhi.misc.model.devices.constants.siu.OperatorMode;
import mx.com.prosa.nabhi.misc.model.devices.constants.siu.SIUError;
import mx.com.prosa.nabhi.misc.model.devices.constants.siu.SafeDoor;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación del dispositivo SIU del cajero" )
public class SIU implements IDevice, Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Indica si el dispositivo se encuentra disponible", example = "0" )
    private int status;
    @ApiModelProperty( value = "Estado de la gabeta", example = "CLOSED" )
    private CabinetDoor cabinetDoor;
    @ApiModelProperty( value = "Estado de la boveda de seguridad", example = "OPEN" )
    private SafeDoor safeDoor;
    @ApiModelProperty( value = "Estado del switch de modo operador/supervisor", example = "SUPERVISOR" )
    private OperatorMode mode;
    @ApiModelProperty( value = "Estado del dispositivo según el estandar XFS", example = "OFFLINE" )
    private DeviceState deviceState;
    @ApiModelProperty( value = "Indica el error ocurrido en el dispositivo en caso de haber uno según ele standar XFS", example = "PORT_ERROR" )
    private SIUError error;

    @Override
    public int getStatus() {
        return status;
    }

    public void setStatus( int status ) {
        this.status = status;
    }

    public String getCabinetDoor() {
        return cabinetDoor.name();
    }

    public void setCabinetDoor( String cabinetDoor ) {
        this.cabinetDoor = CabinetDoor.valueOfCompose( cabinetDoor );
    }

    public String getSafeDoor() {
        return safeDoor.name();
    }

    public void setSafeDoor( String safeDoor ) {
        this.safeDoor = SafeDoor.valueOfCompose( safeDoor );
    }

    public String getMode() {
        return mode.name();
    }

    public void setMode( String mode ) {
        this.mode = OperatorMode.valueOfCompose( mode );
    }

    public String getDeviceState() {
        return deviceState.name();
    }

    public void setDeviceState( String deviceState ) {
        this.deviceState = DeviceState.valueOfCompose( deviceState );
    }

    public String getError() {
        return error.name();
    }

    public void setError( String error ) {
        this.error = SIUError.valueOfCompose( error );
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
