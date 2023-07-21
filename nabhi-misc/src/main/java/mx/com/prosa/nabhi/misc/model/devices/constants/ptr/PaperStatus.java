package mx.com.prosa.nabhi.misc.model.devices.constants.ptr;
//Cambio release/redcat: Cambio de paquete

public enum PaperStatus {

    FULL,
    LOW,
    OUT,
    NOTSUPP,
    UNKNOWN,
    JAMMED;

    public static PaperStatus valueOfCompose(String name ) {
        try {
            return PaperStatus.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return UNKNOWN;
        }
    }
}
