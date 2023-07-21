package mx.com.prosa.nabhi.jse.core.adp.algorithm.container;

import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.CatalogError;

import java.util.Arrays;
import java.util.List;

public class CassetteUnitHealth {

    private List < CassetteHealth > cassetteHealths;
    private final String[] buffer;

    public CassetteUnitHealth( List < CassetteHealth > cassetteHealths, int cassettesSize ) {
        this.cassetteHealths = cassetteHealths;
        this.buffer = new String[ cassettesSize  ];
        Arrays.fill( buffer, "00" );
    }

    public List < CassetteHealth > getCassetteHealths() {
        return cassetteHealths;
    }

    public void setCassetteHealths( List < CassetteHealth > cassetteHealths ) {
        this.cassetteHealths = cassetteHealths;
    }

    public boolean globalStatus(){
        for ( CassetteHealth cassetteHealth : cassetteHealths ){
            if ( !cassetteHealth.isStatus() ){
                return false;
            }
        }
        return true;
    }

    public String getBuffer() throws ATMException {
        int numberOfNotes = 0;
        for ( CassetteHealth cassetteHealth: cassetteHealths ){
            numberOfNotes += cassetteHealth.getOrderDispensed();
            if ( cassetteHealth.getOrderDispensed() > 50 ){
                throw new ATMException( CatalogError.LIMIT_NOTES );
            }
            buffer[ cassetteHealth.getCassetteIndex() - 1 ] = String.format( "%02d", cassetteHealth.getOrderDispensed() );
        }
        if ( numberOfNotes > 50 ){
            throw new ATMException( CatalogError.INVALID_AMOUNT_DISP );
        }
        StringBuilder builder = new StringBuilder();
        for ( String str : buffer ){
            builder.append( str );
        }
        return builder.toString();
    }

}
