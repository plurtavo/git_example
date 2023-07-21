package mx.com.prosa.nabhi.dash.model.uptime;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel( description = "Reporte JSON de la institución y sus cajeros" )
public class InstitutionUptime {

    @ApiModelProperty( value = "ID de la institución", example = "B010" )
    private String fiid;
    @ApiModelProperty( value = "Lista de cajeros de la institución" )
    private List< TerminalUptime > terminalUptimes;

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    public List < TerminalUptime > getTerminalUptimes() {
        return terminalUptimes;
    }

    public void setTerminalUptimes( List < TerminalUptime > terminalUptimes ) {
        this.terminalUptimes = terminalUptimes;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
