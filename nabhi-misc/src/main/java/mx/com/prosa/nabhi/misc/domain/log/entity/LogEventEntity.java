package mx.com.prosa.nabhi.misc.domain.log.entity;

import com.google.gson.GsonBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table( name = "TBL_LOG_EVENT" )
public class LogEventEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @GeneratedValue( strategy = GenerationType.TABLE )
    @Column( name = "PK_ID" )
    private int id;

    @Column( name = "TIME" )
    @Temporal( value = TemporalType.TIMESTAMP )
    private Date time;

    @Column( name = "HOST_NAME", length = 100 )
    private String hostName;

    @Column( name = "APP_NAME", length = 100 )
    private String appName;

    @Column( name = "IP_HOST", length = 100 )
    private String ipHost;

    @Column( name = "REMOTE_HOST_NAME", length = 100 )
    private String remoteHostName;

    @Column( name = "REMOTE_HOST_IP", length = 100 )
    private String remoteHostIp;

    @Column( name = "REMOTE_HOST_PORT", length = 100 )
    private String remoteHostPort;

    @Column( name = "END_POINT", length = 100 )
    private String endPoint;

    @Column( name = "USER_NAME", length = 100 )
    private String userName;

    @Column ( name = "EVENT", length = 300 )
    private String event;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime( Date time ) {
        this.time = time;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName( String hostName ) {
        this.hostName = hostName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName( String appName ) {
        this.appName = appName;
    }

    public String getIpHost() {
        return ipHost;
    }

    public void setIpHost( String ipHost ) {
        this.ipHost = ipHost;
    }

    public String getRemoteHostName() {
        return remoteHostName;
    }

    public void setRemoteHostName( String remoteHostName ) {
        this.remoteHostName = remoteHostName;
    }

    public String getRemoteHostIp() {
        return remoteHostIp;
    }

    public void setRemoteHostIp( String remoteHostIp ) {
        this.remoteHostIp = remoteHostIp;
    }

    public String getRemoteHostPort() {
        return remoteHostPort;
    }

    public void setRemoteHostPort( String remoteHostPort ) {
        this.remoteHostPort = remoteHostPort;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint( String endPoint ) {
        this.endPoint = endPoint;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName( String userName ) {
        this.userName = userName;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent( String event ) {
        this.event = event;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
