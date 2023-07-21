package mx.com.prosa.nabhi.acv.model;

import com.google.gson.GsonBuilder;

public class KeyLoad {

    private String key;
    private int part;

    public String getKey() {
        return key;
    }

    public void setKey( String key ) {
        this.key = key;
    }

    public int getPart() {
        return part;
    }

    public void setPart( int part ) {
        this.part = part;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
