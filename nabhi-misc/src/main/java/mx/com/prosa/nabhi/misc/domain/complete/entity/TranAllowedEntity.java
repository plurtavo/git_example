package mx.com.prosa.nabhi.misc.domain.complete.entity;

import com.google.gson.GsonBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table( name = "TBL_TRAN_ALLOW" )
public class TranAllowedEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @Column( name = "PK_ALLOWED_CODE", length = 1 )
    private String allowedCode;

    public String getAllowedCode() {
        return allowedCode;
    }

    public void setAllowedCode( String allowedCode ) {
        this.allowedCode = allowedCode;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
