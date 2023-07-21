package mx.com.prosa.nabhi.misc.domain.complete.entity;

import com.google.gson.GsonBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@EntityListeners( AuditTable.class )
@Table( name = "TBL_APC" )
public class APCEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @GeneratedValue( generator = "APC" )
    @SequenceGenerator( name = "APC", sequenceName = "APC_SEQ", allocationSize = 1 )
    @Column( name = "PK_ID" )
    private int id;

    @Column( length = 2, name = "TRAN_CODE" )
    private String tranCode;

    @Column( length = 2, name = "FROM_ACCT" )
    private String formAcct;

    @Column( length = 2, name = "TO_ACCT" )
    private String toAcct;

    @Column( length = 4, name = "FIID" )
    private String fiid;

    @Column( name = "ROUTING_GROUP", length = 11 )
    private String routingGroup;

    @Column( name = "SHARING_GROUP", length = 24 )
    private String sharingGroup;

    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "FK_ALLOWED_CODE", foreignKey = @ForeignKey( name = "FK_ALLOWED_CODE" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private TranAllowedEntity allowedCode;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode( String tranCode ) {
        this.tranCode = tranCode;
    }

    public String getFormAcct() {
        return formAcct;
    }

    public void setFormAcct( String formAcct ) {
        this.formAcct = formAcct;
    }

    public String getToAcct() {
        return toAcct;
    }

    public void setToAcct( String toAcct ) {
        this.toAcct = toAcct;
    }

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    public String getRoutingGroup() {
        return routingGroup;
    }

    public void setRoutingGroup( String routingGroup ) {
        this.routingGroup = routingGroup;
    }

    public String getSharingGroup() {
        return sharingGroup;
    }

    public void setSharingGroup( String sharingGroup ) {
        this.sharingGroup = sharingGroup;
    }

    public TranAllowedEntity getAllowedCode() {
        return allowedCode;
    }

    public void setAllowedCode( TranAllowedEntity allowedCode ) {
        this.allowedCode = allowedCode;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
