package mx.com.prosa.nabhi.misc.domain.algorithm.entity;

import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.misc.model.devices.DevicesWrapper;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "TBL_ATD" )
public class ATDForDevicesEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @Column( length = 16, name = "PK_TERMINAL_ID" )
    private String terminalId;

    @Lob
    @Column( name = "DEVICES" )
    private DevicesWrapper terminalDevices;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public DevicesWrapper getTerminalDevices() {
        return terminalDevices;
    }

    public void setTerminalDevices(DevicesWrapper terminalDevices) {
        this.terminalDevices = terminalDevices;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
