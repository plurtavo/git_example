package mx.com.prosa.nabhi.misc.model.journal;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

@ApiModel( description = "Json DTO representación de una consulta para journal" )
public class JournalQuery {

    @ApiModelProperty( value = "Nemotecnico del cajero", example = "ABCCAP" )
    private String terminalId;
    @ApiModelProperty( value = "Timestamp de la fecha de inicio de consulta", example = "23/06/21 20:09:53.578000000" )
    private Timestamp from;
    @ApiModelProperty( value = "Timestamp de la fecha final de consulta", example = "25/06/21 00:47:32.153000000" )
    private Timestamp to;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public Timestamp getFrom() {
        return from;
    }

    public void setFrom( Timestamp from ) {
        this.from = from;
    }

    public Timestamp getTo() {
        return to;
    }

    public void setTo( Timestamp to ) {
        this.to = to;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
