package mx.com.prosa.nabhi.dash.model.redcat;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.dash.model.SearchConditions;

public class RedcatSearchConditions extends SearchConditions {

    @ApiModelProperty( value = "Fecha desde formato dd/MM/yyyy", example = "01/01/1990" )
    private String form;
    @ApiModelProperty( value = "Fecha hasta formato dd/MM/yyyy", example = "01/01/1990" )
    private String to;

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
