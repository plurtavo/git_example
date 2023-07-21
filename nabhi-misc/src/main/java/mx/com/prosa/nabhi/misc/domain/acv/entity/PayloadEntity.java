package mx.com.prosa.nabhi.misc.domain.acv.entity;

import com.google.gson.GsonBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table( name = "TBL_SCREEN_GROUP" )
public class PayloadEntity implements Serializable {

    private static final long serialVersionUID = 1111L;
    @Id
    @Column( length = 16, name = "PK_GROUP_ID" )
    private String groupId;


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId( String groupId ) {
        this.groupId = groupId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode( this.getGroupId()  );
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
