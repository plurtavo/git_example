package mx.com.prosa.nabhi.misc.model.jke;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representación de una solicitud de llave" )
public class KeyRequest {

    @ApiModelProperty( value = "Tipo de cajero", example = "22" )
    private String termType;
    @ApiModelProperty( value = "Nemotecnico del cajero", example = "ABCCAP" )
    private String atmRemote;
    @ApiModelProperty( value = "Nombre de", example = "" )
    private String atmLocal;
    @ApiModelProperty( value = "Pinblock del usuario", example = "0C59E751F2C6228A" )
    private String pinBlock;
    @ApiModelProperty( value = "Track2 del usuario", example = "4152313249710307=17032010000080300000" )
    private String track2;
    @ApiModelProperty( value = "Valor del IPK", example = "000000000000" )
    private String ipk;
    @ApiModelProperty( value = "Secuencia de solicitud", example = "1" )
    private String sequence;


    public String getTermType() {
        return termType;
    }

    public void setTermType( String termType ) {
        this.termType = termType;
    }

    public String getAtmRemote() {
        return atmRemote;
    }

    public void setAtmRemote( String atmRemote ) {
        this.atmRemote = atmRemote;
    }

    public String getAtmLocal() {
        return atmLocal;
    }

    public void setAtmLocal( String atmLocal ) {
        this.atmLocal = atmLocal;
    }

    public String getPinBlock() {
        return pinBlock;
    }

    public void setPinBlock( String pinBlock ) {
        this.pinBlock = pinBlock;
    }

    public String getTrack2() {
        return track2;
    }

    public void setTrack2( String track2 ) {
        this.track2 = track2;
    }

    public String getIpk() {
        return ipk;
    }

    public void setIpk( String ipk ) {
        this.ipk = ipk;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence( String sequence ) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
