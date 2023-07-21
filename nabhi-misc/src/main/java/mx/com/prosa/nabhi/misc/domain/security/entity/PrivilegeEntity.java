package mx.com.prosa.nabhi.misc.domain.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table( name = "TBL_PRIVILEGE" )
public class PrivilegeEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    public PrivilegeEntity() {
    }

    public PrivilegeEntity( String name ) {
        this.name = name;
    }

    @Id
    @Column( name = "NAME" )
    private String name;
    //Cambio release/eventos

    @Column( name = "DESCRIPCION", length = 250 )
    private String description;

    @Column( name = "APP" )
    private boolean app;
    //Cambio release/eventos

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    //Cambio release/eventos
    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public boolean isApp() {
        return app;
    }

    public void setApp( boolean app ) {
        this.app = app;
    }
    //Cambio release/eventos
}