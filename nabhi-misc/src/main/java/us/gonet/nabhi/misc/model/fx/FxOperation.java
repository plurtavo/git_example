package us.gonet.nabhi.misc.model.fx;

import com.google.gson.GsonBuilder;

import java.io.Serializable;

public class FxOperation implements Serializable, IFx {

    private static final long serialVersionUID = 23453246534L;

    private String hardwareOperation;
    private String bufferHardware;
    private String restOperation;
    private String bufferRest;
    private boolean activeFdk;
    private int nextState;
    private int timeout;
    private int hostTimeout;
    private int error;
    private int emv;
    private int misc1;
    private int misc2;
    private int misc3;
    private String misc4;
    private String misc5;
    private String misc6;

    public FxOperation() {
    }

    public FxOperation( String hardwareOperation, String bufferHardware, String restOperation, String bufferRest, boolean activeFdk, int nextState ) {
        this.hardwareOperation = hardwareOperation;
        this.bufferHardware = bufferHardware;
        this.restOperation = restOperation;
        this.bufferRest = bufferRest;
        this.activeFdk = activeFdk;
        this.nextState = nextState;
    }

    public String getHardwareOperation() {
        return hardwareOperation;
    }

    public void setHardwareOperation( String hardwareOperation ) {
        this.hardwareOperation = hardwareOperation;
    }

    public String getBufferHardware() {
        return bufferHardware;
    }

    public void setBufferHardware( String bufferHardware ) {
        this.bufferHardware = bufferHardware;
    }

    public String getRestOperation() {
        return restOperation;
    }

    public void setRestOperation( String restOperation ) {
        this.restOperation = restOperation;
    }

    public String getBufferRest() {
        return bufferRest;
    }

    public void setBufferRest( String bufferRest ) {
        this.bufferRest = bufferRest;
    }

    public boolean isActiveFdk() {
        return activeFdk;
    }

    public void setActiveFdk( boolean activeFdk ) {
        this.activeFdk = activeFdk;
    }

    public int getNextState() {
        return nextState;
    }

    public void setNextState( int nextState ) {
        this.nextState = nextState;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout( int timeout ) {
        this.timeout = timeout;
    }

    public int getHostTimeout() {
        return hostTimeout;
    }

    public void setHostTimeout( int hostTimeout ) {
        this.hostTimeout = hostTimeout;
    }

    public int getError() {
        return error;
    }

    public int getEmv() {
        return emv;
    }

    public void setEmv( int emv ) {
        this.emv = emv;
    }

    public void setError( int error ) {
        this.error = error;
    }

    public int getMisc1() {
        return misc1;
    }

    public void setMisc1( int misc1 ) {
        this.misc1 = misc1;
    }

    public int getMisc2() {
        return misc2;
    }

    public void setMisc2( int misc2 ) {
        this.misc2 = misc2;
    }

    public int getMisc3() {
        return misc3;
    }

    public void setMisc3( int misc3 ) {
        this.misc3 = misc3;
    }

    public String getMisc4() {
        return misc4;
    }

    public void setMisc4( String misc4 ) {
        this.misc4 = misc4;
    }

    public String getMisc5() {
        return misc5;
    }

    public void setMisc5( String misc5 ) {
        this.misc5 = misc5;
    }

    public String getMisc6() {
        return misc6;
    }

    public void setMisc6( String misc6 ) {
        this.misc6 = misc6;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
