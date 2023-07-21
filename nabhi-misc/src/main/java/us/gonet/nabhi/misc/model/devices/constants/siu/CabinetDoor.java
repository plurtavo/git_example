package us.gonet.nabhi.misc.model.devices.constants.siu;


public enum CabinetDoor {

    /**
     * The status is not available.
     */
    NOT_AVAILABLE( 0x0000L ),
    /**
     * All Cabinet Doors are closed.
     */
    CLOSED( 0x0001L ),
    /**
     * At least one of the Cabinet Doors is open.
     */
    OPEN( 0x0002L ),
    /**
     * All Cabinet Doors are closed and locked.
     */
    LOCKED( 0x0004L ),
    /**
     * All Cabinet Doors are closed, locked and bolted.
     */
    BOLTED( 0x0008L );

    private final long value;

    CabinetDoor( final long value ) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static CabinetDoor valueOfCompose( String name ) {
        try {
            return CabinetDoor.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return NOT_AVAILABLE;
        }
    }
}
