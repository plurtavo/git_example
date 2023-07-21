package mx.com.prosa.nabhi.dash.report;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "JSON Reporte de uptime por cajero" )
public class RowJsonUptime {

    @ApiModelProperty( value = "ID de la institución", example = "ABCCAP")
    private String terminalId;
    @ApiModelProperty( value = "Ubicación del cajero", example = "LAB Prosa CDMX")
    private String location;
    @ApiModelProperty( value = "Fecha de inicio", example = "01/01/1990")
    private String from;
    @ApiModelProperty( value = "Fecha de fin", example = "20/01/1990")
    private String to;
    @ApiModelProperty( value = "Numero de dias", example = "20")
    private int days;
    @ApiModelProperty( value = "Promedio de uptime por el rango de fechas", example = "89.01")
    private double uptime;

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

    public String getFrom() {
        return from;
    }

    public void setFrom( String from ) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo( String to ) {
        this.to = to;
    }

    public int getDays() {
        return days;
    }

    public void setDays( int days ) {
        this.days = days;
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
