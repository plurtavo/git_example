package mx.com.prosa.nabhi.misc.model.receipt;

public enum ReceiptMargin {

    LEFT,
    RIGHT,
    CENTER,
    NONE;

    ReceiptMargin() {
    }

    public static ReceiptMargin getValue( String name ) {
        for ( ReceiptMargin variable : ReceiptMargin.values() ) {
            if ( variable.name().equals( name ) ) {
                return variable;
            }
        }
        return LEFT;
    }
}
