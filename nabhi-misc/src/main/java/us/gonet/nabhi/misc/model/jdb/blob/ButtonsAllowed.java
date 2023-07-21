package us.gonet.nabhi.misc.model.jdb.blob;

import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.List;

public class ButtonsAllowed implements Serializable {

    private static final long serialVersionUID = 23453246534L;
    private List < ButtonMapping > buttons;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List < ButtonMapping > getButtons() {
        return buttons;
    }

    public void setButtons( List < ButtonMapping > buttons ) {
        this.buttons = buttons;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
