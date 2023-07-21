package mx.com.prosa.nabhi.misc.model.jdb.composite;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Date;

@ApiModel( description = "Json DTO representación de un ID un tiempo de conexión del cajero" )
public class UpTimeId implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Nemotecnico del cajero", example = "ABCCAP" )
    private String terminalId;
    @ApiModelProperty( value = "Fecha de conexión", example = "24/06/21" )
    private Date date;

    public UpTimeId() {
    }

    public UpTimeId( String terminalId, Date date ) {
        this.terminalId = terminalId;
        this.date = date;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate( Date date ) {
        this.date = date;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
