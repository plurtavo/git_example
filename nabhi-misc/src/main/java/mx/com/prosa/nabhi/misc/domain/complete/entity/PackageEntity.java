package mx.com.prosa.nabhi.misc.domain.complete.entity;

import com.google.gson.GsonBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table( name = "TBL_PACKAGE" )
public class PackageEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @Column( name = "PK_NAME", length = 20 )
    private String name;

    @ManyToMany( cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @Fetch( FetchMode.JOIN )
    private Set < ComponentEntity > components;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public Set < ComponentEntity > getComponents() {
        return components;
    }

    public void setComponents( Set < ComponentEntity > components ) {
        this.components = components;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
