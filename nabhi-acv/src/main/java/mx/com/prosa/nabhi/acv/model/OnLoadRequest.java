package mx.com.prosa.nabhi.acv.model;

import com.google.gson.GsonBuilder;

public class OnLoadRequest {

    private String terminalId;
    private String sessionId;
    private String timestamp;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId( String sessionId ) {
        this.sessionId = sessionId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp( String timestamp ) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
