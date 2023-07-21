package mx.com.prosa.nabhi.misc.domain.complete.entity;

import com.google.gson.GsonBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@EntityListeners( AuditTable.class )
@Table( name = "TBL_BIN" )
public class BINEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @GeneratedValue( generator = "BIN" )
    @SequenceGenerator( name = "BIN", sequenceName = "BIN_SEQ", allocationSize = 1 )
    @Column( name = "PK_ID" )
    private int id;

    @Column( length = 11, name = "BIN" )
    private String bin;

    @Column( name = "PAN_LEN" )
    private int panLen;

    @Column( name = "BIN_LEN" )
    private int binLen;

    @Column( name = "FIID", length = 4 )
    private String fiid;

    @Column( length = 50, name = "DESCRIPTION" )
    private String description;


    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getBin() {
        return bin;
    }

    public void setBin( String bin ) {
        this.bin = bin;
    }

    public int getPanLen() {
        return panLen;
    }

    public void setPanLen( int panLen ) {
        this.panLen = panLen;
    }

    public int getBinLen() {
        return binLen;
    }

    public void setBinLen( int binLen ) {
        this.binLen = binLen;
    }

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
