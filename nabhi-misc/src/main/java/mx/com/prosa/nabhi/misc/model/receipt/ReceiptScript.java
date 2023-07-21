package mx.com.prosa.nabhi.misc.model.receipt;

import com.google.gson.GsonBuilder;

import java.util.List;

public class ReceiptScript {

    private String name;
    private int id;
    private String fiid;
    private String tranCode;
    private boolean costumer;
    private List < Entry > header;
    private List < Entry > body;
    private List < Entry > trailer;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode( String tranCode ) {
        this.tranCode = tranCode;
    }

    public boolean isCostumer() {
        return costumer;
    }

    public void setCostumer( boolean costumer ) {
        this.costumer = costumer;
    }

    public List < Entry > getHeader() {
        return header;
    }

    public void setHeader( List < Entry > header ) {
        this.header = header;
    }

    public List < Entry > getBody() {
        return body;
    }

    public void setBody( List < Entry > body ) {
        this.body = body;
    }

    public List < Entry > getTrailer() {
        return trailer;
    }

    public void setTrailer( List < Entry > trailer ) {
        this.trailer = trailer;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
