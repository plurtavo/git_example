package mx.com.prosa.nabhi.dash.model.uptime;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel( description = "Reporte JSON con datos sin procesar" )
public class UptimeReport {

    @ApiModelProperty( value = "Listado de Instituciones" )
    private List < InstitutionUptime > institutionUptimes;

    public List < InstitutionUptime > getInstitutionUptimes() {
        return institutionUptimes;
    }

    public void setInstitutionUptimes( List < InstitutionUptime > institutionUptimes ) {
        this.institutionUptimes = institutionUptimes;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
