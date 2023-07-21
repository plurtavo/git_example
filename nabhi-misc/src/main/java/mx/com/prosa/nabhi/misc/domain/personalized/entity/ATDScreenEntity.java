package mx.com.prosa.nabhi.misc.domain.personalized.entity;

import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.misc.domain.complete.entity.ScreenGroupEntity;
import mx.com.prosa.nabhi.misc.domain.single.entity.IDFEntityKey;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "TBL_ATD" )
public class ATDScreenEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @Column( length = 16, name = "PK_TERMINAL_ID" )
    private String terminalId;

    @Column( length = 15, name = "IP" )
    private String ip;

    @Column( length = 10, name = "SCRENN_TYPE" )
    private String screenType;

    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "FK_FIID", foreignKey = @ForeignKey( name = "FK_FIID" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private IDFEntityKey idf;

    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "FK_GROUP_SCREEN", foreignKey = @ForeignKey( name = "FK_GROUP_SCREEN" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private ScreenGroupEntity screenGroup;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp( String ip ) {
        this.ip = ip;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType( String screenType ) {
        this.screenType = screenType;
    }

    public IDFEntityKey getIdf() {
        return idf;
    }

    public void setIdf( IDFEntityKey idf ) {
        this.idf = idf;
    }

    public ScreenGroupEntity getScreenGroup() {
        return screenGroup;
    }

    public void setScreenGroup( ScreenGroupEntity screenGroup ) {
        this.screenGroup = screenGroup;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
