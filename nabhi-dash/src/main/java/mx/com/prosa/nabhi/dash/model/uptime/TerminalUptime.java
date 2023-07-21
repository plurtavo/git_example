package mx.com.prosa.nabhi.dash.model.uptime;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel( description = "Reporte JSON de un cajero de la institución y sus UPTIMES" )
public class TerminalUptime {

    @ApiModelProperty( value = "ID del cajero", example = "ABCCAP" )
    private String terminalId;
    @ApiModelProperty( value = "Ubicación del cajero", example = "Lab PROSA, CDMX" )
    private String location;
    @ApiModelProperty( value = "Uptimes del cajero" )
    private List < SingleUptime > uptimes;

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

    public List < SingleUptime > getUptimes() {
        return uptimes;
    }

    public void setUptimes( List < SingleUptime > uptimes ) {
        this.uptimes = uptimes;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
