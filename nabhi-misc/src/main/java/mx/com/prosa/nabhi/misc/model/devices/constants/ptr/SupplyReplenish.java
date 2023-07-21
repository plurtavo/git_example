package mx.com.prosa.nabhi.misc.model.devices.constants.ptr;
//Cambio release/redcat: Cambio de paquete


public enum SupplyReplenish {

    /*
     * @since v3.10
     */
    PAPERUPPER( 0x0001 ),
    /*
     * @since v3.10
     */
    PAPERLOWER( 0x0002 ),
    /*
     * @since v3.10
     */
    PAPERAUX( 0x0004 ),
    /*
     * @since v3.10
     */
    PAPERAUX2( 0x0008 ),
    /*
     * @since v3.10
     */
    TONER( 0x0010 ),
    /*
     * @since v3.10
     */
    INK( 0x0020 ),
    /*
     * @since v3.10
     */
    LAMP( 0x0040 ),
    NA( 0L );

    private final long value;

    SupplyReplenish( final long value ) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static SupplyReplenish valueOfCompose(String name ) {
        try {
            return SupplyReplenish.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return NA;
        }
    }
}
