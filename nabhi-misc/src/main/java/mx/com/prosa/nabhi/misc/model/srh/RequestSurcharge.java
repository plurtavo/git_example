package mx.com.prosa.nabhi.misc.model.srh;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de uuna solicitud de comsión" )
public class RequestSurcharge implements Serializable {


    @ApiModelProperty( value = "Track2 de la tarjetar del usuario", example = "5499490525737268=1906201000005741?" )
    private String track;
    @ApiModelProperty( value = "IP del cajero", example = "172.100.0.2" )
    private String ip;
    @ApiModelProperty( value = "Nemotécnico del cajero", example = "ABCCAP" )
    private String termId;
    @ApiModelProperty( value = "Código de transacción", example = "01" )
    private String transactionCode;

    public String getTrack() {
        return track;
    }

    public void setTrack( String track ) {
        this.track = track;
    }

    public String getIp() {
        return ip;
    }

    public void setIp( String ip ) {
        this.ip = ip;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId( String termId ) {
        this.termId = termId;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode( String transactionCode ) {
        this.transactionCode = transactionCode;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }


}
