package mx.com.prosa.nabhi.dash.report;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel( description = "Json Reporte resumen de uptime por la RED NABHI" )
public class Summary {

    @ApiModelProperty( value = "Listado de resumen por institucion" )
    private List < InstitutionSummary > institutionSummaries;
    @ApiModelProperty( value = "Cabeceras" )
    private static final String [] COLUMN_NAMES = { "Institución", "Uptime" };
    @ApiModelProperty( value = "Cabeceras" )
    private static final String [] SUMMARY_NAMES = { "Total", "" };
    @ApiModelProperty( value = "Total de uptime por toda la red NABHI" )
    private double uptime;

    public List < InstitutionSummary > getInstitutionSummaries() {
        return institutionSummaries;
    }

    public void setInstitutionSummaries( List < InstitutionSummary > institutionSummaries ) {
        this.institutionSummaries = institutionSummaries;
    }

    public double getUptime() {
        return uptime;
    }

    public void setUptime( double uptime ) {
        this.uptime = uptime;
    }

    public String[] getColumnNames() {
        return COLUMN_NAMES;
    }

    public String[] getSummaryNames() {
        return SUMMARY_NAMES;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
