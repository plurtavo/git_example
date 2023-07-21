package mx.com.prosa.nabhi.dash.model.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.dash.model.PageModel;

@ApiModel( description = "Json con los criterios de búsqueda para grupos de cajeros" )
public class CriteriaSearch extends PageModel {

    @ApiModelProperty( value = "Nemotécnico del cajero", example = "N204123" )
    private String terminalId;
    @ApiModelProperty( value = "FIID del banco", example = "B000" )
    private String fiid;
    @ApiModelProperty( value = "Código del estado de la republica", example = "09" )
    private String stateCode;
    @ApiModelProperty( value = "ID del municipio", example = "123" )
    private int countyCode;
    @ApiModelProperty( value = "Tipo de cajero", example = "30" )
    private String deviceType;
    @ApiModelProperty( value = "Tipo de pantalla", example = "TOUCH" )
    private String screenType;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode( String stateCode ) {
        this.stateCode = stateCode;
    }

    public int getCountyCode() {
        return countyCode;
    }

    public void setCountyCode( int countyCode ) {
        this.countyCode = countyCode;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType( String deviceType ) {
        this.deviceType = deviceType;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType( String screenType ) {
        this.screenType = screenType;
    }

}
