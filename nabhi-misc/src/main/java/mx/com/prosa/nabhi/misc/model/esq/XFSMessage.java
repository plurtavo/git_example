package mx.com.prosa.nabhi.misc.model.esq;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representación de un mensaje para el monitoreo de eventos" )
public class XFSMessage {

    @ApiModelProperty( value = "Nemotécnico del cajero", example = "ABCCAP" )
    private String nemoTecnico;
    @ApiModelProperty( value = "Código XFS a guardar", example = "301" )
    private int codigoXFS;

    public XFSMessage( String nemoTecnico, int codigoXFS ) {
        this.nemoTecnico = nemoTecnico;
        this.codigoXFS = codigoXFS;
    }

    public String getNemoTecnico() {
        return nemoTecnico;
    }

    public void setNemoTecnico( String nemoTecnico) {
        this.nemoTecnico = nemoTecnico;
    }

    public int getCodigoXFS() {
        return codigoXFS;
    }

    public void setCodigoXFS(int codigoXFS) {
        this.codigoXFS = codigoXFS;
    }
    
    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
