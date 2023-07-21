package mx.com.prosa.nabhi.misc.domain.complete.entity;

import com.google.gson.GsonBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
//Cambio release/eventos
@Table( name = "TBL_JOURNAL" )
public class JournalEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @GeneratedValue( generator = "JOURNAL" )
    @SequenceGenerator( name = "JOURNAL", sequenceName = "JOURNAL_SEQ", allocationSize = 1 )
    @Column( name = "PK_ID_JOURNAL" )
    private int id;

    @Column( length = 16, name = "TERMINAL_ID" )
    private String terminalId;

    @Lob
    @Column( name = "MESSAGE" )
    private String message;

    @Column( name = "WRITE_DATE" )
    private Timestamp writeDate;

    public JournalEntity() {
    }

    public JournalEntity( String message, Timestamp writeDate, String terminalId ) {
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

    public Timestamp getWriteDate() {
        return writeDate;
    }

    public void setWriteDate( Timestamp writeDate ) {
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
