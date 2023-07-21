package us.gonet.nabhi.misc.model.devices.constants.idc;


public enum IDCDeviceState {
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

    IDCDeviceState() {
    }

    public static IDCDeviceState valueOfCompose( String name ) {
        try {
            return IDCDeviceState.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return HWERROR;
        }
    }

}
