package mx.com.prosa.nabhi.dash.model.uptime;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.dash.model.SearchConditions;

public class ReportSearchConditions extends SearchConditions {

    @ApiModelProperty( value = "Fecha desde formato dd/MM/yyyy", example = "01/01/1990" )
    private String form;
    @ApiModelProperty( value = "Fecha hasta formato dd/MM/yyyy", example = "01/01/1990" )
    private String to;
    @ApiModelProperty( value = "Criterio para buscar cajeros con disponibilidad mayores al valor especificado", example = "90" )
    private int greaterThan;
    @ApiModelProperty( value = "Criterio para buscar cajeros con disponibilidad menores al valor especificado", example = "90" )
    private int lessThan;

    public String getForm() {
        return form;
    }

    public void setForm( String form ) {
        this.form = form;
    }

    public String getTo() {
        return to;
    }

    public void setTo( String to ) {
        this.to = to;
    }

    public int getGreaterThan() {
        return greaterThan;
    }

    public void setGreaterThan( int greaterThan ) {
        this.greaterThan = greaterThan;
    }

    public int getLessThan() {
        return lessThan;
    }

    public void setLessThan( int lessThan ) {
        this.lessThan = lessThan;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
