package mx.com.prosa.nabhi.misc.model.jdb.personalized;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.jdb.Image;

import java.io.Serializable;
import java.util.List;

@ApiModel( description = "Json DTO representación de un IDF (Para consultar Imagenes)" )
public class IDFImage implements Serializable {

    @ApiModelProperty( value = "ID del FIID", example = "B000" )
    private String fiid;
    @ApiModelProperty( value = "Imagenes pertenecientes a la institución", example = "Background.png" )
    private List < Image > images;

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    public List < Image > getImages() {
        return images;
    }

    public void setImages( List < Image > images ) {
        this.images = images;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }

}
