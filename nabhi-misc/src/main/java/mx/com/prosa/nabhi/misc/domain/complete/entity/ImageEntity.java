package mx.com.prosa.nabhi.misc.domain.complete.entity;

import com.google.gson.GsonBuilder;
import us.gonet.nabhi.misc.model.jdb.blob.FileWrapper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@EntityListeners( AuditTable.class )
@Table( name = "TBL_IMAGES" )
public class ImageEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @Column( length = 50, name = "PK_NAME" )
    private String name;

    @Column( length = 50, name = "CATEGORY" )
    private String category;

    @Lob
    @Column( name = "IMAGE" )
    private FileWrapper wrapper;

    @Column( length = 4, name = "FIID" )
    private String fiid;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory( String category ) {
        this.category = category;
    }

    public FileWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper( FileWrapper wrapper ) {
        this.wrapper = wrapper;
    }

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode( this.getName() );
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
