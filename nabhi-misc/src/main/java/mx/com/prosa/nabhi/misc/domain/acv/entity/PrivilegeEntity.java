package mx.com.prosa.nabhi.misc.domain.acv.entity;

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

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

}