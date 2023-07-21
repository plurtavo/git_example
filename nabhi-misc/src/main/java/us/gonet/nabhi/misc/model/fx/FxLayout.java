package us.gonet.nabhi.misc.model.fx;

import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.List;

public class FxLayout implements Serializable, IFx {

    private static final long serialVersionUID = 23453246534L;

    private List < FxButton > buttons;
    private List < FxNode > nodes;
    private FxOperation operation;

    public FxLayout() {
    }

    public FxLayout( List < FxButton > buttons, List < FxNode > nodes, FxOperation operation ) {
        this.buttons = buttons;
        this.nodes = nodes;
        this.operation = operation;
    }

    public List < FxButton > getButtons() {
        return buttons;
    }

    public void setButtons( List < FxButton > buttons ) {
        this.buttons = buttons;
    }

    public List < FxNode > getNodes() {
        return nodes;
    }

    public void setNodes( List < FxNode > nodes ) {
        this.nodes = nodes;
    }

    public FxOperation getOperation() {
        return operation;
    }

    public void setOperation( FxOperation operation ) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
