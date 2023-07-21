package mx.com.prosa.nabhi.misc.model.devices.constants.pin;
//Cambio release/redcat: Cambio de paquete


public enum EncStat {

    /**
     * The encryption module is initialized and ready (at least one key is
     * imported into the encryption module).
     */
    WFS_PIN_ENCREADY( 0L ),
    /**
     * The encryption module is not ready.
     */
    WFS_PIN_ENCNOTREADY( 1L ),
    /**
     * The encryption module is not initialized (no master key loaded).
     */
    WFS_PIN_ENCNOTINITIALIZED( 2L ),
    /**
     * The encryption module is busy (implies that the device is busy).
     */
    WFS_PIN_ENCBUSY( 3L ),
    /**
     * The encryption module state is undefined.
     */
    WFS_PIN_ENCUNDEFINED( 4L ),
    /**
     * The encryption module is initialized and master key (where required) and
     * any other initial keys are loaded; ready to import other keys.
     */
    WFS_PIN_ENCINITIALIZED( 5L ),
    /**
     * @since 3.10
     */
    WFS_PIN_ENCPINTAMPERED( 6L ),

    NA( 7L );


    private final long value;

    EncStat( final long value ) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static EncStat valueOfCompose(String name ) {
        try {
            return EncStat.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return NA;
        }
    }
}
