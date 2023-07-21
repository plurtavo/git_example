package mx.com.prosa.nabhi.esq.data;

import java.io.Serializable;

public class XFSEntity implements Serializable {

    private int xfsCode;
    private String xfsDescription;
    private int ddcCode;
    private String ddcDescription;
    private int severity;
    private int module;

    XFSEntity( int xfsCode, String xfsDescription, int ddcCode, String ddcDescription, int severity, int module ) {
        this.xfsCode = xfsCode;
        this.xfsDescription = xfsDescription;
        this.ddcCode = ddcCode;
        this.ddcDescription = ddcDescription;
        this.severity = severity;
        this.module = module;
    }

    public int getXfsCode() {
        return xfsCode;
    }

    public void setXfsCode( int xfsCode ) {
        this.xfsCode = xfsCode;
    }

    public String getXfsDescription() {
        return xfsDescription;
    }

    public void setXfsDescription( String xfsDescription ) {
        this.xfsDescription = xfsDescription;
    }

    public int getDdcCode() {
        return ddcCode;
    }

    public void setDdcCode( int ddcCode ) {
        this.ddcCode = ddcCode;
    }

    public String getDdcDescription() {
        return ddcDescription;
    }

    public void setDdcDescription( String ddcDescription ) {
        this.ddcDescription = ddcDescription;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity( int severity ) {
        this.severity = severity;
    }

    public int getModule() {
        return module;
    }

    public void setModule( int module ) {
        this.module = module;
    }
}
