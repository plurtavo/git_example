package mx.com.prosa.nabhi.misc.model.jse.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representación una respuesta para solicitar datos a imprimir en ticket" )
public class Ticket {

    @ApiModelProperty( value = "Código de transacción", example = "31" )
    private String code;
    @ApiModelProperty( value = "Datos a imprimir en el ticket", example = "" +
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
    private String bodyTicket;

    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    public String getBodyTicket() {
        return bodyTicket;
    }

    public void setBodyTicket( String bodyTicket ) {
        this.bodyTicket = bodyTicket;
    }

}
