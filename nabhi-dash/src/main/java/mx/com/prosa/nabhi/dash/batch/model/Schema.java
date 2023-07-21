package mx.com.prosa.nabhi.dash.batch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.GsonBuilder;

@JsonIgnoreProperties( ignoreUnknown = true )
@JsonInclude( JsonInclude.Include.NON_NULL )
@JsonPropertyOrder( {
        "operación",
        "módulo"

} )
public class Schema {

    @JsonProperty( "operación" )
    private Operation operation;
    @JsonProperty( "módulo" )
    private Module module;

    public Operation getOperation() {
        return operation;
    }

    public void setOperation( Operation operation ) {
        this.operation = operation;
    }

    public Module getModule() {
        return module;
    }

    public void setModule( Module module ) {
        this.module = module;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }

    public enum Operation {
        ALTA,
        BAJA,
        CAMBIO,
    }

    public enum Module {
        INSTITUCIONES,
        BINES,
        CAJEROS,
        APC,
        COMISIONES
    }
}
