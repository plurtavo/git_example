package mx.com.prosa.nabhi.misc.domain.complete.entity;


import com.google.gson.GsonBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table( name = "TBL_STATE" )
public class StateEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @Column( length = 2, name = "PK_STATE_CODE" )
    private String stateCode;

    @Column( length = 20, name = "STATE_NAME" )
    private String stateName;

    @Column( length = 3, name = "STATE_SHORT_NAME" )
    private String stateShortName;

    @Column( length = 50, name = "ZONE_UTC" )
    private String zone;

    @OneToMany( cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @Fetch( FetchMode.JOIN )
    private Set < CountyEntity > counties;

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode( String stateCode ) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName( String stateName ) {
        this.stateName = stateName;
    }

    public String getStateShortName() {
        return stateShortName;
    }

    public void setStateShortName( String stateShortName ) {
        this.stateShortName = stateShortName;
    }

    public String getZone() {
        return zone;
    }

    public void setZone( String zone ) {
        this.zone = zone;
    }

    public Set < CountyEntity > getCounties() {
        return counties;
    }

    public void setCounties( Set < CountyEntity > counties ) {
        this.counties = counties;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
