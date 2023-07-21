package mx.com.prosa.nabhi.misc.domain.algorithm.entity;

import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.misc.model.devices.DevicesWrapper;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "TBL_ATD" )
public class ATDForAlgorithmEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @Column( length = 16, name = "PK_TERMINAL_ID" )
    private String terminalId;

    @Lob
    @Column( name = "DEVICES" )
    private DevicesWrapper terminalDevices;

    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "FK_FIID", foreignKey = @ForeignKey( name = "FK_FIID" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private IDFForAlgorithmEntity idf;

    @ManyToOne( cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "DISPENSED_ALGORITHM", foreignKey = @ForeignKey( name = "FK_DISPENSED_ALGORITHM" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private DispensedAlgorithmEntity dispensedAlgorithm;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public DevicesWrapper getTerminalDevices() {
        return terminalDevices;
    }

    public void setTerminalDevices( DevicesWrapper terminalDevices ) {
        this.terminalDevices = terminalDevices;
    }

    public IDFForAlgorithmEntity getIdf() {
        return idf;
    }

    public void setIdf( IDFForAlgorithmEntity idf ) {
        this.idf = idf;
    }

    public DispensedAlgorithmEntity getDispensedAlgorithm() {
        return dispensedAlgorithm;
    }

    public void setDispensedAlgorithm( DispensedAlgorithmEntity dispensedAlgorithm ) {
        this.dispensedAlgorithm = dispensedAlgorithm;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
