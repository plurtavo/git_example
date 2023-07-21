package mx.com.prosa.nabhi.misc.domain.security.entity;

import com.google.gson.GsonBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "TBL_MENU",  uniqueConstraints = @UniqueConstraint( columnNames = "MENU", name = "NDX_UK_MENU" ) )
public class MenuEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @GeneratedValue( generator = "MENU" )
    @SequenceGenerator( name = "MENU", sequenceName = "MENU_SEQ", allocationSize = 1 )
    @Column( length = 16, name = "PK_ID" )
    private int id;

    @Column( name = "MENU", length = 150 )
    private String menu;

    @Column( name = "PARAMS", length = 500 )
    private String params;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu( String menu ) {
        this.menu = menu;
    }

    public String getParams() {
        return params;
    }

    public void setParams( String params ) {
        this.params = params;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}