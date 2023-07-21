package mx.com.prosa.nabhi.misc.model.jse.request;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

@ApiModel( description = "Json DTO representación de un modelo con datos genericos para una transacción, estos datos se utilizan para todas las transacciones" )
public class Generic {

    @ApiModelProperty( value = "IP del cajero", example = "172.100.0.2" )
    private String ip;
    @ApiModelProperty( value = "Nemotecnico del cajero", example = "ABCCAP" )
    private String termId;
    @ApiModelProperty( value = "Comisión", example = "10.00" )
    private String txCommission;
    @ApiModelProperty( value = "Código de tipo de cuenta ", example = "10" )
    private String tipoCuenta;
    @ApiModelProperty( value = "Nip encriptado del usuario", example = "2915BEDAA4B8A6B8" )
    private String nip;
    @ApiModelProperty( value = "Track2 de la tarjeta del usuario", example = "5499490525737268=1906201000005741?" )
    private String track;
    @ApiModelProperty( value = "Tags de EMV de targeta de usuario", example = "{9F1C:5445535431323334, 9F1A:0826}" )
    private Map < String, String > emv;

    public String getTermId() {
        return termId;
    }

    public void setTermId( String termId ) {
        this.termId = termId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp( String ip ) {
        this.ip = ip;
    }

    public String getTxCommission() {
        return txCommission;
    }

    public void setTxCommission( String txCommission ) {
        this.txCommission = txCommission;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta( String tipoCuenta ) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getNip() {
        return nip;
    }

    public void setNip( String nip ) {
        this.nip = nip;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack( String track ) {
        this.track = track;
    }

    public Map < String, String > getEmv() {
        return emv;
    }

    public void setEmv( Map < String, String > emv ) {
        this.emv = emv;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }

}
