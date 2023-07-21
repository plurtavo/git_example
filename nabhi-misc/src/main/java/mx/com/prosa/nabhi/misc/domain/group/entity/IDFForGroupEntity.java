package mx.com.prosa.nabhi.misc.domain.group.entity;

import com.google.gson.GsonBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table( name = "TBL_IDF" )
public class IDFForGroupEntity implements Serializable {

    @Id
    @Column( length = 4, name = "PK_FIID" )
    private String fiid;

    public IDFForGroupEntity() {
    }

    public IDFForGroupEntity( String fiid ) {
        this.fiid = fiid;
    }

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }

}
