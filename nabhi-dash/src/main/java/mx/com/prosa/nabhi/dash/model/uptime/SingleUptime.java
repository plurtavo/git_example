package mx.com.prosa.nabhi.dash.model.uptime;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Reporte JSON de los uptime del cajero" )
public class SingleUptime {

    @ApiModelProperty( value = "Fecha de la medición", example = "01/01/1990" )
    private String date;
    @ApiModelProperty( value = "Numero de segundo que estuvo conectado durante el dia", example = "86400" )
    private int uptime;

    public SingleUptime( String date, int uptime ) {
        this.date = date;
        this.uptime = uptime;
    }

    public SingleUptime() {
    }

    public String getDate() {
        return date;
    }

    public void setDate( String date ) {
        this.date = date;
    }

    public int getUptime() {
        return uptime;
    }

    public void setUptime( int uptime ) {
        this.uptime = uptime;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
