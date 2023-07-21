package us.gonet.nabhi.misc.model.devices.cdm;

import com.google.gson.GsonBuilder;
import us.gonet.nabhi.misc.model.devices.IDevice;
import us.gonet.nabhi.misc.model.devices.constants.cdm.CDMDeviceState;
import us.gonet.nabhi.misc.model.devices.constants.cdm.CDMError;
import us.gonet.nabhi.misc.model.devices.constants.cdm.DispenserStatus;
import us.gonet.nabhi.misc.model.devices.constants.cdm.Shutter;

import java.io.Serializable;
import java.util.List;

public class Dispenser implements IDevice, Serializable {

    private static final long serialVersionUID = 23453246534L;
    private int status;
    private List < Cassette > cassettes;
    private CDMDeviceState deviceState;
    private CDMError error;
    private DispenserStatus dispenserStatus;
    private Shutter shutter;

    @Override
    public int getStatus() {
        return status;
    }

    public void setStatus( int status ) {
        this.status = status;
    }
    public List < Cassette > getCassettes() {
        return cassettes;
    }

    public void setCassettes( List < Cassette > cassettes ) {
        this.cassettes = cassettes;
    }

    public String getDeviceState() {
        return deviceState.name();
    }

    public void setDeviceState( String deviceState ) {
        this.deviceState = CDMDeviceState.valueOfCompose( deviceState );
    }

    public String getError() {
        return error.name();
    }

    public void setError( String error ) {
        this.error = CDMError.valueOfCompose( error );
    }

    public String getDispenserStatus() {
        return dispenserStatus.name();
    }

    public void setDispenserStatus( String dispenserStatus ) {
        this.dispenserStatus = DispenserStatus.valueOfCompose( dispenserStatus );
    }

    public String getShutter() {
        return shutter.name();
    }

    public void setShutter( String shutter ) {
        this.shutter = Shutter.valueOfCompose( shutter );
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
