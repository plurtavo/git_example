package mx.com.prosa.nabhi.dash.model.monitoring;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel( description = "Json con los datos de la institución y sus cajeros" )
public class InstitutionView {

    @ApiModelProperty( value = "ID de la institución", example = "B010" )
    private String fiid;
    @ApiModelProperty( value = "RED de cajeros" )
    private List< TerminalView > terminalViews;

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    public List < TerminalView > getTerminalViews() {
        return terminalViews;
    }

    public void setTerminalViews( List < TerminalView > terminalViews ) {
        this.terminalViews = terminalViews;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
