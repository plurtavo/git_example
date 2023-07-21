package mx.com.prosa.nabhi.misc.domain.single.entity;

import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.misc.domain.complete.entity.AuditTable;
import mx.com.prosa.nabhi.misc.domain.complete.entity.CountryEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@EntityListeners( AuditTable.class )
@Table( name = "TBL_IDF" )
public class IDFEntityKey implements Serializable {

    @Id
    @Column( length = 4, name = "PK_FIID" )
    private String fiid;

    @Column( length = 4, name = "LOGICAL_NET" )
    private String logicalNet;

    @Column( length = 45, name = "NAME" )
    private String name;

    @Column( length = 255, name = "AGREEMENT" )
    private String agreement;

    @Column( length = 11, name = "ACQUIRING_ID" )
    private String acquiringId;

    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "FK_COUNTRY_CODE", foreignKey = @ForeignKey( name = "FK_COUNTY_CODE" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private CountryEntity country;

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement( String agreement ) {
        this.agreement = agreement;
    }

    public String getAcquiringId() {
        return acquiringId;
    }

    public void setAcquiringId( String acquiringId ) {
        this.acquiringId = acquiringId;
    }

    public String getLogicalNet() {
        return logicalNet;
    }

    public void setLogicalNet(String logicalNet) {
        this.logicalNet = logicalNet;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry( CountryEntity country ) {
        this.country = country;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }

}
