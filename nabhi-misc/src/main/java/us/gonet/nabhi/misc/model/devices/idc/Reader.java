package us.gonet.nabhi.misc.model.devices.idc;

import com.google.gson.GsonBuilder;
import us.gonet.nabhi.misc.model.devices.IDevice;
import us.gonet.nabhi.misc.model.devices.constants.idc.*;

import java.io.Serializable;

public class Reader implements IDevice, Serializable {

    private static final long serialVersionUID = 23453246534L;

    private int status;
    private AntiFraudModule fraudModule;
    private ChipModule chipModule;
    private IDCError error;
    private RetainBin retainBin;
    private IDCDeviceState deviceState;

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

    public String getChipModule() {
        return chipModule.name();
    }

    public void setChipModule( String chipModule ) {
        this.chipModule = ChipModule.valueOfCompose( chipModule );
    }

    public String getError() {
        return error.name();
    }

    public void setError( String error ) {
        this.error = IDCError.valueOfCompose( error );
    }

    public String getRetainBin() {
        return retainBin.name();
    }

    public void setRetainBin( String retainBin ) {
        this.retainBin = RetainBin.valueOfCompose( retainBin );
    }


    public String getDeviceState() {
        return deviceState.name();
    }

    public void setDeviceState( String deviceState ) {
        this.deviceState = IDCDeviceState.valueOfCompose( deviceState );
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
