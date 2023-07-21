package mx.com.prosa.nabhi.dash.model.monitoring;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel( description = "Json global con los datos de las instituciones y sus cajeros" )
public class MonitoringView {

    @ApiModelProperty( value = "Lista de instituciones" )
    private List < InstitutionView > institutionViews;

    public List < InstitutionView > getInstitutionViews() {
        return institutionViews;
    }

    public void setInstitutionViews( List < InstitutionView > institutionViews ) {
        this.institutionViews = institutionViews;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
