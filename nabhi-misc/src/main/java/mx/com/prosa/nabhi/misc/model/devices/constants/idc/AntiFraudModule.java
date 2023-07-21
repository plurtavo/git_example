package mx.com.prosa.nabhi.misc.model.devices.constants.idc;
//Cambio release/redcat: Cambio de paquete


public enum AntiFraudModule {

    NOTSUPP( 0L ),
    OK( 1L ),
    INOP( 2L ),
    DEVICEDETECTED( 3L ),
    UNKNOWN( 4L );

    private final long value;

    AntiFraudModule( final long value ) {
        this.value = value;
    }

    public static AntiFraudModule valueOfCompose(String name ) {
        try {
            return AntiFraudModule.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return UNKNOWN;
        }
    }

}
