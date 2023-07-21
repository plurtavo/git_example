package mx.com.prosa.nabhi.jse.core.adp.algorithm.container;

import java.util.Objects;

public class CassetteHealth implements Comparable < CassetteHealth > {

    private int cassetteIndex;
    private int denomination;
    private boolean status;
    private int current;
    private int orderDispensed;
    private Thresholds thresholds;

    public CassetteHealth( int cassetteIndex, int denomination, boolean status, int current ) {
        this.cassetteIndex = cassetteIndex;
        this.denomination = denomination;
        this.status = status;
        this.current = current;
        this.orderDispensed = 0;
    }

    public CassetteHealth() {
    }

    public int getCassetteIndex() {
        return cassetteIndex;
    }

    public void setCassetteIndex( int cassetteIndex ) {
        this.cassetteIndex = cassetteIndex;
    }

    public int getDenomination() {
        return denomination;
    }

    public void setDenomination( int denomination ) {
        this.denomination = denomination;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus( boolean status ) {
        this.status = status;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent( int current ) {
        this.current = current;
    }

    public int getOrderDispensed() {
        return orderDispensed;
    }

    public void incrementOrderDispensed() {
        this.orderDispensed += 1;
    }

    public void setOrderDispensed( int orderDispensed ) {
        this.orderDispensed = orderDispensed;
    }

    public Thresholds getThresholds() {
        return thresholds;
    }

    public void setThresholds( Thresholds thresholds ) {
        this.thresholds = thresholds;
    }

    @Override
    public int compareTo( CassetteHealth o ) {
        return ( this.denomination - o.getDenomination() );
    }

    @Override
    @SuppressWarnings( "EqualsWhichDoesntCheckParameterClass" )
    public boolean equals( Object o ) {
        return Objects.equals( this.denomination, o );
    }

    @Override
    public int hashCode() {
        return Objects.hashCode( this.denomination );
    }
}
