package mx.com.prosa.nabhi.misc.domain.personalized.entity;

import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.misc.domain.complete.entity.CountyEntity;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "TBL_ATD" )
public class ATDUpTimeEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @Column( length = 16, name = "PK_TERMINAL_ID" )
    private String terminalId;

    //Cambio release/monitoreoatm
    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "FK_FIID", foreignKey = @ForeignKey( name = "FK_FIID" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private IDFEntityKey idf;

    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "FK_COUNTY_CODE_ID", foreignKey = @ForeignKey( name = "FK_COUNTY_CODE_ID" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private CountyEntity county;

    @Column( length = 25, name = "LOCATION" )
    private String location;

    @Column( name = "ATM_ONLINE" )
    private boolean online;
    //Cambio release/monitoreoatm

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    //Cambio release/monitoreoatm
    public IDFEntityKey getIdf() {
        return idf;
    }

    public void setIdf( IDFEntityKey idf ) {
        this.idf = idf;
    }

    public CountyEntity getCounty() {
        return county;
    }

    public void setCounty( CountyEntity county ) {
        this.county = county;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation( String location ) {
        this.location = location;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline( boolean online ) {
        this.online = online;
    }
    //Cambio release/monitoreoatm
    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
