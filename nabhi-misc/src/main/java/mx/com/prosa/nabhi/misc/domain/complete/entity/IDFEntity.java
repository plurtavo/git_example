package mx.com.prosa.nabhi.misc.domain.complete.entity;

import com.google.gson.GsonBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@EntityListeners( AuditTable.class )
@Table( name = "TBL_IDF" )
public class IDFEntity implements Serializable {

    @Id
    @Column( length = 4, name = "PK_FIID" )
    private String fiid;

    @Column( length = 4, name = "LOGICAL_NET" )
    private String logicalNet;

    @Column( length = 45, name = "NAME" )
    private String name;

    @Column( length = 11, name = "ACQUIRING_ID" )
    private String acquiringId;

    @Column( length = 150, name = "NAME_LONG" )
    private String nameLong;

    @Column( length = 10, name = "CURRENT_BUSINESS_DAY" )
    private String currentBusinessDay;

    @Column( length = 10, name = "NEXT_BUSINESS_DAY" )
    private String nextBusinessDay;

    @Column( length = 5, name = "FORCED_CUT_OVER" )
    private String forcedCutOver;

    @Column( length = 255, name = "AGREEMENT" )
    private String agreement;

    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "FK_COUNTRY_CODE", foreignKey = @ForeignKey( name = "FK_COUNTY_CODE" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private CountryEntity country;

    @OneToMany( cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    private Set < SurchargeEntity > surcharges;

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    public String getLogicalNet() {
        return logicalNet;
    }

    public void setLogicalNet( String logicalNet ) {
        this.logicalNet = logicalNet;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getAcquiringId() {
        return acquiringId;
    }

    public void setAcquiringId( String acquiringId ) {
        this.acquiringId = acquiringId;
    }

    public String getNameLong() {
        return nameLong;
    }

    public void setNameLong( String nameLong ) {
        this.nameLong = nameLong;
    }

    public String getCurrentBusinessDay() {
        return currentBusinessDay;
    }

    public void setCurrentBusinessDay( String currentBusinessDay ) {
        this.currentBusinessDay = currentBusinessDay;
    }

    public String getForcedCutOver() {
        return forcedCutOver;
    }

    public void setForcedCutOver( String forcedCutOver ) {
        this.forcedCutOver = forcedCutOver;
    }

    public String getNextBusinessDay() {
        return nextBusinessDay;
    }

    public void setNextBusinessDay( String nextBusinessDay ) {
        this.nextBusinessDay = nextBusinessDay;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement( String agreement ) {
        this.agreement = agreement;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry( CountryEntity country ) {
        this.country = country;
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
