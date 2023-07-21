package mx.com.prosa.nabhi.misc.domain.complete.entity.composite;

import com.google.gson.GsonBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

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
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        UpTimeIdentity that = ( UpTimeIdentity ) o;
        return Objects.equals( terminalId, that.terminalId ) && Objects.equals( date, that.date );
    }

    @Override
    public int hashCode() {
        return Objects.hash( terminalId, date );
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
