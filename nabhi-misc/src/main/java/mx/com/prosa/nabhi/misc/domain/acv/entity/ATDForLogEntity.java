package mx.com.prosa.nabhi.misc.domain.acv.entity;

import com.google.gson.GsonBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table( name = "TBL_ATD" )
public class ATDForLogEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @Column( length = 16, name = "PK_TERMINAL_ID" )
    private String terminalId;

    @Column( name = "ATM_ONLINE" )
    private boolean online;

    @Column( length = 15, name = "IP" )
    private String ip;

    @OneToMany( cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    private Set < UpTimeEntity > upTimes;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline( boolean online ) {
        this.online = online;
    }

    public String getIp() {
        return ip;
    }

    public void setIp( String ip ) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
