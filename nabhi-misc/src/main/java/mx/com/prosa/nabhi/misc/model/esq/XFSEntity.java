package mx.com.prosa.nabhi.misc.model.esq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de un mensaje XFS" )
public class XFSEntity implements Serializable {

    @ApiModelProperty( value = "Código XFS a guardar", example = "301" )
    private int xfsCode;
    @ApiModelProperty( value = "Descripción del mensaje", example = "CASSETTE_MISSING" )
    private String xfsDescription;
    @ApiModelProperty( value = "Código DCC", example = "2000" )
    private int ddcCode;
    @ApiModelProperty( value = "Descripción de mensaje DCC", example = "Cassetera retirada" )
    private String ddcDescription;
    @ApiModelProperty( value = "Severidad del mensaje", example = "2" )
    private int severity;
    @ApiModelProperty( value = "Modulo del cajero", example = "3" )
    private int module;

    XFSEntity( int xfsCode, String xfsDescription, int ddcCode, String ddcDescription, int severity, int module ) {
        this.xfsCode = xfsCode;
        this.xfsDescription = xfsDescription;
        this.ddcCode = ddcCode;
        this.ddcDescription = ddcDescription;
        this.severity = severity;
        this.module = module;
    }

    public int getXfsCode() {
        return xfsCode;
    }

    public void setXfsCode( int xfsCode ) {
        this.xfsCode = xfsCode;
    }

    public String getXfsDescription() {
        return xfsDescription;
    }

    public void setXfsDescription( String xfsDescription ) {
        this.xfsDescription = xfsDescription;
    }

    public int getDdcCode() {
        return ddcCode;
    }

    public void setDdcCode( int ddcCode ) {
        this.ddcCode = ddcCode;
    }

    public String getDdcDescription() {
        return ddcDescription;
    }

    public void setDdcDescription( String ddcDescription ) {
        this.ddcDescription = ddcDescription;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity( int severity ) {
        this.severity = severity;
    }

    public int getModule() {
        return module;
    }

    public void setModule( int module ) {
        this.module = module;
    }
}
