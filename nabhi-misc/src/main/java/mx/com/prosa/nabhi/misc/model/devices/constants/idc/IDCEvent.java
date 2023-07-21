package mx.com.prosa.nabhi.misc.model.devices.constants.idc;
//Cambio release/redcat: Cambio de paquete

import mx.com.prosa.nabhi.misc.model.devices.constants.AtmEvent;

public enum IDCEvent implements AtmEvent {

    //0 : Guardar en Journal
    //1 : Solo en device status
    //2 : Notificaciones
    //3 : Especial
    // ## Nada


    UNSUPP( 999, "Evento no conocido", "0000" ),
    /*
     * @since v3.00
     */
    EXEE_INVALIDTRACKDATA( 201, "Track invalido ", "0010" ),
    /*
     * @since v3.00
     */
    EXEE_MEDIAINSERTED( 203, "Tarjeta introducida ", "1010" ),
    /*
     * @since v3.00
     */
    SRVE_MEDIAREMOVED( 204, "Tarjeta retirada ", "1010" ),
    /*
     * @since v3.00
     */
    SRVE_CARDACTION( 205, "Card action ", "0000" ),
    /*
     * @since v3.00
     */
    USRE_RETAINBINTHRESHOLD( 206, "Se alcanzó el limite de tarjeta que se pueden retener ", "1111" ),
    /*
     * @since v3.00
     */
    EXEE_INVALIDMEDIA( 207, "Tarjeta invalida ", "1010" ),
    /*
     * @since v3.00
     */
    EXEE_MEDIARETAINED( 208, "Tarjeta retenida ", "1011" ),
    /*
     * @since v3.00
     */
    SRVE_MEDIADETECTED( 209, "Tarjeta detectada ", "0000" ),
    /*
     * @since v3.10
     */
    SRVE_RETAINBININSERTED( 210, " ", "0000" ),
    /*
     * @since v3.10
     */
    SRVE_RETAINBINREMOVED( 211, " ", "0000" ),
    /*
     * @since v3.10
     */
    EXEE_INSERTCARD( 212, "Listo para insertar tarjeta ", "1010" ),
    /*
     * @since v3.10
     */
    SRVE_DEVICEPOSITION( 213, " ", "0000" ),
    /*
     * @since v3.10
     */
    SRVE_POWER_SAVE_CHANGE( 214, " ", "0010" ),
    /*
     * @since v3.20
     */
    EXEE_TRACKDETECTED( 215, "Track detectado ", "0010" ),
    /*
     * @since v3.30
     */
    EXEE_EMVCLESSREADSTATUS( 216, " ", "0010" );


    IDCEvent( int code, String journalMessage, String actions ) {
        this.code = code;
        this.journalMessage = journalMessage;
        this.actions = actions;
    }

    private final int code;
    private final String journalMessage;
    private final String actions;


    public int getCode() {
        return code;
    }

    public static IDCEvent getEventFromCode(int code ) {
        for ( IDCEvent e : values() ) {
            if ( e.getCode() == code ) {
                return e;
            }
        }
        return UNSUPP;
    }

    @Override
    public String getJournalMessage() {
        return journalMessage;
    }

    @Override
    public String getActions() {
        return actions;
    }
}
