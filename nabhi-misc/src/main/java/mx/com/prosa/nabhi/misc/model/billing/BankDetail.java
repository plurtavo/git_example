package mx.com.prosa.nabhi.misc.model.billing;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel( description = "Json DTO representación de los detalles de un banco para reporte" )
public class BankDetail {

    @ApiModelProperty( value = "FIID del banco", example = "B000" )
    private String fiid;
    @ApiModelProperty( value = "Nombre de la institución", example = "Prosa" )
    private String name;
    @ApiModelProperty( value = "Detalles de los paquetes de cajeros asociados", example = "" )
    private List< PackageDetail > detail;

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public List < PackageDetail > getDetail() {
        return detail;
    }

    public void setDetail( List < PackageDetail > detail ) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
