package mx.com.prosa.nabhi.jse.core.database.memory.bin;

import mx.com.prosa.nabhi.misc.model.jdb.Prefix;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class BinarySearch {

    private BinTable table;

    public void buildTable( List < Prefix > prefixes ) {
        table = new BinTable();
        for ( Prefix b : prefixes ) {
            PanLenWrapper panLen = table.getForPanLen( b.getPanLen() );
            BinLenWrapper binLen = panLen.getForBinLen( b.getBinLen() );
            binLen.addBins( b.getBin(), b.getFiid() );
        }
    }

    public String search( int panLen, int binLen, String target ) {
        if ( table.isEmpty() ) {
            return "PROS";
        }
        if ( target.length() > 11 ) {
            target = target.substring( 0, 11 );
        }
        List < BinWrapper > bins;
        PanLenWrapper pWrapper = table.getForPanLen( panLen );
        String tmp = target;
        int index;
        for ( int i = binLen; i > 0; i-- ) {
            BinLenWrapper bWrapper = pWrapper.getForBinLen( i );
            bins = bWrapper.getBins();
            Collections.sort( bins );
            index = Collections.binarySearch( bins, new BinWrapper( tmp ) );
            if ( index < 0 ) {
                tmp = tmp.substring( 0, i - 1 );
            } else {
                return bins.get( index ).getFiid();
            }
        }
        return "PROS";
    }

    public BinTable getTable() {
        return table;
    }

    public void setTable( BinTable table ) {
        this.table = table;
    }
}
