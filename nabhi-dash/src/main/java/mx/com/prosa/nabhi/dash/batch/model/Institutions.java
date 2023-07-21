package mx.com.prosa.nabhi.dash.batch.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.GsonBuilder;

import java.util.List;

@JsonInclude( JsonInclude.Include.NON_NULL )
@JsonPropertyOrder( {
        "metadata"

} )
public class Institutions extends Schema {

    @JsonProperty( "metadata" )
    private List < Institution > institutionsList;


    public List < Institution > getInstitutionsList() {
        return institutionsList;
    }

    public void setInstitutionsList( List < Institution > institutionsList ) {
        this.institutionsList = institutionsList;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }

}
