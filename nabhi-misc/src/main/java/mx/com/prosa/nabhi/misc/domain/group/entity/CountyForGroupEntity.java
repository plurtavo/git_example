package mx.com.prosa.nabhi.misc.domain.group.entity;

import com.google.gson.GsonBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "TBL_COUNTY" )
public class CountyForGroupEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @Column( name = "PK_COUNTY_CODE_ID" )
    @GeneratedValue( strategy = GenerationType.TABLE )
    private int countyCodeId;

    @Column( length = 3, name = "COUNTY_CODE" )
    private String countyCode;

    @Column( length = 45, name = "COUNTY_NAME" )
    private String countyName;

    @ManyToOne( cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @Fetch( FetchMode.JOIN )
    @JoinColumn( name = "FK_STATE_CODE", foreignKey = @ForeignKey( name = "FK_STATE_CODE" ) )
    private StateForGroupEntity state;

    public int getCountyCodeId() {
        return countyCodeId;
    }

    public void setCountyCodeId( int countyCodeId ) {
        this.countyCodeId = countyCodeId;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode( String countyCode ) {
        this.countyCode = countyCode;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName( String countyName ) {
        this.countyName = countyName;
    }

    public StateForGroupEntity getState() {
        return state;
    }

    public void setState( StateForGroupEntity state ) {
        this.state = state;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
