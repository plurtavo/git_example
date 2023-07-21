package us.gonet.nabhi.misc.model.devices.constants.ptr;


import us.gonet.nabhi.misc.model.devices.constants.AtmEvent;

public enum PTREvent implements AtmEvent {

    //0 : Guardar en Journal
    //1 : Solo en device status
    //2 : Notificaciones
    //3 : Especial

    UNSUPP( 999, "Respuesta desconocida", "0000" ),
    SUCCESS( 100, "Comando exitoso", "0000" ),
    /*
     * @since v3.00
     */
    EXEE_PTR_NOMEDIA( 101, "No hay papel", "0011" ),
    /*
     * @since v3.00
     */
    EXEE_PTR_MEDIAINSERTED( 102, "Se insertó un nuevo medio  ", "0010" ),
    /*
     * @since v3.00
     */
    EXEE_PTR_FIELDERROR( 103, "Ocurrio un error al dar formato al ticket", "0010" ),
    /*
     * @since v3.00
     */
    EXEE_PTR_FIELDWARNING( 104, " ", "0000" ),
    /*
     * @since v3.00
     */
    USRE_PTR_RETRACTBINTHRESHOLD( 105, "Se alcanzo del limite de tickets retraidos ", "0110" ),
    /*
     * @since v3.00
     */
    SRVE_PTR_MEDIATAKEN( 106, "El ticket fue tomado por el usuario ", "0010" ),
    /*
     * @since v3.00
     */
    USRE_PTR_PAPERTHRESHOLD( 107, "Se termino el papel ", "0110" ),
    /*
     * @since v3.00
     */
    USRE_PTR_TONERTHRESHOLD( 108, "Se termino la tinta", "0110" ),
    /*
     * @since v3.00
     */
    SRVE_PTR_MEDIAINSERTED( 109, "Se insertó un nuevo medio", "0010" ),
    /*
     * @since v3.00
     */
    USRE_PTR_LAMPTHRESHOLD( 110, " ", "0010" ),
    /*
     * @since v3.00
     */
    USRE_PTR_INKTHRESHOLD( 111, " ", "0110" ),
    /*
     * @since v3.00
     */
    SRVE_PTR_MEDIADETECTED( 112, " ", "0000" ),
    /*
     * @since v3.10
     */
    SRVE_PTR_RETRACTBINSTATUS( 113, " ", "0000" ),
    /*
     * @since v3.10
     */
    EXEE_PTR_MEDIAPRESENTED( 114, "Ticket presentado al usuario ", "0010" ),
    /*
     * @since v3.10
     */
    SRVE_PTR_DEFINITIONLOADED( 115, " ", "0000" ),
    /*
     * @since v3.10
     */
    EXEE_PTR_MEDIAREJECTED( 116, " ", "0010" ),
    /*
     * @since v3.10
     */
    SRVE_PTR_MEDIAPRESENTED( 117, "Se presento el ticket al usuario ", "0010" ),
    /*
     * @since v3.10
     */
    SRVE_PTR_MEDIAAUTORETRACTED( 118, "Se retrajo el ticket", "0000" ),
    /*
     * @since v3.10
     */
    SRVE_PTR_DEVICEPOSITION( 119, " ", "0000" ),
    /*
     * @since v3.10
     */
    SRVE_PTR_POWER_SAVE_CHANGE( 120, " ", "0000" );

    private final int code;
    private final String journalMessage;
    private final String actions;

    PTREvent( int code, String journalMessage, String actions ) {
        this.code = code;
        this.journalMessage = journalMessage;
        this.actions = actions;

    }

    public static PTREvent getEventFromCode( int code ) {
        for ( PTREvent e : values() ) {
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
