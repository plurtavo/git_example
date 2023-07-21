package us.gonet.nabhi.misc.model.devices.constants.siu;

public enum OperatorMode {

    /**
     *
     */
    RUN( 0x0001L ),
    /**
     *
     */
    MAINTENANCE( 0x0002L ),
    /**
     *
     */
    SUPERVISOR( 0x0004L );

    private final long value;

    OperatorMode( final long value ) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static OperatorMode valueOfCompose( String name ) {
        try {
            return OperatorMode.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return MAINTENANCE;
        }
    }
}
