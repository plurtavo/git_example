package mx.com.prosa.nabhi.dash.model.monitoring;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json con los datos basico del cajero" )
public class TerminalView {

    @ApiModelProperty( value = "ID del cajero", example = "ABCCAP" )
    private String terminalId;
    @ApiModelProperty( value = "Ubicación del cajero", example = "LAB Prosa CDMX" )
    private String location;
    @ApiModelProperty( value = "Indica si esta online o no", example = "true" )
    private boolean online;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation( String location ) {
        this.location = location;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline( boolean online ) {
        this.online = online;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
