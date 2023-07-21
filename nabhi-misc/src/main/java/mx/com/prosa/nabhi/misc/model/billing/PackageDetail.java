package mx.com.prosa.nabhi.misc.model.billing;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representación de los detalles de un banco para reporte" )
public class PackageDetail {

    @ApiModelProperty( value = "Nombre del paquete adquirido", example = "ADVANCE" )
    private String packageName;
    @ApiModelProperty( value = "Número de cajero conectados con este paquete", example = "4" )
    private int numberOfATM;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName( String packageName ) {
        this.packageName = packageName;
    }

    public int getNumberOfATM() {
        return numberOfATM;
    }

    public void setNumberOfATM( int numberOfATM ) {
        this.numberOfATM = numberOfATM;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
