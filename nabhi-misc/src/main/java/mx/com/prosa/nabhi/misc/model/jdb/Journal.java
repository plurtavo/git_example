package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de un mensaje de Journal" )
public class Journal implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "ID del algoritmo", example = "1" )
    private int id;
    @ApiModelProperty( value = "Mensaje que indica el evento almacenado", example = "El cliente acepta la comisión de 20.00" )
    private String message;
    @ApiModelProperty( value = "Hora y fecha en la cual se guardo el mensaje", example = "06/21 14:47:32" )
    private String writeDate;
    @ApiModelProperty( value = "Nemotecnico del cajero que almaceno el mensaje", example = "ABCCAP" )
    private String terminalId;

    public Journal() {
    }

    public Journal( String message, String writeDate, String terminalId ) {
        this.message = message;
        this.writeDate = writeDate;
        this.terminalId = terminalId;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate( String writeDate ) {
        this.writeDate = writeDate;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
