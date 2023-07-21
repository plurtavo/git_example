package mx.com.prosa.nabhi.misc.model.receipt;

import com.google.gson.GsonBuilder;

import java.io.Serializable;

public class Entry implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    private String text;
    private ReceiptVariable variable;
    private ReceiptMargin marginText;
    private ReceiptMargin marginVariable;

    public String getText() {
        return text;
    }

    public void setText( String text ) {
        this.text = text;
    }

    public ReceiptVariable getVariable() {
        return variable;
    }

    public void setVariable( ReceiptVariable variable ) {
        this.variable = variable;
    }

    public ReceiptMargin getMarginText() {
        return marginText;
    }

    public void setMarginText( ReceiptMargin marginText ) {
        this.marginText = marginText;
    }

    public ReceiptMargin getMarginVariable() {
        return marginVariable;
    }

    public void setMarginVariable( ReceiptMargin marginVariable ) {
        this.marginVariable = marginVariable;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
