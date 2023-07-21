package mx.com.prosa.nabhi.dash.batch.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

@JsonInclude( JsonInclude.Include.NON_NULL )
@JsonPropertyOrder( {
        "nemotécnico",
        "ip",
        "id institución",
        "tipo de cajero",
        "ubicación",
        "código de municipio",
        "tipo de pantalla",
        "nodo iso",
        "nodo jke",
        "paquete transaccional",
        "id de pantallas",
        "algoritmo dispensado",
        "licencia"

} )
public class ATM implements Serializable {


    @JsonProperty( required = true, value = "nemotécnico" )
    private String terminalId;
    @JsonProperty( required = true, value = "ip" )
    private String ip;
    @JsonProperty( required = true, value = "id institución" )
    private String fiid;
    @JsonProperty( required = true, value = "tipo de cajero" )
    private String deviceType;
    @JsonProperty( required = true, value = "ubicación" )
    private String location;
    @JsonProperty( required = true, value = "código de municipio" )
    private int countyCodeId;
    @JsonProperty( required = true, value = "tipo de pantalla" )
    private String screenType;
    @JsonProperty( required = true, value = "nodo iso" )
    private String nodeHiso;
    @JsonProperty( required = true, value = "nodo jke" )
    private String nodeMtvk;
    @JsonProperty( required = true, value = "paquete transaccional" )
    private String packageName;
    @JsonProperty( required = true, value = "id de pantallas" )
    private String groupId;
    @JsonProperty( required = true, value = "algoritmo dispensado" )
    private int algorithmId;
    @JsonProperty( required = true, value = "licencia" )
    private String sequence;

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

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType( String deviceType ) {
        this.deviceType = deviceType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation( String location ) {
        this.location = location;
    }

    public int getCountyCodeId() {
        return countyCodeId;
    }

    public void setCountyCodeId( int countyCodeId ) {
        this.countyCodeId = countyCodeId;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType( String screenType ) {
        this.screenType = screenType;
    }

    public String getNodeHiso() {
        return nodeHiso;
    }

    public void setNodeHiso( String nodeHiso ) {
        this.nodeHiso = nodeHiso;
    }

    public String getNodeMtvk() {
        return nodeMtvk;
    }

    public void setNodeMtvk( String nodeMtvk ) {
        this.nodeMtvk = nodeMtvk;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName( String packageName ) {
        this.packageName = packageName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId( String groupId ) {
        this.groupId = groupId;
    }

    public int getAlgorithmId() {
        return algorithmId;
    }

    public void setAlgorithmId( int algorithmId ) {
        this.algorithmId = algorithmId;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence( String sequence ) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }

}
