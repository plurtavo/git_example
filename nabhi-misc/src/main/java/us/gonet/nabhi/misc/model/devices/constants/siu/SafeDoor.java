package us.gonet.nabhi.misc.model.devices.constants.siu;


public enum SafeDoor {

    /**
     * The status is not available.
     */
    NOT_AVAILABLE( 0x0000L ),
    /**
     * The Safe Doors are closed.
     */
    CLOSED( 0x0001L ),
    /**
     * At least one of the Safe Doors is open.
     */
    OPEN( 0x0002L ),
    /**
     * The Safe Doors are closed and locked.
     */
    LOCKED( 0x0004L ),
    /**
     * The Safe Doors are closed, locked and bolted.
     */
    BOLTED( 0x0008L );

    private final long value;

    SafeDoor( final long value ) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static SafeDoor valueOfCompose( String name ) {
        try {
            return SafeDoor.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return NOT_AVAILABLE;
        }
    }
}
