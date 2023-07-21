package mx.com.prosa.nabhi.misc.domain.acv.entity;

import com.google.gson.GsonBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table( name = "TBL_USER" )
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @Column( length = 45, name = "PK_ID" )
    private String id;

    @Column( name = "PASSWORD", length = 100 )
    private String password;

    @ManyToMany( cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @Fetch( FetchMode.JOIN )
    private Set < RoleEntity > roles;

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public Set < RoleEntity > getRoles() {
        return roles;
    }

    public void setRoles( Set < RoleEntity > roles ) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
