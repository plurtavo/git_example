package mx.com.prosa.nabhi.misc.domain.personalized.entity;

import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.misc.domain.complete.entity.AuditTable;
import mx.com.prosa.nabhi.misc.domain.complete.entity.SurchargeEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@EntityListeners( AuditTable.class )
@Table( name = "TBL_IDF" )
public class IDFSurchargeEntity implements Serializable {

    @Id
    @Column( length = 4, name = "PK_FIID" )
    private String fiid;

    @Column( length = 45, name = "NAME" )
    private String name;

    @OneToMany( cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    private Set < SurchargeEntity > surcharges;


    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    public Set < SurchargeEntity > getSurcharges() {
        return surcharges;
    }

    public void setSurcharges( Set < SurchargeEntity > surcharges ) {
        this.surcharges = surcharges;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }

}
