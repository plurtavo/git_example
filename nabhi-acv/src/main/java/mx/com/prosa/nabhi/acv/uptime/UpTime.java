package mx.com.prosa.nabhi.acv.uptime;

import com.google.gson.GsonBuilder;

public class UpTime {

    private String sessionId;
    private String up;
    private String down;
    private String id;

    public UpTime( String sessionId, String up, String down, String id ) {
        this.sessionId = sessionId;
        this.up = up;
        this.down = down;
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId( String sessionId ) {
        this.sessionId = sessionId;
    }

    public String getUp() {
        return up;
    }

    public void setUp( String up ) {
        this.up = up;
    }

    public String getDown() {
        return down;
    }

    public void setDown( String down ) {
        this.down = down;
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}

