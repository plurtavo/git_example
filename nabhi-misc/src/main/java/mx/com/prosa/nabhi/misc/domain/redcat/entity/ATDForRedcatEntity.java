package mx.com.prosa.nabhi.misc.domain.redcat.entity;

import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.misc.model.devices.DevicesWrapper;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "TBL_ATD" )
public class ATDForRedcatEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @Column( length = 16, name = "PK_TERMINAL_ID" )
    private String terminalId;

    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "FK_FIID", foreignKey = @ForeignKey( name = "FK_FIID" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private IDFForRedcatEntity idf;

    @Lob
    @Column( name = "DEVICES" )
    private DevicesWrapper terminalDevices;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public IDFForRedcatEntity getIdf() {
        return idf;
    }

    public void setIdf(IDFForRedcatEntity idf) {
        this.idf = idf;
    }

    public DevicesWrapper getTerminalDevices() {
        return terminalDevices;
    }

    public void setTerminalDevices( DevicesWrapper terminalDevices ) {
        this.terminalDevices = terminalDevices;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}

