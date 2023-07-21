package us.gonet.nabhi.misc.model.devices.constants.siu;


public enum DeviceState {

    ONLINE(),
    OFFLINE(),
    POWEROFF(),
    NODEVICE(),
    HWERROR(),
    USERERROR(),
    BUSY(),
    FRAUDATTEMPT(),
    POTENTIALFRAUD(),
    NA();


    DeviceState() {
    }

    public static DeviceState valueOfCompose( String name ) {
        try {
            return DeviceState.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return NA;
        }
    }

}
