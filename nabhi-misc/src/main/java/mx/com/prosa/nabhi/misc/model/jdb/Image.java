package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import us.gonet.nabhi.misc.model.jdb.blob.FileWrapper;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de la estrucutra de una imagen" )
public class Image implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Nombre de la imagen", example = "Backround" )
    private String name;
    @ApiModelProperty( value = "Categoria de la imagen para indicar donde se utilizará", example = "Publicity" )
    private String category;
    @ApiModelProperty( value = "Imagen codificada para su traslado", example = "" )
    private FileWrapper wrapper;
    @ApiModelProperty( value = "ID de la Institución dueña del script", example = "B000" )
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
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
