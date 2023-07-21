package mx.com.prosa.nabhi.misc.model.devices.constants.cdm.cassette;
//Cambio release/redcat: Cambio de paquete

public enum CassetteType {

    NA,
    REJECTCASSETTE,
    BILLCASSETTE,
    COINCYLINDER,
    COINDISPENSER,
    RETRACTCASSETTE,
    COUPON,
    DOCUMENT,
    REPCONTAINER,
    RECYCLING;

    public static CassetteType valueOfCompose(String name ) {
        try {
            return CassetteType.valueOf( name );
        } catch ( IllegalArgumentException e ) {
            return NA;
        }
    }
}
