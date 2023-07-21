package mx.com.prosa.nabhi.misc.model.jdb.personalized;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.jdb.NodeTCP;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de un ATD (Para consulta desde la consola)" )
public class ATDBasic implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Nemotécnico del cajero", example = "ABCCAP" )
    private String terminalId;
    @ApiModelProperty( value = "Número secuencia de transacción del cajero", example = "123" )
    private int sequenceNumber;
    @ApiModelProperty( value = "Número de licencia del cajero", example = "12345679812" )
    private String sequence;
    @ApiModelProperty( value = "Tipo de cajero", example = "NCR" )
    private String deviceType;
    @ApiModelProperty( value = "Día de alta del cajero", example = "10/06/20" )
    private String postingDay;
    @ApiModelProperty( value = "Bandera que identifica si el cajero esta en línea", example = "true" )
    private boolean online;
    @ApiModelProperty( value = "IP del cajero", example = "192.168.100.2" )
    private String ip;
    @ApiModelProperty( value = "Bandera que indica si existe alguna transacción activa en el cajero", example = "false" )
    private boolean activeTrx;
    @ApiModelProperty( value = "Recibo generado para la última transacción", example =
            "FECHA         HORA     CAJERO          \n" +
            "24/06/21      01:38    ABCCAP          \n" +
            "PROS     \n" +
            "CUAUHTEMOC    DIF\n" +
            "\n" +
            "TARJETA:               547046XXXXXX2131\n" +
            "FOLIO:                           994941\n" +
            "RETIRO\n" +
            "TIPO CUENTA: CUENTA DE AHORROS                     \n" +
            "SALDO ANTERIOR:             $859,697.38\n" +
            "MONTO RETIRADO:                 $100.00\n" +
            "COMISION:                        $29.99\n" +
            "IVA:                              $5.71\n" +
            "TOTAL:                          $135.70\n" +
            "SALDO ACTUAL:               $859,561.69\n" +
            "AID:                     A0000000041010\n" +
            "ARQC:                  359D032AE0A95825\n" +
            "\n" +
            "\n" +
            "GRACIAS POR UTILIZAR LOS CAJEROS\n" +
            "VUELVA PRONTO\n" +
            "ATM CLOUD" )
    private String receipt;
    @ApiModelProperty( value = "Nodo por el cual se conectará para el traslado de pinblock", example = "S1A^TEST^JKE" )
    private NodeTCP nodeX;


    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber( Integer sequenceNumber ) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence( String sequence ) {
        this.sequence = sequence;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType( String deviceType ) {
        this.deviceType = deviceType;
    }

    public String getPostingDay() {
        return postingDay;
    }

    public void setPostingDay( String postingDay ) {
        this.postingDay = postingDay;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline( boolean online ) {
        this.online = online;
    }

    public String getIp() {
        return ip;
    }

    public void setIp( String ip ) {
        this.ip = ip;
    }

    public boolean isActiveTrx() {
        return activeTrx;
    }

    public void setActiveTrx( boolean activeTrx ) {
        this.activeTrx = activeTrx;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt( String receipt ) {
        this.receipt = receipt;
    }

    public NodeTCP getNodeX() {
        return nodeX;
    }

    public void setNodeX( NodeTCP nodeX ) {
        this.nodeX = nodeX;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
