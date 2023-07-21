package mx.com.prosa.nabhi.misc.model.receipt;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel( description = "Json DTO representación de un formato para probar un recibo en la consola" )
public class TestReceipt {

    @ApiModelProperty( value = "Nemotecnico del cajero", example = "ABCCAP" )
    private String terminalId;
    @ApiModelProperty( value = "Cadena de respuesta de autorización ISO", example = "ISO0160000500210B23062300003139375470464958812131=1703201000008030000000000000000499494100ABCCAP!" )
    private String isoMessage;
    @ApiModelProperty( value = "Template con el cual se forma el Header de un recibo", example = "@FECHA[IT1],#DA[IT1],@HORA[IT1],#T1[IT1],@CAJERO[IT1],#AT[IT1],@BANCO[IT1],#OWNER[IT1],@UBICACION[IT1],#LOC[IT1]," )
    private String header;
    @ApiModelProperty( value = "Template con el cual se forma el Body de un recibo", example = "@TARJETA:[IT1],#PAN[IT1],@FOLIO:[IT1],#SEC[IT1],@TRANSACCION:[IT1],#TC[IT1],@CUENTA[IT1],#ATC[IT1],@SALDO ANTERIOR:[IT1],#OB[IT1],@IMP:[IT1],#AMT[IT1],@COMISION:[IT1],#SRH[IT1],@TOTAL:[IT1],#TT[IT1],@SALDO ACTUAL:[IT1],#CB[IT1],@AID:[IT1],#AID[IT1],@PROD:[IT1],#PROD[IT1],@ARQC:[IT1],#ARQC[IT1],@FECHA CONTABLE:[IT1],#DA[1IT],@CODSER:[IT1],#COD[IT1],@PEM:[IT1],#PEM[IT1],@TERMCAP:[IT1],#TCAP[IT1],@NUMAUT:[IT1],#AC[IT1]," )
    private String body;
    @ApiModelProperty( value = "Template con el cual se forma el Trailer de un recibo", example = "@GRACIAS POR UTILIZAR EL CAJERO, VUELVA PRONTO" )
    private String trailer;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public String getIsoMessage() {
        return isoMessage;
    }

    public void setIsoMessage( String isoMessage ) {
        this.isoMessage = isoMessage;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader( String header ) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody( String body ) {
        this.body = body;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer( String trailer ) {
        this.trailer = trailer;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
