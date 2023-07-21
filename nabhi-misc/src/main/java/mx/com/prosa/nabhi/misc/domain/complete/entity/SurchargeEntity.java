package mx.com.prosa.nabhi.misc.domain.complete.entity;

import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.misc.domain.complete.entity.composite.SurchargeIdentity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@EntityListeners( AuditTable.class )
@Table( name = "TBL_SURCHARGE" )
public class SurchargeEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @EmbeddedId
    private SurchargeIdentity surchargeId;

    @Column( name = "SURCHARGE" )
    private String surcharges;

    public SurchargeIdentity getSurchargeId() {
        return surchargeId;
    }

    public void setSurchargeId( SurchargeIdentity surchargeId ) {
        this.surchargeId = surchargeId;
    }

    public String getSurcharges() {
        return surcharges;
    }

    public void setSurcharges( String surcharges ) {
        this.surcharges = surcharges;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode( this.surchargeId.getFiidAcquirer() + this.surchargeId.getFiidIssuing() + this.surchargeId.getTranCode() + surcharges );
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
