package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de un nodo de conexión TCP" )
public class NodeTCP implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Nombre del nodo", example = "S1A^TEST^ISO" )
    private String nodeName;
    @ApiModelProperty( value = "Especifica que tipo de nodo es", example = "HKEY" )
    private String nodeType;
    @ApiModelProperty( value = "IP del nodo Base 24", example = "172.100.0.1" )
    private String ip;
    @ApiModelProperty( value = "Puerto del nodo Base24", example = "2770" )
    private int port;
    @ApiModelProperty( value = "Numero de traza P11", example = "9" )
    private int tracerNumber;
    @ApiModelProperty( value = "Tiempo de espera por mensaje en segundos", example = "30" )
    private int timeout;
    @ApiModelProperty( value = "Si el NODO TCP es de tipo ISO, este campo especifica el tiempo en el que se intercambiaran mensajes de red tipo 0800:301 en segundos", example = "15" )
    private int echoDelay;
    @ApiModelProperty( value = "Zone Pin Key", example = "0000000000000000" )
    private String zpk;
    @ApiModelProperty( value = "Espefica un controlador transaccional, se puede personalizar el comportamiento de la mensajeria", example = "ISOController" )
    private String controller;
    @ApiModelProperty( value = "Especifica que si el socket es SSL o TLS", example = "true" )
    private boolean sslEnable;

    public NodeTCP() {
    }

    public NodeTCP( String nodeName ) {
        this.nodeName = nodeName;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

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
