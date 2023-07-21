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
public class Prefixes extends Schema {

    @JsonProperty( "metadata" )
    private List < Prefix > bins;

    public List < Prefix > getBins() {
        return bins;
    }

    public void setBins( List < Prefix > bins ) {
        this.bins = bins;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }

}
