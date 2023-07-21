package mx.com.prosa.nabhi.misc.domain.group.entity;

import com.google.gson.GsonBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "TBL_ATD" )
public class ATDForGroupEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @Column( length = 16, name = "PK_TERMINAL_ID" )
    private String terminalId;

    @Column( length = 2, name = "DEVICE_TYPE" )
    private String deviceType;

    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "FK_FIID", foreignKey = @ForeignKey( name = "FK_FIID" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private IDFForGroupEntity idf;

    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "FK_COUNTY_CODE_ID", foreignKey = @ForeignKey( name = "FK_COUNTY_CODE_ID" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private CountyForGroupEntity county;

    @Column( length = 10, name = "SCRENN_TYPE" )
    private String screenType;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType( String deviceType ) {
        this.deviceType = deviceType;
    }

    public IDFForGroupEntity getIdf() {
        return idf;
    }

    public void setIdf( IDFForGroupEntity idf ) {
        this.idf = idf;
    }

    public CountyForGroupEntity getCounty() {
        return county;
    }

    public void setCounty( CountyForGroupEntity county ) {
        this.county = county;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType( String screenType ) {
        this.screenType = screenType;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
