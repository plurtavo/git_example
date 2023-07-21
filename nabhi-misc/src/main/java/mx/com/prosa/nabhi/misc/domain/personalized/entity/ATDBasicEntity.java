package mx.com.prosa.nabhi.misc.domain.personalized.entity;

import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.misc.domain.node.entity.NodeTCPEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "TBL_ATD" )
public class ATDBasicEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @Column( length = 16, name = "PK_TERMINAL_ID" )
    private String terminalId;

    @Column( length = 12, name = "SEQUENCE_NUMBER" )
    private Integer sequenceNumber;

    @Column( name = "SEQUENCE", length = 9 )
    private String sequence;

    @Column( length = 2, name = "DEVICE_TYPE" )
    private String deviceType;

    @Column( length = 10, name = "POSTING_DAY" )
    private String postingDay;

    @Column( name = "ATM_ONLINE" )
    private boolean online;

    @Column( length = 15, name = "IP" )
    private String ip;

    @Column( name = "ACTIVE_TRANSACTION" )
    private boolean activeTrx;

    @Column( name = "RECEIPT" )
    private String receipt;

    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "PK_NODE_NAME_X", foreignKey = @ForeignKey( name = "PK_NODE_NAME_X" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private NodeTCPEntity nodeX;


    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber( Integer sequenceNumber ) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence( String sequence ) {
        this.sequence = sequence;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType( String deviceType ) {
        this.deviceType = deviceType;
    }

    public String getPostingDay() {
        return postingDay;
    }

    public void setPostingDay( String postingDay ) {
        this.postingDay = postingDay;
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

    public boolean isActiveTrx() {
        return activeTrx;
    }

    public void setActiveTrx( boolean activeTrx ) {
        this.activeTrx = activeTrx;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt( String receipt ) {
        this.receipt = receipt;
    }

    public NodeTCPEntity getNodeX() {
        return nodeX;
    }

    public void setNodeX( NodeTCPEntity nodeX ) {
        this.nodeX = nodeX;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
