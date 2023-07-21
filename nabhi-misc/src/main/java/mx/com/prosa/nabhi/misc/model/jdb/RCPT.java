package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de un formato para recibo" )
public class RCPT implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Nombre", example = "TEST-B000-01-CLIENTE" )
    private String name;
    @ApiModelProperty( value = "ID del recibo", example = "1" )
    private int id;
    @ApiModelProperty( value = "FIID", example = "B000" )
    private String fiid;
    @ApiModelProperty( value = "Codigo de transacción", example = "01" )
    private String tranCode;
    @ApiModelProperty( value = "Cliente o Journal", example = "true" )
    private boolean costumer;
    @ApiModelProperty( value = "Template con el cual se forma el Header de un recibo", example = "@FECHA[IT1],#DA[IT1],@HORA[IT1],#T1[IT1],@CAJERO[IT1],#AT[IT1],@BANCO[IT1],#OWNER[IT1],@UBICACION[IT1],#LOC[IT1]," )
    private String header;
    @ApiModelProperty( value = "Template con el cual se forma el Body de un recibo", example = "@TARJETA:[IT1],#PAN[IT1],@FOLIO:[IT1],#SEC[IT1],@TRANSACCION:[IT1],#TC[IT1],@CUENTA[IT1],#ATC[IT1],@SALDO ANTERIOR:[IT1],#OB[IT1],@IMP:[IT1],#AMT[IT1],@COMISION:[IT1],#SRH[IT1],@TOTAL:[IT1],#TT[IT1],@SALDO ACTUAL:[IT1],#CB[IT1],@AID:[IT1],#AID[IT1],@PROD:[IT1],#PROD[IT1],@ARQC:[IT1],#ARQC[IT1],@FECHA CONTABLE:[IT1],#DA[1IT],@CODSER:[IT1],#COD[IT1],@PEM:[IT1],#PEM[IT1],@TERMCAP:[IT1],#TCAP[IT1],@NUMAUT:[IT1],#AC[IT1]," )
    private String body;
    @ApiModelProperty( value = "Template con el cual se forma el Trailer de un recibo", example = "@GRACIAS POR UTILIZAR EL CAJERO, VUELVA PRONTO" )
    private String trailer;


    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode( String tranCode ) {
        this.tranCode = tranCode;
    }

    public boolean isCostumer() {
        return costumer;
    }

    public void setCostumer( boolean costumer ) {
        this.costumer = costumer;
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
