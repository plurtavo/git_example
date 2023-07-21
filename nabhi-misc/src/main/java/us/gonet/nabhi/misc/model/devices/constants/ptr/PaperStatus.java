package us.gonet.nabhi.misc.model.devices.constants.ptr;

public enum PaperStatus {

    FULL,
    LOW,
    OUT,
    NOTSUPP,
    UNKNOWN,
    JAMMED;

    public static PaperStatus valueOfCompose( String name ) {
        try {
            return PaperStatus.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return UNKNOWN;
        }
    }
}
