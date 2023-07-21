package mx.com.prosa.nabhi.misc.model.jdb.personalized;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.jdb.Journal;

import java.io.Serializable;
import java.util.List;

@ApiModel( description = "Json DTO representación de un ATD (Para consultar journal)" )
public class ATDJournal implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Nemotécnico del cajero", example = "ABCCAP" )
    private String terminalId;
    @ApiModelProperty( value = "Lista de registros almacenados en el Journal", example = "El cliente acepta la comision 35.70" )
    private List < Journal > journal;


    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public List < Journal > getJournal() {
        return journal;
    }

    public void setJournal( List < Journal > journal ) {
        this.journal = journal;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
