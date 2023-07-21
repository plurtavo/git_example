package mx.com.prosa.nabhi.misc.model.jke;


import io.swagger.annotations.ApiModelProperty;

public class TmkEntity {

    @ApiModelProperty( value = "Classe de solicitud", example = "9" )
    private String clazz;
    @ApiModelProperty( value = "Código de error", example = "0" )
    private String error;
    @ApiModelProperty( value = "Código de error", example = "0" )
    private String encrypType;
    @ApiModelProperty( value = "Modo de traslado", example = "" )
    private String mode;
    @ApiModelProperty( value = "Valor de llave TPK en hexadecimal", example = "123456789ABCDEF0123456789ABCDEF0" )
    private String tpk;
    @ApiModelProperty( value = "Número de secuencia de solicitud", example = "3" )
    private String sequence;
    @ApiModelProperty( value = "Nuevo Pinblock", example = "0C59E751F2C6228A" )
    private String pinBlock;

    public String getClazz() {
        return clazz;
    }

    public void setClazz( String clazz ) {
        this.clazz = clazz;
    }

    public String getError() {
        return error;
    }

    public void setError( String error ) {
        this.error = error;
    }

    public String getEncrypType() {
        return encrypType;
    }

    public void setEncrypType( String encrypType ) {
        this.encrypType = encrypType;
    }

    public String getMode() {
        return mode;
    }

    public void setMode( String mode ) {
        this.mode = mode;
    }

    public String getTpk() {
        return tpk;
    }

    public void setTpk( String tpk ) {
        this.tpk = tpk;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence( String sequence ) {
        this.sequence = sequence;
    }

    public String getPinBlock() {
        return pinBlock;
    }

    public void setPinBlock( String pinBlock ) {
        this.pinBlock = pinBlock;
    }
}
