package mx.com.prosa.nabhi.dash.report;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json Reporte resumen de uptime por la institución" )
public class InstitutionSummary {

    @ApiModelProperty( value = "ID de la institución" )
    private String fiid;
    @ApiModelProperty( value = "Uptime de la institución", example = "31.2")
    private double uptime;

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    public double getUptime() {
        return uptime;
    }

    public void setUptime( double uptime ) {
        this.uptime = uptime;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
