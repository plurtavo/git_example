package mx.com.prosa.nabhi.dash.report;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel( description = "JSON Reporte de uptime por institución" )
public class SheetJsonUptime {

    @ApiModelProperty( value = "ID de la institución", example = "B010" )
    private String sheetName;
    @ApiModelProperty( value = "Cabeceras" )
    private static final String[] COLUMN_NAMES = { "ID Cajero", "Ubicación", "Fecha Desde", "Fecha Hasta", "Dias Calculados", "Uptime" };
    @ApiModelProperty( value = "Cabeceras" )
    private static final String[] SUMMARY_NAMES = { "", "", "", "", "Total", "" };
    @ApiModelProperty( value = "Uptime de la institución", example = "89.7" )
    private double uptime;
    @ApiModelProperty( value = "Listado de cajeros" )
    private List < RowJsonUptime > rowJsonUptimes;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName( String sheetName ) {
        this.sheetName = sheetName;
    }

    public String[] getColumnNames() {
        return COLUMN_NAMES;
    }

    public String[] getSummaryNames() {
        return SUMMARY_NAMES;
    }

    public double getUptime() {
        return uptime;
    }

    public void setUptime( double uptime ) {
        this.uptime = uptime;
    }

    public List < RowJsonUptime > getRowJsonUptimes() {
        return rowJsonUptimes;
    }

    public void setRowJsonUptimes( List < RowJsonUptime > rowJsonUptimes ) {
        this.rowJsonUptimes = rowJsonUptimes;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
