package mx.com.prosa.nabhi.misc.model.jse.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel( description = "Json DTO representación de una solicitud de información del cajero " )
public class AtmInfo {

    @ApiModelProperty( value = "IP del cajero", example = "172.100.0.2" )
    private String ip;
    @ApiModelProperty( value = "Nemotecnico del cajero", example = "ABCCAP" )
    private String termId;
    @ApiModelProperty( value = "Track2 de la tarjeta del usuario", example = "5499490525737268=1906201000005741?" )
    private String track;
    @ApiModelProperty( value = "Código de transacción", example = "01" )
    private String transactionCode;
    @ApiModelProperty( value = "Lista de mensajer para almacenar en journal", example = "{Finaliza la transacción}" )
    private List < String > buffer;


    public AtmInfo() {
    }

    public AtmInfo( String ip, String track, String transactionCode, List < String > buffer, String termId ) {
        this.ip = ip;
        this.track = track;
        this.transactionCode = transactionCode;
        this.buffer = buffer;
        this.termId = termId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp( String ip ) {
        this.ip = ip;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack( String track ) {
        this.track = track;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode( String transactionCode ) {
        this.transactionCode = transactionCode;
    }

    public List < String > getBuffer() {
        return buffer;
    }

    public void setBuffer( List < String > buffer ) {
        this.buffer = buffer;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId( String termId ) {
        this.termId = termId;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        if ( !super.equals( o ) ) return false;

        AtmInfo atmInfo = ( AtmInfo ) o;

        if ( ip != null ? !ip.equals( atmInfo.ip ) : atmInfo.ip != null ) return false;
        if ( track != null ? !track.equals( atmInfo.track ) : atmInfo.track != null ) return false;
        if ( transactionCode != null ? !transactionCode.equals( atmInfo.transactionCode ) : atmInfo.transactionCode != null )
            return false;
        if ( buffer != null ? !buffer.equals( atmInfo.buffer ) : atmInfo.buffer != null ) return false;
        return termId != null ? termId.equals( atmInfo.termId ) : atmInfo.termId == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + ( ip != null ? ip.hashCode() : 0 );
        result = 31 * result + ( track != null ? track.hashCode() : 0 );
        result = 31 * result + ( transactionCode != null ? transactionCode.hashCode() : 0 );
        result = 31 * result + ( buffer != null ? buffer.hashCode() : 0 );
        result = 31 * result + ( termId != null ? termId.hashCode() : 0 );
        return result;
    }

    @Override
    public String toString() {
        return "AtmInfo{" +
                "ip='" + ip + '\'' +
                ", track='" + track + '\'' +
                ", transactionCode='" + transactionCode + '\'' +
                ", buffer=" + buffer +
                ", termId='" + termId + '\'' +
                '}';
    }
}
