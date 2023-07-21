package mx.com.prosa.nabhi.misc.model.devices.constants.ptr;
//Cambio release/redcat: Cambio de paquete

public enum TonerStatus {

    FULL,
    LOW,
    OUT,
    NOTSUPP,
    UNKNOWN;

    public static TonerStatus valueOfCompose(String name ) {
        try {
            return TonerStatus.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return UNKNOWN;
        }
    }
}
