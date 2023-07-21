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
public class AcquirerProfilesMain extends Schema {

    @JsonProperty( "metadata" )
    private List < AcquirerProfile > acquirerProfiles;

    public List < AcquirerProfile > getAcquirerProfiles() {
        return acquirerProfiles;
    }

    public void setAcquirerProfiles( List < AcquirerProfile > acquirerProfiles ) {
        this.acquirerProfiles = acquirerProfiles;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }

}
