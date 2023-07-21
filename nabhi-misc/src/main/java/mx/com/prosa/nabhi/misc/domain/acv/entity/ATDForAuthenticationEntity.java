package mx.com.prosa.nabhi.misc.domain.acv.entity;

import com.google.gson.GsonBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "TBL_ATD" )
public class ATDForAuthenticationEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @Column( length = 16, name = "PK_TERMINAL_ID" )
    private String terminalId;

    @Column( length = 15, name = "IP" )
    private String ip;

    @Column( name = "ATM_ONLINE" )
    private boolean online;

    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "FK_GROUP_SCREEN", foreignKey = @ForeignKey( name = "FK_GROUP_SCREEN" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private PayloadEntity screenGroup;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp( String ip ) {
        this.ip = ip;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline( boolean online ) {
        this.online = online;
    }

    public PayloadEntity getScreenGroup() {
        return screenGroup;
    }

    public void setScreenGroup( PayloadEntity screenGroup ) {
        this.screenGroup = screenGroup;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
