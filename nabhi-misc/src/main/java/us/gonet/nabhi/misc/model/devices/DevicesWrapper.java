package us.gonet.nabhi.misc.model.devices;

import com.google.gson.GsonBuilder;
import us.gonet.nabhi.misc.model.devices.cdm.Dispenser;
import us.gonet.nabhi.misc.model.devices.idc.Reader;
import us.gonet.nabhi.misc.model.devices.pin.EPP;
import us.gonet.nabhi.misc.model.devices.ptr.Printer;
import us.gonet.nabhi.misc.model.devices.siu.SIU;

import java.io.Serializable;

public class DevicesWrapper implements Serializable {

    private static final long serialVersionUID = 1111L;

    private IDevice dispenser;
    private IDevice printer;
    private IDevice cardReader;
    private IDevice pinPad;
    private IDevice siu;
    private IDevice cashInModule;
    private IDevice barCodeReader;
    private IDevice camera;
    private IDevice depository;
    private IDevice coinDispenser;
    private IDevice checkReader;
    private IDevice diagnostic;
    private IDevice alarm;
    private IDevice embossingCard;
    private IDevice cardDispenser;
    private IDevice itemProcessing;
    private IDevice textTerminalUnit;


    public Dispenser getDispenser() {
        return ( Dispenser ) dispenser;
    }

    public void setDispenser( Dispenser dispenser ) {
        this.dispenser = dispenser;
    }

    public Printer getPrinter() {
        return ( Printer ) printer;
    }

    public void setPrinter( Printer printer ) {
        this.printer = printer;
    }

    public Reader getCardReader() {
        return ( Reader ) cardReader;
    }

    public void setCardReader( Reader cardReader ) {
        this.cardReader = cardReader;
    }

    public EPP getPinPad() {
        return ( EPP ) pinPad;
    }

    public void setPinPad( EPP pinPad ) {
        this.pinPad = pinPad;
    }

    public SIU getSiu() {
        return ( SIU ) siu;
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
