package mx.com.prosa.nabhi.misc.domain.acv.entity.composite;

import com.google.gson.GsonBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Date;

@Embeddable
public class UpTimeIdentity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Column( length = 16, name = "PK_TERMINAL_ID" )
    private String terminalId;

    @Column( name = "PK_DATE" )
    private Date date;

    public UpTimeIdentity() {
    }

    public UpTimeIdentity( String terminalId, Date date ) {
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
