package mx.com.prosa.nabhi.acv.model;

import com.google.gson.GsonBuilder;

public class PrintScreenRequest {

    private boolean create;
    private String fileName;
    private String terminalId;

    public boolean isCreate() {
        return create;
    }

    public void setCreate( boolean create ) {
        this.create = create;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName( String fileName ) {
        this.fileName = fileName;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
