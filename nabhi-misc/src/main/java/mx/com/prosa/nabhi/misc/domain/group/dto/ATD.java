package mx.com.prosa.nabhi.misc.domain.group.dto;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de un cajero" )
public class ATD implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Nemotécnico del cajero", example = "N201234" )
    private String terminalId;
    @ApiModelProperty( value = "Tipo de cajero", example = "30" )
    private String deviceType;
    @ApiModelProperty( value = "IDF de la institución dueña del cajero", example = "B000" )
    private IDF idf;
    @ApiModelProperty( value = "Municipio en donde esta el cajero" )
    private County county;
    @ApiModelProperty( value = "Tipo de pantalla", example = "Touch" )
    private String screenType;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType( String deviceType ) {
        this.deviceType = deviceType;
    }

    public IDF getIdf() {
        return idf;
    }

    public void setIdf( IDF idf ) {
        this.idf = idf;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty( County county ) {
        this.county = county;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType( String screenType ) {
        this.screenType = screenType;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
