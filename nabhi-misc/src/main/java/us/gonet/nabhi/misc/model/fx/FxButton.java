package us.gonet.nabhi.misc.model.fx;

import com.google.gson.GsonBuilder;

import java.io.Serializable;

public class FxButton implements Serializable, IFx {

    private static final long serialVersionUID = 23453246534L;

    private int buttonId;
    private int referenced;
    private boolean active;
    private String text;
    private String data;

    public FxButton() {
    }

    public FxButton( int buttonId, int referenced, boolean active, String text, String data ) {
        this.buttonId = buttonId;
        this.referenced = referenced;
        this.active = active;
        this.text = text;
        this.data = data;
    }

    public int getButtonId() {
        return buttonId;
    }

    public void setButtonId( int buttonId ) {
        this.buttonId = buttonId;
    }

    public int getReferenced() {
        return referenced;
    }

    public void setReferenced( int referenced ) {
        this.referenced = referenced;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive( boolean active ) {
        this.active = active;
    }

    public String getText() {
        return text;
    }

    public void setText( String text ) {
        this.text = text;
    }

    public String getData() {
        return data;
    }

    public void setData( String data ) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
