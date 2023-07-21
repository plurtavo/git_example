package us.gonet.nabhi.misc.model.devices.constants.cdm;


public enum Shutter {

    /*
     * @since v3.00
     */
    CLOSED( 0L ),
    /*
     * @since v3.00
     */
    OPEN( 1L ),
    /*
     * @since v3.00
     */
    JAMMED( 2L ),
    /*
     * @since v3.00
     */
    UNKNOWN( 3L ),
    /*
     * @since v3.00
     */
    NOTSUPPORTED( 4L );

    private final long value;

    Shutter( final long value ) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static Shutter valueOfCompose( String name ) {
        try {
            return Shutter.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return NOTSUPPORTED;
        }
    }

}
