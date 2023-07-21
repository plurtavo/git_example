package mx.com.prosa.nabhi.misc.model.iso;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

@ApiModel( description = "Json DTO representación una solicitud de transacción" )
public class ATMRequestModel {

    @ApiModelProperty( value = "Código de transacción", example = "31" )
    private String tranCode;
    @ApiModelProperty( value = "Código de tipo cuenta origen", example = "20" )
    private String fromAccount;
    @ApiModelProperty( value = "Código de tipo de cuenta destino", example = "00" )
    private String toAccount;
    @ApiModelProperty( value = "Monto de retiro/cargo", example = "250.00" )
    private String amount;
    @ApiModelProperty( value = "Entry mode", example = "05" )
    private String entryMode;
    @ApiModelProperty( value = "Comisión por la transacción", example = "12.50" )
    private String surcharge;
    @ApiModelProperty( value = "Track2 de la tarjeta", example = "5470464958812131=17032010000080300000" )
    private String track2;
    @ApiModelProperty( value = "Número de secuencia de la transacción", example = "11" )
    private String sequenceNumber;
    @ApiModelProperty( value = "Nemotecnico del cajero", example = "ABCCAP" )
    private String termId;
    @ApiModelProperty( value = "Nombre del dueño de la terminal", example = "Prosa" )
    private String termOwnerName;
    @ApiModelProperty( value = "Ciudad actual del cajero", example = "Coyoacan" )
    private String termCity;
    @ApiModelProperty( value = "Estado actual del cajero", example = "Ciudad de México" )
    private String termState;
    @ApiModelProperty( value = "País actual del cajero", example = "México" )
    private String termCountry;
    @ApiModelProperty( value = "Allowed group de transacciones permitidas", example = "4" )
    private String groupAllow;
    @ApiModelProperty( value = "Código de moneda", example = "484" )
    private String currencyCode;
    @ApiModelProperty( value = "Pinblock del usuario (Nip encriptado)", example = "0E69FBD96E42C50C" )
    private String pinBlock;
    @ApiModelProperty( value = "Nuevo pinblock (para cambio de nip)", example = "0E69FBD96E42C50C" )
    private String newPinBlock;
    @ApiModelProperty( value = "Confirmación de nuevo pinblock (para cambio de nip)", example = "0E69FBD96E42C50C" )
    private String newPinBlock2;
    @ApiModelProperty( value = "IDF dueña del cajero", example = "B000" )
    private String termFiid;
    @ApiModelProperty( value = "Red lógica por donde viaja la transacción", example = "PROS1" )
    private String lNet;
    @ApiModelProperty( value = "Zona horaria", example = "America/Mexico_City" )
    private String timeOffSet;
    @ApiModelProperty( value = "Tags de EMV de targeta de usuario", example = "{9F1C:5445535431323334, 9F1A:0826}" )
    private Map < String, String > emv;
    @ApiModelProperty( value = "Tipo de cajero (NCR, Diebold, otro)", example = "22" )
    private String termType;
    @ApiModelProperty( value = "Compañia telefonica (para VTA)", example = "MOVISTAR" )
    private String company;
    @ApiModelProperty( value = "Número telefonico (para VTA)", example = "000134657" )
    private String phoneNumber;
    @ApiModelProperty( value = "Confirmación de número telefonico", example = "0001234657" )
    private String phoneNumber2;
    @ApiModelProperty( value = "Nodo para traslado de NIP", example = "S1A^TEST^JKE" )
    private String nodeExchange;
    @ApiModelProperty( value = "Nodo para conexión con interfaz HISO", example = "S1A^TEST^ISO" )
    private String nodeIso;
    @ApiModelProperty( value = "Cuenta destino", example = "5470464958812131" )
    private String toAccountP103;
    @ApiModelProperty( value = "Campos extras para cadena ISO", example = "" )
    private Map < String, String > extra;
    @ApiModelProperty( value = "ID adquiriente", example = "00000000001" )
    private String acquiringId;



    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode( String tranCode ) {
        this.tranCode = tranCode;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount( String fromAccount ) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount( String toAccount ) {
        this.toAccount = toAccount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount( String amount ) {
        this.amount = amount;
    }

    public String getEntryMode() {
        return entryMode;
    }

    public void setEntryMode( String entryMode ) {
        this.entryMode = entryMode;
    }

    public String getSurcharge() {
        return surcharge;
    }

    public void setSurcharge( String surcharge ) {
        this.surcharge = surcharge;
    }

    public String getTrack2() {
        return track2;
    }

    public void setTrack2( String track2 ) {
        this.track2 = track2;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber( String sequenceNumber ) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId( String termId ) {
        this.termId = termId;
    }

    public String getTermOwnerName() {
        return termOwnerName;
    }

    public void setTermOwnerName( String termOwnerName ) {
        this.termOwnerName = termOwnerName;
    }

    public String getTermCity() {
        return termCity;
    }

    public void setTermCity( String termCity ) {
        this.termCity = termCity;
    }

    public String getTermState() {
        return termState;
    }

    public void setTermState( String termState ) {
        this.termState = termState;
    }

    public String getTermCountry() {
        return termCountry;
    }

    public void setTermCountry( String termCountry ) {
        this.termCountry = termCountry;
    }

    public String getGroupAllow() {
        return groupAllow;
    }

    public void setGroupAllow( String groupAllow ) {
        this.groupAllow = groupAllow;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode( String currencyCode ) {
        this.currencyCode = currencyCode;
    }

    public String getPinBlock() {
        return pinBlock;
    }

    public void setPinBlock( String pinBlock ) {
        this.pinBlock = pinBlock;
    }

    public String getNewPinBlock() {
        return newPinBlock;
    }

    public void setNewPinBlock( String newPinBlock ) {
        this.newPinBlock = newPinBlock;
    }

    public String getNewPinBlock2() {
        return newPinBlock2;
    }

    public void setNewPinBlock2( String newPinBlock2 ) {
        this.newPinBlock2 = newPinBlock2;
    }

    public String getTermFiid() {
        return termFiid;
    }

    public void setTermFiid( String termFiid ) {
        this.termFiid = termFiid;
    }

    public String getlNet() {
        return lNet;
    }

    public void setlNet( String lNet ) {
        this.lNet = lNet;
    }

    public String getTimeOffSet() {
        return timeOffSet;
    }

    public void setTimeOffSet( String timeOffSet ) {
        this.timeOffSet = timeOffSet;
    }

    public Map < String, String > getEmv() {
        return emv;
    }

    public void setEmv( Map < String, String > emv ) {
        this.emv = emv;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType( String termType ) {
        this.termType = termType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany( String company ) {
        this.company = company;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber ) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2( String phoneNumber2 ) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getNodeExchange() {
        return nodeExchange;
    }

    public void setNodeExchange( String nodeExchange ) {
        this.nodeExchange = nodeExchange;
    }

    public String getNodeIso() {
        return nodeIso;
    }

    public void setNodeIso( String nodeIso ) {
        this.nodeIso = nodeIso;
    }

    public String getToAccountP103() {
        return toAccountP103;
    }

    public void setToAccountP103( String toAccountP103 ) {
        this.toAccountP103 = toAccountP103;
    }

    public Map < String, String > getExtra() {
        return extra;
    }

    public void setExtra( Map < String, String > extra ) {
        this.extra = extra;
    }

    public String getAcquiringId() {
        return acquiringId;
    }

    public void setAcquiringId( String acquiringId ) {
        this.acquiringId = acquiringId;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
