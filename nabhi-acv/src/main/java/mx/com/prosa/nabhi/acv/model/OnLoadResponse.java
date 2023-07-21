package mx.com.prosa.nabhi.acv.model;

import com.google.gson.GsonBuilder;

import java.util.Map;

public class OnLoadResponse {

    private String terminalId;
    private Map< String, Object > extra;

    public OnLoadResponse() { }

    public OnLoadResponse( String terminalId ) {
        this.terminalId = terminalId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public Map < String, Object > getExtra() {
        return extra;
    }

    public void setExtra( Map < String, Object > extra ) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
