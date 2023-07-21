package mx.com.prosa.nabhi.misc.model.devices.constants.pin;
//Cambio release/redcat: Cambio de paquete


public enum DeviceState {

    ONLINE(),
    OFFLINE(),
    POWEROFF(),
    BUSY(),
    NODEVICE(),
    HWERROR(),
    USERERROR(),
    FRAUDATTEMPT(),
    POTENTIALFRAUD(),
    NA;

    DeviceState() {
    }

    public static DeviceState valueOfCompose(String name ) {
        try {
            return DeviceState.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return NA;
        }
    }

}
