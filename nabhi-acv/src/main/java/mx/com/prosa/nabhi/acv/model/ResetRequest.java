package mx.com.prosa.nabhi.acv.model;

import com.google.gson.GsonBuilder;

public class ResetRequest {

    private String device;

    public String getDevice() {
        return device;
    }

    public void setDevice( String device ) {
        this.device = device;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
