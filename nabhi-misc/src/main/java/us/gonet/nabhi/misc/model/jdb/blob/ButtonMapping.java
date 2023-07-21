package us.gonet.nabhi.misc.model.jdb.blob;

import com.google.gson.GsonBuilder;
import us.gonet.nabhi.misc.model.fx.FxLayout;

import java.io.Serializable;

public class ButtonMapping implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    private int id;
    private String bitmap;
    private String screenComponent;
    private FxLayout layout;

    public ButtonMapping() {
    }

    public ButtonMapping( int id, String bitmap, String screenComponent ) {
        this.id = id;
        this.bitmap = bitmap;
        this.screenComponent = screenComponent;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getBitmap() {
        return bitmap;
    }

    public void setBitmap( String bitmap ) {
        this.bitmap = bitmap;
    }

    public String getScreenComponent() {
        return screenComponent;
    }

    public void setScreenComponent( String screenComponent ) {
        this.screenComponent = screenComponent;
    }

    public FxLayout getLayout() {
        return layout;
    }

    public void setLayout( FxLayout layout ) {
        this.layout = layout;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
