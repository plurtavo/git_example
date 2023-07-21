package mx.com.prosa.nabhi.misc.model.devices.constants.idc;
//Cambio release/redcat: Cambio de paquete


public enum ChipModule {

    OK( 1L ),
    INOP( 2L ),
    UNKNOWN( 3L ),
    NOTSUPP( 4L );

    private final long value;

    ChipModule( final long value ) {
        this.value = value;
    }

    public static ChipModule valueOfCompose(String name ) {
        try {
            return ChipModule.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return NOTSUPP;
        }
    }
}
