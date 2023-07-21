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
public class SurchargeFees extends Schema {

    @JsonProperty( "metadata" )
    private List < SurchargeFee > surcharges;

    public List < SurchargeFee > getSurcharges() {
        return surcharges;
    }

    public void setSurcharges( List < SurchargeFee > surcharges ) {
        this.surcharges = surcharges;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }

}
