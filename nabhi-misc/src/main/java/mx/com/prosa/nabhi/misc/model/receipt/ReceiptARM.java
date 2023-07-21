package mx.com.prosa.nabhi.misc.model.receipt;

import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.misc.model.iso.ATMRequestModel;

public class ReceiptARM {

    private ReceiptScript script;
    private ATMRequestModel arm;
    private String isoMessage;

    public ReceiptScript getScript() {
        return script;
    }

    public void setScript( ReceiptScript script ) {
        this.script = script;
    }

    public ATMRequestModel getArm() {
        return arm;
    }

    public void setArm( ATMRequestModel arm ) {
        this.arm = arm;
    }

    public String getIsoMessage() {
        return isoMessage;
    }

    public void setIsoMessage( String isoMessage ) {
        this.isoMessage = isoMessage;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
