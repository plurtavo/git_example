package mx.com.prosa.nabhi.misc.model.iso;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import us.gonet.serializable.data.ISO;

@ApiModel( description = "Json DTO representación una respuesta de autorización de transacción" )
public class ATMResponseModel {

    @ApiModelProperty( value = "Recibo", example = "" +
            "            \"FECHA         HORA     CAJERO          \\n\" +\n" +
            "            \"24/06/21      01:38    ABCCAP          \\n\" +\n" +
            "            \"PROS     \\n\" +\n" +
            "            \"CUAUHTEMOC    DIF\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"TARJETA:               547046XXXXXX2131\\n\" +\n" +
            "            \"FOLIO:                           994941\\n\" +\n" +
            "            \"RETIRO\\n\" +\n" +
            "            \"TIPO CUENTA: CUENTA DE AHORROS                     \\n\" +\n" +
            "            \"SALDO ANTERIOR:             $859,697.38\\n\" +\n" +
            "            \"MONTO RETIRADO:                 $100.00\\n\" +\n" +
            "            \"COMISION:                        $29.99\\n\" +\n" +
            "            \"IVA:                              $5.71\\n\" +\n" +
            "            \"TOTAL:                          $135.70\\n\" +\n" +
            "            \"SALDO ACTUAL:               $859,561.69\\n\" +\n" +
            "            \"AID:                     A0000000041010\\n\" +\n" +
            "            \"ARQC:                  359D032AE0A95825\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"GRACIAS POR UTILIZAR LOS CAJEROS\\n\" +\n" +
            "            \"VUELVA PRONTO\\n\" +\n" +
            "            \"ATM CLOUD\" )" )
    private String receipt;
    @ApiModelProperty( value = "Cadena ISO de respuesta", example = "ISO0160000500210B23062300003139375470464958812131=1703201000008030000000000000000499494100ABCCAP!" )
    private ISO message;

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt( String receipt ) {
        this.receipt = receipt;
    }

    public ISO getMessage() {
        return message;
    }

    public void setMessage( ISO message ) {
        this.message = message;
    }
}
