package mx.com.prosa.nabhi.misc.model.devices.constants.pin;
//Cambio release/redcat: Cambio de paquete

public enum AntiFraudModule {

    /**
     * No anti-fraud module is available.
     *
     * @since 3.20
     */
    WFS_PIN_AFMNOTSUPP( 0L ),
    /**
     * Anti-fraud module is in a good state and no foreign device is detected.
     *
     * @since 3.20
     */
    WFS_PIN_AFMOK( 1L ),
    /**
     * Anti-fraud module is inoperable.
     *
     * @since 3.20
     */
    WFS_PIN_AFMINOP( 2L ),
    /**
     * Anti-fraud module detected the presence of a foreign device.
     *
     * @since 3.20
     */
    WFS_PIN_AFMDEVICEDETECTED( 3L ),
    /**
     * The state of the anti-fraud module cannot be determined.
     *
     * @since 3.20
     */
    WFS_PIN_AFMUNKNOWN( 4L );

    private final long value;

    AntiFraudModule( final long value ) {
        this.value = value;
    }


    public long getValue() {
        return value;
    }

    public static AntiFraudModule valueOfCompose(String name ) {
        try {
            return AntiFraudModule.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return WFS_PIN_AFMNOTSUPP;
        }
    }
}
