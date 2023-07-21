package us.gonet.nabhi.misc.model.devices.pin;

import com.google.gson.GsonBuilder;
import us.gonet.nabhi.misc.model.devices.IDevice;
import us.gonet.nabhi.misc.model.devices.constants.pin.*;

import java.io.Serializable;

public class EPP implements IDevice, Serializable {

    private static final long serialVersionUID = 23453246534L;

    private int status;
    private AntiFraudModule fraudModule;
    private DevicePosition devicePosition;
    private DeviceState deviceState;
    private EncStat encStat;
    private PINError error;

    @Override
    public int getStatus() {
        return status;
    }

    public void setStatus( int status ) {
        this.status = status;
    }

    public String getFraudModule() {
        return fraudModule.name();
    }

    public void setFraudModule( String fraudModule ) {
        this.fraudModule = AntiFraudModule.valueOfCompose( fraudModule );
    }

    public String getDevicePosition() {
        return devicePosition.name();
    }

    public void setDevicePosition( String devicePosition ) {
        this.devicePosition = DevicePosition.valueOfCompose( devicePosition );
    }

    public String getDeviceState() {
        return deviceState.name();
    }

    public void setDeviceState( String deviceState ) {
        this.deviceState = DeviceState.valueOf( deviceState );
    }

    public String getEncStat() {
        return encStat.name();
    }

    public void setEncStat( String encStat ) {
        this.encStat = EncStat.valueOfCompose( encStat );
    }

    public String getError() {
        return error.name();
    }

    public void setError( String error ) {
        this.error = PINError.valueOfCompose( error );
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
