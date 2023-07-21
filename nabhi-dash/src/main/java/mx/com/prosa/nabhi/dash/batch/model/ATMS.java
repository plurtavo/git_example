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
public class ATMS extends Schema {

    @JsonProperty( "metadata" )
    private List < ATM > atmList;

    public List < ATM > getAtmList() {
        return atmList;
    }

    public void setAtmList( List < ATM > atmList ) {
        this.atmList = atmList;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }

}
