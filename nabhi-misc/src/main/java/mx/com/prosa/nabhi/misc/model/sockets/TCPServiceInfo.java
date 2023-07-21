package mx.com.prosa.nabhi.misc.model.sockets;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación un Nodo TCP" )
public class TCPServiceInfo implements Serializable {

    private static final long serialVersionUID = 8799656478674716638L;
    @ApiModelProperty( value = "Nombre del nodo TCP", example = "S1A^TEST^ISO" )
    private String nodeName;
    @ApiModelProperty( value = "IP del nodo", example = "172.100.0.2" )
    private String ip;
    @ApiModelProperty( value = "Puerto", example = "443" )
    private int port;
    @ApiModelProperty( value = "Número de secuencia", example = "1" )
    private int tracerNumber;
    @ApiModelProperty( value = "Llave de zona", example = "000000000000000000" )
    private String zpk;
    @ApiModelProperty( value = "Tiempo de espera por mensaje", example = "" )
    private int echoDelay;
    @ApiModelProperty( value = "Modo del socket", example = "CLIENT" )
    private String mode;
    @ApiModelProperty( value = "Mensaje enviado por la conexión", example = "" )
    private String message;
    @ApiModelProperty( value = "Estado del nodo", example = "Disconnected" )
    private String status;
    @ApiModelProperty( value = "Tipo de nodo", example = "ISOController" )
    private String controller;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName( String nodeName ) {
        this.nodeName = nodeName;
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

    public String getZpk() {
        return zpk;
    }

    public void setZpk( String zpk ) {
        this.zpk = zpk;
    }

    public int getEchoDelay() {
        return echoDelay;
    }

    public void setEchoDelay( int echoDelay ) {
        this.echoDelay = echoDelay;
    }

    public String getMode() {
        return mode;
    }

    public void setMode( String mode ) {
        this.mode = mode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

    public String getController() {
        return controller;
    }

    public void setController( String controller ) {
        this.controller = controller;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
