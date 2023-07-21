package us.gonet.nabhi.misc.model.devices.cdm;

import com.google.gson.GsonBuilder;
import us.gonet.nabhi.misc.model.devices.constants.cdm.cassette.CassetteStatus;
import us.gonet.nabhi.misc.model.devices.constants.cdm.cassette.CassetteType;

import java.io.Serializable;

public class Cassette implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    private int cassetteIndex;
    private String currency;
    private int denomination;
    private int initialCount;
    private int decrement;
    private int current;
    private int dispensed;
    private int deposited;
    private int rejected;
    private int retracted;
    private int presented;
    private CassetteStatus status;
    private CassetteType type;

    public Cassette() {
    }

    public Cassette( int def ) {
        this.cassetteIndex = def;
        this.currency = "MXN";
        this.denomination = def;
        this.initialCount = def;
        this.decrement = def;
        this.current = def;
        this.dispensed = def;
        this.deposited = def;
        this.rejected = def;
        this.retracted = def;
        this.status = CassetteStatus.MISSING;
        this.type = CassetteType.NA;
    }

    public int getCassetteIndex() {
        return cassetteIndex;
    }

    public void setCassetteIndex( int cassetteIndex ) {
        this.cassetteIndex = cassetteIndex;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency( String currency ) {
        this.currency = currency;
    }

    public int getDenomination() {
        return denomination;
    }

    public void setDenomination( int denomination ) {
        this.denomination = denomination;
    }

    public int getInitialCount() {
        return initialCount;
    }

    public void setInitialCount( int initialCount ) {
        this.initialCount = initialCount;
    }

    public int getDecrement() {
        return decrement;
    }

    public void setDecrement( int decrement ) {
        this.decrement = decrement;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent( int current ) {
        this.current = current;
    }

    public int getDispensed() {
        return dispensed;
    }

    public void setDispensed( int dispensed ) {
        this.dispensed = dispensed;
    }

    public int getDeposited() {
        return deposited;
    }

    public void setDeposited( int deposited ) {
        this.deposited = deposited;
    }

    public int getRejected() {
        return rejected;
    }

    public void setRejected( int rejected ) {
        this.rejected = rejected;
    }

    public int getRetracted() {
        return retracted;
    }

    public void setRetracted( int retracted ) {
        this.retracted = retracted;
    }

    public String getStatus() {
        return status.name();
    }

    public void setStatus( String status ) {
        this.status = CassetteStatus.valueOfCompose( status );
    }

    public String getType() {
        return type.name();
    }

    public void setType( String type ) {
        this.type = CassetteType.valueOfCompose( type );
    }

    public int getPresented() {
        return presented;
    }

    public void setPresented( int presented ) {
        this.presented = presented;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
