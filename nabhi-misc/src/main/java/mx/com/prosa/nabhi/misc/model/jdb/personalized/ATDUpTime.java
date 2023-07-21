package mx.com.prosa.nabhi.misc.model.jdb.personalized;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.jdb.UpTimes;

import java.io.Serializable;
import java.util.List;

@ApiModel( description = "Json DTO representación de un ATD (para consulta de conexiones)" )
public class ATDUpTime implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Nemotécnico del cajero", example = "ABCCAP" )
    private String terminalId;
    @ApiModelProperty( value = "Lista de registros de conexión del cajero", example = "14/06/21" )
    private List< UpTimes > upTimes;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public List < UpTimes > getUpTimes() {
        return upTimes;
    }

    public void setUpTimes( List < UpTimes > upTimes ) {
        this.upTimes = upTimes;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
