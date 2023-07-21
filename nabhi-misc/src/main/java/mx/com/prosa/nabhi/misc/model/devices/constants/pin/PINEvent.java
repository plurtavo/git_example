package mx.com.prosa.nabhi.misc.model.devices.constants.pin;
//Cambio release/redcat: Cambio de paquete


import mx.com.prosa.nabhi.misc.model.devices.constants.AtmEvent;

public enum PINEvent implements AtmEvent {

    //0 : Guardar en Journal
    //1 : Solo en device status
    //2 : Notificaciones
    //3 : Especial
    // ## Nada

    UNSUPP( 999, "Respuesta desconocida", "0000" ),
    SUCCESS( 400, "Comando exitoso", "0000" ),
    /**
     * @since 3.0
     */
    WFS_EXEE_PIN_KEY( 401, "Tecla presionada ", "0010" ),
    /**
     * @since 3.0
     */
    WFS_SRVE_PIN_INITIALIZED( 402, "Pinpad inicializado", "0111" ),
    /**
     * @since 3.0
     */
    WFS_SRVE_PIN_ILLEGAL_KEY_ACCESS( 403, "Error al acceder a las llaves de encripción ", "0010" ),
    WFS_SRVE_PIN_OPT_REQUIRED( 404, " ", "0010" ),
    WFS_SRVE_PIN_HSM_TDATA_CHANGED( 405, " ", "0010" ),
    WFS_SRVE_PIN_CERTIFICATE_CHANGE( 406, " ", "0010" ),
    WFS_SRVE_PIN_HSM_CHANGED( 407, " ", "0010" ),
    /**
     * @since 3.10
     */
    WFS_EXEE_PIN_ENTERDATA( 408, "Listo para presionar teclas", "0010" ),
    WFS_SRVE_PIN_DEVICEPOSITION( 409, " ", "0010" ),
    WFS_SRVE_PIN_POWER_SAVE_CHANGE( 410, " ", "0010" );

    private final int code;
    private final String journalMessage;
    private final String actions;

    PINEvent( int code, String journalMessage, String actions ) {
        this.code = code;
        this.journalMessage = journalMessage;
        this.actions = actions;

    }

    public static PINEvent getEventFromCode(int code ) {
        for ( PINEvent e : values() ) {
            if ( e.getCode() == code ) {
                return e;
            }
        }
        return UNSUPP;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getActions() {
        return actions;
    }

    @Override
    public String getJournalMessage() {
        return journalMessage;
    }
}
