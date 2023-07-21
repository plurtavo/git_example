package mx.com.prosa.nabhi.misc.domain.complete.entity;
//Cambio release/redcat

import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.misc.domain.algorithm.entity.DispensedAlgorithmEntity;
import mx.com.prosa.nabhi.misc.domain.node.entity.NodeTCPEntity;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import mx.com.prosa.nabhi.misc.model.devices.DevicesWrapper;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@EntityListeners( AuditTable.class )
@Table( name = "TBL_ATD" )
public class ATDEntity implements Serializable {

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

    @Column( length = 25, name = "MODEL" )
    private String model;

    @Column( length = 10, name = "POSTING_DAY" )
    private String postingDay;

    @Column( name = "ATM_ONLINE" )
    private boolean online;

    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "FK_FIID", foreignKey = @ForeignKey( name = "FK_FIID" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private IDFEntityKey idf;

    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "FK_COUNTY_CODE_ID", foreignKey = @ForeignKey( name = "FK_COUNTY_CODE_ID" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private CountyEntity county;

    @Column( length = 15, name = "IP" )
    private String ip;

    @Lob
    @Column( name = "LAST_TRANSACTION" )
    private String lastTrx;

    @Column( name = "ACTIVE_TRANSACTION" )
    private boolean activeTrx;

    @Column( length = 25, name = "LOCATION" )
    private String location;

    @Lob
    @Column( name = "RECEIPT" )
    private String receipt;

    @Column( length = 10, name = "SCRENN_TYPE" )
    private String screenType;

    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "FK_GROUP_SCREEN", foreignKey = @ForeignKey( name = "FK_GROUP_SCREEN" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private ScreenGroupEntity screenGroup;

    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "PK_NODE_NAME", foreignKey = @ForeignKey( name = "FK_NODE_NAME" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private NodeTCPEntity nodeHiso;

    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "PK_NODE_NAME_X", foreignKey = @ForeignKey( name = "PK_NODE_NAME_X" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private NodeTCPEntity nodeMtvk;

    @Lob
    @Column( name = "DEVICES" )
    private DevicesWrapper terminalDevices;

    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "FK_PACKAGE", foreignKey = @ForeignKey( name = "FK_PACKAGE" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private PackageEntity packageName;

    //Cambio release/algoritmos
    @ManyToOne( cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "DISPENSED_ALGORITHM", foreignKey = @ForeignKey( name = "FK_DISPENSED_ALGORITHM" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private DispensedAlgorithmEntity dispensedAlgorithm;
    //Cambio release/algoritmos

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

    public IDFEntityKey getIdf() {
        return idf;
    }

    public void setIdf( IDFEntityKey idf ) {
        this.idf = idf;
    }

    public CountyEntity getCounty() {
        return county;
    }

    public void setCounty( CountyEntity county ) {
        this.county = county;
    }

    public String getIp() {
        return ip;
    }

    public void setIp( String ip ) {
        this.ip = ip;
    }

    public String getLastTrx() {
        return lastTrx;
    }

    public void setLastTrx( String lastTrx ) {
        this.lastTrx = lastTrx;
    }

    public boolean isActiveTrx() {
        return activeTrx;
    }

    public void setActiveTrx( boolean activeTrx ) {
        this.activeTrx = activeTrx;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation( String location ) {
        this.location = location;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt( String receipt ) {
        this.receipt = receipt;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType( String screenType ) {
        this.screenType = screenType;
    }

    public ScreenGroupEntity getScreenGroup() {
        return screenGroup;
    }

    public void setScreenGroup( ScreenGroupEntity screenGroup ) {
        this.screenGroup = screenGroup;
    }

    public NodeTCPEntity getNodeHiso() {
        return nodeHiso;
    }

    public void setNodeHiso( NodeTCPEntity nodeHiso ) {
        this.nodeHiso = nodeHiso;
    }

    public NodeTCPEntity getNodeMtvk() {
        return nodeMtvk;
    }

    public void setNodeMtvk( NodeTCPEntity nodeMtvk ) {
        this.nodeMtvk = nodeMtvk;
    }

    public DevicesWrapper getTerminalDevices() {
        return terminalDevices;
    }

    public void setTerminalDevices( DevicesWrapper terminalDevices ) {
        this.terminalDevices = terminalDevices;
    }

    public String getModel() {
        return model;
    }

    public void setModel( String model ) {
        this.model = model;
    }

    public PackageEntity getPackageName() {
        return packageName;
    }

    public void setPackageName( PackageEntity packageName ) {
        this.packageName = packageName;
    }

    //Cambio release/algoritmos
    public DispensedAlgorithmEntity getDispensedAlgorithm() {
        return dispensedAlgorithm;
    }

    public void setDispensedAlgorithm( DispensedAlgorithmEntity dispensedAlgorithm ) {
        this.dispensedAlgorithm = dispensedAlgorithm;
    }
    //Cambio release/algoritmos

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
