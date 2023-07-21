package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel( description = "Json DTO representación de un mensaje de LOG de usuario dentro de la consola Nabhi" )
public class LogEvent implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "ID del algoritmo", example = "1" )
    private int id;
    @ApiModelProperty( value = "Fecha en la cual se almaceno el LOG", example = "19/05/21 18:24:12" )
    private Date time;
    @ApiModelProperty( value = "Nombre del host desde el cual se utilizo la consola", example = "d5af434cd0df" )
    private String hostName;
    @ApiModelProperty( value = "Nombre de la aplicación que almaceno el LOG", example = "NABHI" )
    private String appName;
    @ApiModelProperty( value = "IP desde la cual se utilizo la consola", example = "172.22.0.2" )
    private String ipHost;
    @ApiModelProperty( value = "Host name de donde se dispara la solicitud", example = "172.22.0.5" )
    private String remoteHostName;
    @ApiModelProperty( value = "IP de donde se dispara la solicitud", example = "5e21b8c7ccf7" )
    private String remoteHostIp;
    @ApiModelProperty( value = "Puerto de donde se dispara la solicitud", example = "8080" )
    private String remoteHostPort;
    @ApiModelProperty( value = "Punto de entrada del servicio", example = "/dash/jdb/create/atd" )
    private String endPoint;
    @ApiModelProperty( value = "Usuario firmado en la aplicación", example = "testQA" )
    private String userName;
    @ApiModelProperty( value = "Detalle del evento", example = "El usuario testQA actualizo el registro con id MTVK de la tabla nodeTcp desde la IP 172.22.0.8" )
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
