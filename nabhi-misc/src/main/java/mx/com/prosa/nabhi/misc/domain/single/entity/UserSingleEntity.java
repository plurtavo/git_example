package mx.com.prosa.nabhi.misc.domain.single.entity;

import com.google.gson.GsonBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "TBL_USER" )
public class UserSingleEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @Column( length = 45, name = "PK_ID" )
    private String id;

    @ManyToOne( cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "FK_FIID", foreignKey = @ForeignKey( name = "FK_OWNER_USER" ) )
    @Fetch( FetchMode.JOIN )
    private IDFEntityKey owner;

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public IDFEntityKey getOwner() {
        return owner;
    }

    public void setOwner( IDFEntityKey owner ) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
