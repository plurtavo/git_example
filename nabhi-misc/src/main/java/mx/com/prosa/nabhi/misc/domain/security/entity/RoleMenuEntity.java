package mx.com.prosa.nabhi.misc.domain.security.entity;

import com.google.gson.GsonBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table( name = "TBL_ROLE" )
public class RoleMenuEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    public RoleMenuEntity() {
    }

    public RoleMenuEntity( String name ) {
        this.name = name;
    }

    @Id
    @Column( length = 100, name = "NAME" )
    private String name;

    @OneToMany( cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinTable( name = "TBL_ROLE_MENU",
            joinColumns = @JoinColumn( name = "NAME", foreignKey = @ForeignKey( name = "FK_ROLE_NAME" ) ),
            inverseJoinColumns = @JoinColumn( name = "PK_ID", foreignKey = @ForeignKey( name = "FK_MENU_ID" ) ) )
    @Fetch( FetchMode.JOIN )
    private Set < MenuEntity > menus;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public Set < MenuEntity > getMenus() {
        return menus;
    }

    public void setMenus( Set < MenuEntity > menus ) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
