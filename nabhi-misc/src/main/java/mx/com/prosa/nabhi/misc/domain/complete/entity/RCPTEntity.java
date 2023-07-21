package mx.com.prosa.nabhi.misc.domain.complete.entity;

import com.google.gson.GsonBuilder;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@EntityListeners( AuditTable.class )
@Table( name = "TBL_RCPT", uniqueConstraints = @UniqueConstraint( columnNames = "NOMBRE", name = "NDX_UK_NOMBRE_RCPT" ) )
@TypeDef( name = "json", typeClass = JsonStringType.class )
public class RCPTEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @GeneratedValue( generator = "RCPT" )
    @SequenceGenerator( name = "RCPT", sequenceName = "RCPT_SEQ", allocationSize = 1 )
    @Column( name = "PK_ID" )
    private int id;

    @Column( length = 100, name = "NOMBRE" )
    private String name;

    @Column( length = 4, name = "FIID" )
    private String fiid;

    @Column( length = 2, name = "TRAN_CODE" )
    private String tranCode;

    @Column( name = "COSTUMER" )
    private boolean costumer;

    @Type( type = "json" )
    @Column( name = "HEADER" )
    private String header;

    @Type( type = "json" )
    @Column( name = "BODY" )
    private String body;

    @Type( type = "json" )
    @Column( name = "TRAILER" )
    private String trailer;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getFiid() {
        return fiid;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode( String tranCode ) {
        this.tranCode = tranCode;
    }

    public boolean isCostumer() {
        return costumer;
    }

    public void setCostumer( boolean costumer ) {
        this.costumer = costumer;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader( String header ) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody( String body ) {
        this.body = body;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer( String trailer ) {
        this.trailer = trailer;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
