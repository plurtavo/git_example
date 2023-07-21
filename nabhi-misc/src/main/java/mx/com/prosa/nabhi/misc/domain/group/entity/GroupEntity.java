package mx.com.prosa.nabhi.misc.domain.group.entity;

import com.google.gson.GsonBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table( name = "TBL_GROUP", uniqueConstraints = @UniqueConstraint( columnNames = "NOMBRE", name = "NDX_UK_GRUPO_NOMBRE" ) )
public class GroupEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @GeneratedValue( generator = "GROUP" )
    @SequenceGenerator( name = "GROUP", sequenceName = "GROUP_SEQ", allocationSize = 1 )
    @Column( name = "PK_ID" )
    private int id;

    @Column( name = "NOMBRE", length = 50 )
    private String name;

    @Column( name = "DESCRIPCION", length = 250 )
    private String description;

    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "FIID", foreignKey = @ForeignKey( name = "FK_GROUP_FIID" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private IDFForGroupEntity idf;

    @OneToMany( cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinTable( name = "TBL_GROUP_ATD",
            joinColumns = @JoinColumn( name = "PK_ID", foreignKey = @ForeignKey( name = "FK_TBL_GROUP_ATD_PK_ID" ) ) ,
            inverseJoinColumns = @JoinColumn( name = "PK_TERMINAL_ID", foreignKey = @ForeignKey( name = "FK_TBL_GROUP_ATD_PK_TERMINAL_ID" ) ) )
    @Fetch( FetchMode.JOIN )
    private Set< ATDForGroupEntity > atds;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public IDFForGroupEntity getIdf() {
        return idf;
    }

    public void setIdf( IDFForGroupEntity idf ) {
        this.idf = idf;
    }

    public Set < ATDForGroupEntity > getAtds() {
        return atds;
    }

    public void setAtds( Set < ATDForGroupEntity > atds ) {
        this.atds = atds;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
