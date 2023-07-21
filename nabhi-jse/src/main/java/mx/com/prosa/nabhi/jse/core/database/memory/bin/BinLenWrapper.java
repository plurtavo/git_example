package mx.com.prosa.nabhi.jse.core.database.memory.bin;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class BinLenWrapper {

    private int binLen;
    private List < BinWrapper > bins = new ArrayList <>();

    BinLenWrapper( int binLen ) {
        this.binLen = binLen;
    }

    int getBinLen() {
        return binLen;
    }

    public void setBinLen( int binLen ) {
        this.binLen = binLen;
    }

    List < BinWrapper > getBins() {
        return bins;
    }

    public void setBins( List < BinWrapper > bins ) {
        this.bins = bins;
    }

    void addBins( String bin, String fiid ) {
        bins.add( new BinWrapper( bin, fiid ) );
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
