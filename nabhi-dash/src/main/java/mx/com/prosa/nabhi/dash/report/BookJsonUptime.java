package mx.com.prosa.nabhi.dash.report;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel( description = "JSON Reporte de UPTIME" )
public class BookJsonUptime {

    @ApiModelProperty( value = "Instituciones reportadas" )
    private List< SheetJsonUptime > sheetJsonUptimes;
    @ApiModelProperty( value = "Resumen" )
    private Summary summary;

    public List < SheetJsonUptime > getSheetJsonUptimes() {
        return sheetJsonUptimes;
    }

    public void setSheetJsonUptimes( List < SheetJsonUptime > sheetJsonUptimes ) {
        this.sheetJsonUptimes = sheetJsonUptimes;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary( Summary summary ) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
