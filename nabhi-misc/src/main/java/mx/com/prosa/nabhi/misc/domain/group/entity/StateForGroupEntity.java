package mx.com.prosa.nabhi.misc.domain.group.entity;

import com.google.gson.GsonBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table( name = "TBL_STATE" )
public class StateForGroupEntity implements Serializable {

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

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
