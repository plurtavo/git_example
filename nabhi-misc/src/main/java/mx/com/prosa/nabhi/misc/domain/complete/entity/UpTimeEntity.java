package mx.com.prosa.nabhi.misc.domain.complete.entity;

import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.misc.domain.complete.entity.composite.UpTimeIdentity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table( name = "TBL_UP_TIME" )
public class UpTimeEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @EmbeddedId
    private UpTimeIdentity upTimeId;

    @Column( name = "UP" )
    private int upTime;

    public UpTimeIdentity getUpTimeIdentity() {
        return upTimeId;
    }

    public void setUpTimeIdentity( UpTimeIdentity upTimeId ) {
        this.upTimeId = upTimeId;
    }

    public int getUpTime() {
        return upTime;
    }

    public void setUpTime( int upTime ) {
        this.upTime = upTime;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
