package mx.com.prosa.nabhi.misc.model.devices.constants.pin;
//Cambio release/redcat: Cambio de paquete


public enum DevicePosition {

    /**
     * The device is in its normal operating position, or is fixed in place and
     * cannot be moved.
     *
     * @since 3.10
     */
    WFS_PIN_DEVICEINPOSITION( 0L ),
    /**
     * The device has been removed from its normal operating position.
     *
     * @since 3.10
     */
    WFS_PIN_DEVICENOTINPOSITION( 1L ),
    /**
     * Due to a xfs error or other condition, the position of the device
     * cannot be determined.
     *
     * @since 3.10
     */
    WFS_PIN_DEVICEPOSUNKNOWN( 2L ),
    /**
     * The physical device does not have the capability of detecting the
     * position.
     *
     * @since 3.10
     */
    WFS_PIN_DEVICEPOSNOTSUPP( 3L ),
    NA( 0L );

    private final long value;

    DevicePosition( final long value ) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static DevicePosition valueOfCompose(String name ) {
        try {
            return DevicePosition.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return NA;
        }
    }
}
