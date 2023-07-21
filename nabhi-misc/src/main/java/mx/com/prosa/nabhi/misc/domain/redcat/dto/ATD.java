package mx.com.prosa.nabhi.misc.domain.redcat.dto;

import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.misc.model.devices.DevicesWrapper;

public class ATD {

    private String terminalId;

    private IDF idf;

    private DevicesWrapper terminalDevices;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public IDF getIdf() {
        return idf;
    }

    public void setIdf(IDF idf) {
        this.idf = idf;
    }

    public DevicesWrapper getTerminalDevices() {
        return terminalDevices;
    }

    public void setTerminalDevices(DevicesWrapper terminalDevices) {
        this.terminalDevices = terminalDevices;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
