package mx.com.prosa.nabhi.misc.model.devices.constants.siu;
//Cambio release/redcat: Cambio de paquete


public enum SIUError {

    /**
     *
     */
    INVALID_PORT( -801L ),
    /**
     *
     */
    SYNTAX( -802L ),
    /**
     *
     */
    PORT_ERROR( -803L ),
    /**
     *
     */
    POWERSAVETOOSHORT( -804L ),
    NA( 0L );

    private final long value;

    SIUError( final long value ) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static SIUError valueOfCompose(String name ) {
        try {
            return SIUError.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return NA;
        }
    }
}
