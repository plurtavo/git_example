package mx.com.prosa.nabhi.misc.domain.security.entity;

import com.google.gson.GsonBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table( name = "TBL_ROLE" )
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    public RoleEntity() {
    }

    public RoleEntity( String name ) {
        this.name = name;
    }

    @Id
    @Column( length = 100, name = "NAME" )
    private String name;

    @ManyToMany( cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @Fetch( FetchMode.JOIN )
    private Set < PrivilegeEntity > privileges;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public Set < PrivilegeEntity > getPrivileges() {
        return privileges;
    }

    public void setPrivileges( Set < PrivilegeEntity > privileges ) {
        this.privileges = privileges;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
