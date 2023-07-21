package mx.com.prosa.nabhi.misc.model.devices.constants.ptr;
//Cambio release/redcat: Cambio de paquete


public enum PTRDeviceState {

    /*
     * @since v3.00
     */
    ONLINE(),
    /*
     * @since v3.00
     */
    OFFLINE(),
    /*
     * @since v3.00
     */
    POWEROFF(),
    /*
     * @since v3.00
     */
    NODEVICE(),
    /*
     * @since v3.00
     */
    HWERROR(),
    /*
     * @since v3.00
     */
    USERERROR(),
    /*
     * @since v3.00
     */
    BUSY(),
    /*
     * @since v3.10
     */
    FRAUDATTEMPT(),
    /*
     * @since v3.20
     */
    POTENTIALFRAUD();


    PTRDeviceState() {
    }

    public static PTRDeviceState valueOfCompose(String name ) {
        try {
            return PTRDeviceState.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return HWERROR;
        }
    }

}
