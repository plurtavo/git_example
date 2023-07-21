package us.gonet.nabhi.misc.model.devices.constants.idc;


public enum RetainBin {

    BINOK( 1L ),
    NOTSUPP( 2L ),
    BINFULL( 3L ),
    BINHIGH( 4L ),
    BINMISSING( 5L ),
    NA( 6L );

    private final long value;

    RetainBin( final long value ) {
        this.value = value;
    }

    public static RetainBin valueOfCompose( String name ) {
        try {
            return RetainBin.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return NA;
        }
    }

}
