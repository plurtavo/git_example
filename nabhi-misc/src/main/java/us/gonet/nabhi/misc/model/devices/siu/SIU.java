package us.gonet.nabhi.misc.model.devices.siu;

import com.google.gson.GsonBuilder;
import us.gonet.nabhi.misc.model.devices.IDevice;
import us.gonet.nabhi.misc.model.devices.constants.pin.DeviceState;
import us.gonet.nabhi.misc.model.devices.constants.siu.CabinetDoor;
import us.gonet.nabhi.misc.model.devices.constants.siu.OperatorMode;
import us.gonet.nabhi.misc.model.devices.constants.siu.SIUError;
import us.gonet.nabhi.misc.model.devices.constants.siu.SafeDoor;

import java.io.Serializable;

public class SIU implements IDevice, Serializable {

    private static final long serialVersionUID = 23453246534L;

    private int status;
    private CabinetDoor cabinetDoor;
    private SafeDoor safeDoor;
    private OperatorMode mode;
    private DeviceState deviceState;
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
