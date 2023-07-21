package mx.com.prosa.nabhi.misc.constants;

public enum EntryMode {

    UNKNOWN( "00" ),
    MANUAL( "01" ),
    BARCODE( "03" ),
    OCR( "04" ),
    CHIP( "05" ),
    CHIP_ERROR( "80" ),
    MAGNETIC_STRIPE( "90" )
    ;

    private String value;

    EntryMode( String value ) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
