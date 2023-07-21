package mx.com.prosa.nabhi.misc.model.devices.constants.cdm;
//Cambio release/redcat: Cambio de paquete

import mx.com.prosa.nabhi.misc.model.devices.constants.AtmEvent;

public enum CDMEvent implements AtmEvent {
    //0 : Guardar en Journal
    //1 : Solo en device status
    //2 : Notificaciones
    //3 : Especial

    CASH_UNIT_OK( 300, "Dispensación correcta", "0001" ),


    UNSUPP( 999, "Evento no conocido", "0000" ),
    /*
     * @since v3.00
     */
    SRVE_SAFEDOOROPEN( 301, "Puerta de la caja fuerte abierta", "0010" ),
    /*
     * @since v3.00
     */
    SRVE_SAFEDOORCLOSED( 302, "Puerta de la caja fuerte cerrada", "0010" ),
    /*
     * @since v3.00
     */
    USRE_CASHUNITTHRESHOLD( 303, "Límite de billetes máximos o mínimos alcanzados", "0110" ),
    /*
     * @since v3.00
     */
    SRVE_CASHUNITINFOCHANGED( 304, "Cambio el estado de las caseteras", "0011" ),
    /*
     * @since v3.00
     */
    SRVE_TELLERINFOCHANGED( 305, "Cambio en la información de dispensador", "0011" ),
    /*
     * @since v3.00
     */
    EXEE_DELAYEDDISPENSE( 306, "Retardo en la dispensación ", "0010" ),
    /*
     * @since v3.00
     */
    EXEE_STARTDISPENSE( 307, "Se comenzó a dispensar ", "0010" ),
    /*
     * @since v3.00
     */
    EXEE_CASHUNITERROR( 308, "Error en alguna casetera ", "0010" ),
    /*
     * @since v3.00
     */
    SRVE_ITEMSTAKEN( 309, "El usuario tomo el dinero ", "0010" ),
    /*
     * @since v3.00
     */
    EXEE_PARTIALDISPENSE( 310, "Se dispensó solo una parte ", "0010" ),
    /*
     * @since v3.00
     */
    EXEE_SUBDISPENSEOK( 311, " ", "0010" ),
    /*
     * @since v3.00
     */
    SRVE_ITEMSPRESENTED( 313, "Dinero presentado al usuario ", "0010" ),
    /*
     * @since v3.00
     */
    SRVE_COUNTS_CHANGED( 314, "Cambio en los contadores ", "0010" ),
    /*
     * @since v3.00
     */
    EXEE_INCOMPLETEDISPENSE( 315, "No se dispenso el monto total ", "0010" ),
    /*
     * @since v3.00
     */
    EXEE_NOTEERROR( 316, " ", "0010" ),
    /*
     * @since v3.00
     */
    EXEE_MEDIADETECTED( 317, "Se depositó un billete ", "0010" ),
    /*
     * @since v3.00
     */
    SRVE_MEDIADETECTED( 317, "Se depositó un billete ", "0010" ),
    /*
     * @since v3.10
     */
    EXEE_INPUT_P6( 318, " ", "0010" ),
    /*
     * @since v3.10
     */
    SRVE_DEVICEPOSITION( 319, " ", "0010" ),
    /*
     * @since v3.10
     */
    SRVE_POWER_SAVE_CHANGE( 320, " ", "0010" ),
    /*
     * @since v3.30
     */
    EXEE_INFO_AVAILABLE( 321, " ", "0010" ),
    /*
     * @since v3.30
     */
    EXEE_INCOMPLETERETRACT( 322, "No se pudo retraer todos los billetes ", "0010" ),
    /*
     * @since v3.30
     */
    SRVE_SHUTTERSTATUSCHANGED( 323, "Cambio el estado del shutter ", "0010" );


    CDMEvent( int code, String journalMessage, String actions ) {
        this.code = code;
        this.journalMessage = journalMessage;
        this.actions = actions;
    }

    private final int code;
    private final String journalMessage;

    @Override
    public String getActions() {
        return actions;
    }

    private final String actions;

    public int getCode() {
        return code;
    }

    @Override
    public String getJournalMessage() {
        return journalMessage;
    }


    public static CDMEvent getEventFromCode(int code ) {
        for ( CDMEvent e : values() ) {
            if ( e.getCode() == code ) {
                return e;
            }
        }
        return UNSUPP;
    }
}
