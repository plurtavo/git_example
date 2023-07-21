package us.gonet.nabhi.misc.model.devices.ptr;

import com.google.gson.GsonBuilder;
import us.gonet.nabhi.misc.model.devices.IDevice;
import us.gonet.nabhi.misc.model.devices.constants.ptr.*;

import java.io.Serializable;

public class Printer implements IDevice, Serializable {

    private static final long serialVersionUID = 23453246534L;

    private int status;
    private PaperStatus paper;
    private TonerStatus toner;
    private PTRDeviceState deviceState;
    private PTRError error;
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
