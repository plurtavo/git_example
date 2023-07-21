package mx.com.prosa.nabhi.misc.model.devices.constants.cdm.cassette;
//Cambio release/redcat: Cambio de paquete

public enum CassetteStatus {

    OK,
    FULL,
    HIGH,
    LOW,
    EMPTY,
    INOP,
    MISSING,
    NOVAL,
    NOREF,
    MANIP,
    INVALID;

    public static CassetteStatus valueOfCompose(String name ) {
        try {
            return CassetteStatus.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return INVALID;
        }
    }
}
