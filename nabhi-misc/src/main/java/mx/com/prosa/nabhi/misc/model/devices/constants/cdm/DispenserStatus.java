package mx.com.prosa.nabhi.misc.model.devices.constants.cdm;
//Cambio release/redcat: Cambio de paquete


public enum DispenserStatus {

    /*
     * @since v3.00
     */
    OK( 0L ),
    /*
     * @since v3.00
     */
    CUSTATE( 1L ),
    /*
     * @since v3.00
     */
    CUSTOP( 2L ),
    /*
     * @since v3.00
     */
    CUUNKNOWN( 3L ),
    NA( 0L );

    private final long value;

    DispenserStatus( final long value ) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static DispenserStatus valueOfCompose(String name ) {
        try {
            return DispenserStatus.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return NA;
        }
    }
}
