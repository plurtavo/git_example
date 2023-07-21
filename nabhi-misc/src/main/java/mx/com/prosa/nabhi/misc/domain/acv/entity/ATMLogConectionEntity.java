package mx.com.prosa.nabhi.misc.domain.acv.entity;

import com.google.gson.GsonBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table( name = "TBL_LOG_CONECTION" )
public class ATMLogConectionEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @GeneratedValue( generator = "LOG_CONECTION" )
    @SequenceGenerator( name = "LOG_CONECTION", sequenceName = "LOG_CONECTION_SEQ", allocationSize = 1 )
    @Column( length = 16, name = "PK_ID" )
    private int id;

    @Column( name = "SESION_ID", length = 200 )
    private String sessionId;

    @Column( name = "UP" )
    private Timestamp up;

    @Column( name = "DOWN" )
    private Timestamp down;

    @ManyToOne( cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @Fetch( FetchMode.JOIN )
    @JoinColumn( name = "TERMINAL_ID", foreignKey = @ForeignKey( name = "FK_TBL_ATD" ) )
    private ATDForLogEntity atd;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId( String sesionId ) {
        this.sessionId = sesionId;
    }

    public Timestamp getUp() {
        return up;
    }

    public void setUp( Timestamp up ) {
        this.up = up;
    }

    public Timestamp getDown() {
        return down;
    }

    public void setDown( Timestamp down ) {
        this.down = down;
    }

    public ATDForLogEntity getAtd() {
        return atd;
    }

    public void setAtd( ATDForLogEntity atd ) {
        this.atd = atd;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
