package us.gonet.nabhi.misc.model.fx;

import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.List;

public class FxNode implements IFx, Serializable {

    private static final long serialVersionUID = 23453246534L;

    private String component;
    private List < String > data;

    public FxNode() {
    }

    public FxNode( String component, List < String > data ) {
        this.component = component;
        this.data = data;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent( String component ) {
        this.component = component;
    }

    public List < String > getData() {
        return data;
    }

    public void setData( List < String > data ) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
