package mx.com.prosa.nabhi.misc.domain.node.entity;

import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.misc.domain.complete.entity.AuditTable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@EntityListeners( AuditTable.class )
@Table( name = "TBL_NODE_TCP" )
public class NodeTCPEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @Column( length = 16, name = "PK_NODE_NAME" )
    private String nodeName;

    @Column( length = 10, name = "NODE_TYPE" )
    private String nodeType;

    @Column( length = 15, name = "IP" )
    private String ip;

    @Column( length = 6, name = "PORT" )
    private int port;

    @Column( name = "TRACER_NUMBER" )
    private int tracerNumber;

    @Column( name = "TIMEOUT" )
    private int timeout;

    @Column( name = "ECHO_DELAY" )
    private int echoDelay;

    @Column( length = 32, name = "ZPK" )
    private String zpk;

    @Column( length = 254, name = "CONTROLLER" )
    private String controller;

    @Column( name = "SSL_ENABLE" )
    private boolean sslEnable;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName( String nodeName ) {
        this.nodeName = nodeName;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType( String nodeType ) {
        this.nodeType = nodeType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp( String ip ) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort( int port ) {
        this.port = port;
    }

    public int getTracerNumber() {
        return tracerNumber;
    }

    public void setTracerNumber( int tracerNumber ) {
        this.tracerNumber = tracerNumber;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout( int timeout ) {
        this.timeout = timeout;
    }

    public int getEchoDelay() {
        return echoDelay;
    }

    public void setEchoDelay( int echoDelay ) {
        this.echoDelay = echoDelay;
    }

    public String getZpk() {
        return zpk;
    }

    public void setZpk( String zpk ) {
        this.zpk = zpk;
    }

    public String getController() {
        return controller;
    }

    public void setController( String controller ) {
        this.controller = controller;
    }

    public boolean isSslEnable() {
        return sslEnable;
    }

    public void setSslEnable( boolean sslEnable ) {
        this.sslEnable = sslEnable;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
