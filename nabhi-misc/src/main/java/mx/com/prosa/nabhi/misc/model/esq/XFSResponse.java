package mx.com.prosa.nabhi.misc.model.esq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representación de una respuesta de eventos" )
public class XFSResponse {

    @ApiModelProperty( value = "Código de respuesta del servicio de monitoreo", example = "10" )
    private int codResp;
    @ApiModelProperty( value = "Mensaje de respuesta del servicio de monitoreo", example = "10" )
    private String descripcion;
    @ApiModelProperty( value = "Estado del mensaje", example = "1" )
    private int estado;

    public int getCodResp() {
        return codResp;
    }

    public void setCodResp(int codResp) {
        this.codResp = codResp;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion( String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public String toString( ) {
        final StringBuilder sb = new StringBuilder( "XFSResponse{" );
        sb.append( "codResp=" ).append( codResp );
        sb.append( ", descripcion='" ).append( descripcion ).append( '\'' );
        sb.append( ", estado=" ).append( estado );
        sb.append( '}' );
        return sb.toString( );
    }
}
