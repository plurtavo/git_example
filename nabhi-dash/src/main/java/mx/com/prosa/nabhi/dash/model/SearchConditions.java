package mx.com.prosa.nabhi.dash.model;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json con los criterios de búsqueda" )
public class SearchConditions extends PageModel {

    @ApiModelProperty( value = "ID del cajero", example = "ABCCAP" )
    private String terminalId;
    @ApiModelProperty( value = "ID de la Institución", example = "B010" )
    private String fiid;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
