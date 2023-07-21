package mx.com.prosa.nabhi.misc.model.devices;
//Cambio release/redcat: Cambio de paquete

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.devices.cdm.Dispenser;
import mx.com.prosa.nabhi.misc.model.devices.idc.Reader;
import mx.com.prosa.nabhi.misc.model.devices.pin.EPP;
import mx.com.prosa.nabhi.misc.model.devices.ptr.Printer;
import mx.com.prosa.nabhi.misc.model.devices.siu.SIU;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación del conjunto de dispoisitovs de un cajero" )
public class DevicesWrapper implements Serializable {

    private static final long serialVersionUID = 1111L;

    @ApiModelProperty( value = "Dispensador", example = "" )
    private IDevice dispenser;
    @ApiModelProperty( value = "Impresora", example = "" )
    private IDevice printer;
    @ApiModelProperty( value = "Lectora de tarjetas", example = "" )
    private IDevice cardReader;
    @ApiModelProperty( value = "Teclado encriptador", example = "" )
    private IDevice pinPad;
    @ApiModelProperty( value = "Sensores", example = "" )
    private IDevice siu;
    @ApiModelProperty( value = "Reciclador", example = "" )
    private IDevice cashInModule;
    @ApiModelProperty( value = "Lector de código de barras", example = "" )
    private IDevice barCodeReader;
    @ApiModelProperty( value = "Camara", example = "" )
    private IDevice camera;
    @ApiModelProperty( value = "Despositador", example = "" )
    private IDevice depository;
    @ApiModelProperty( value = "Dispensador de monedas", example = "" )
    private IDevice coinDispenser;
    @ApiModelProperty( value = "Lector de cheques", example = "" )
    private IDevice checkReader;
    @ApiModelProperty( value = "Dispositivo de diagnostico", example = "" )
    private IDevice diagnostic;
    @ApiModelProperty( value = "Alarmas", example = "" )
    private IDevice alarm;
    @ApiModelProperty( value = "Embosador de tarjetas", example = "" )
    private IDevice embossingCard;
    @ApiModelProperty( value = "Dispensador de tarjetas", example = "" )
    private IDevice cardDispenser;
    @ApiModelProperty( value = "Procesador de elemenots", example = "" )
    private IDevice itemProcessing;
    @ApiModelProperty( value = "Terminal de texto", example = "" )
    private IDevice textTerminalUnit;


    public Dispenser getDispenser() {
        return (Dispenser) dispenser;
    }

    public void setDispenser( Dispenser dispenser ) {
        this.dispenser = dispenser;
    }

    public Printer getPrinter() {
        return (Printer) printer;
    }

    public void setPrinter( Printer printer ) {
        this.printer = printer;
    }

    public Reader getCardReader() {
        return (Reader) cardReader;
    }

    public void setCardReader( Reader cardReader ) {
        this.cardReader = cardReader;
    }

    public EPP getPinPad() {
        return (EPP) pinPad;
    }

    public void setPinPad( EPP pinPad ) {
        this.pinPad = pinPad;
    }

    public SIU getSiu() {
        return (SIU) siu;
    }

    public void setSiu( SIU siu ) {
        this.siu = siu;
    }

    public IDevice getCashInModule() {
        return cashInModule;
    }

    public void setCashInModule( IDevice cashInModule ) {
        this.cashInModule = cashInModule;
    }

    public IDevice getBarCodeReader() {
        return barCodeReader;
    }

    public void setBarCodeReader( IDevice barCodeReader ) {
        this.barCodeReader = barCodeReader;
    }

    public IDevice getCamera() {
        return camera;
    }

    public void setCamera( IDevice camera ) {
        this.camera = camera;
    }

    public IDevice getDepository() {
        return depository;
    }

    public void setDepository( IDevice depository ) {
        this.depository = depository;
    }

    public IDevice getCoinDispenser() {
        return coinDispenser;
    }

    public void setCoinDispenser( IDevice coinDispenser ) {
        this.coinDispenser = coinDispenser;
    }

    public IDevice getCheckReader() {
        return checkReader;
    }

    public void setCheckReader( IDevice checkReader ) {
        this.checkReader = checkReader;
    }

    public IDevice getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic( IDevice diagnostic ) {
        this.diagnostic = diagnostic;
    }

    public IDevice getAlarm() {
        return alarm;
    }

    public void setAlarm( IDevice alarm ) {
        this.alarm = alarm;
    }

    public IDevice getEmbossingCard() {
        return embossingCard;
    }

    public void setEmbossingCard( IDevice embossingCard ) {
        this.embossingCard = embossingCard;
    }

    public IDevice getCardDispenser() {
        return cardDispenser;
    }

    public void setCardDispenser( IDevice cardDispenser ) {
        this.cardDispenser = cardDispenser;
    }

    public IDevice getItemProcessing() {
        return itemProcessing;
    }

    public void setItemProcessing( IDevice itemProcessing ) {
        this.itemProcessing = itemProcessing;
    }

    public IDevice getTextTerminalUnit() {
        return textTerminalUnit;
    }

    public void setTextTerminalUnit( IDevice textTerminalUnit ) {
        this.textTerminalUnit = textTerminalUnit;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
