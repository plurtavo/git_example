package mx.com.prosa.nabhi.misc.model.devices.constants.ptr;
//Cambio release/redcat: Cambio de paquete


import mx.com.prosa.nabhi.misc.model.devices.constants.AtmResponse;
import mx.com.prosa.nabhi.misc.model.devices.constants.XFSError;


public enum PTRResponse implements AtmResponse {

    UNSUPP( 999, "Respuesta desconocida", "##" ),
    SUCCESS( 100, "Comando exitoso", "00" ),
    /*
     * @since v3.00
     */
    FORMNOTFOUND( -100, "Formato no encontrado ", "-1" ),
    /*
     * @since v3.00
     */
    FIELDNOTFOUND( -101, "Campo no encontrado ", "-1" ),
    /*
     * @since v3.00
     */
    NOMEDIAPRESENT( -102, "No se pudo presentar o retraer el ticket ", "-1" ),
    /*
     * @since v3.00
     */
    READNOTSUPPORTED( -103, "El dispositivo no tiene la capacidad de lectura ", "-1" ),
    /*
     * @since v3.00
     */
    FLUSHFAIL( -104, "El dispositivo no pudo vaciar los datos ", "-1" ),
    /*
     * @since v3.00
     */
    MEDIAOVERFLOW( -105, "El mensaje de impresion excede los límites del ticket", "-1" ),
    /*
     * @since v3.00
     */
    FIELDSPECFAILURE( -106, "La sintaxis del campo de entrada es invalida ", "-1" ),
    /*
     * @since v3.00
     */
    FIELDERROR( -107, "Se produjo un error al procesar un campo ", "-1" ),
    /*
     * @since v3.00
     */
    MEDIANOTFOUND( -108, "Definición de medios no encontrada", "-1" ),
    /*
     * @since v3.00
     */
    EXTENTNOTSUPPORTED( -109, "El dispositivo no puede reportar la extensión ", "-1" ),
    /*
     * @since v3.00
     */
    MEDIAINVALID( -110, "La definición de medios es invalida ", "-1" ),
    /*
     * @since v3.00
     */
    FORMINVALID( -111, "Formato de impresión invalido ", "-1" ),
    /*
     * @since v3.00
     */
    FIELDINVALID( -112, "El campo especifidado es invalido ", "-1" ),
    /*
     * @since v3.00
     */
    MEDIASKEWED( -113, " ", "-1" ),
    /*
     * @since v3.00
     */
    RETRACTBINFULL( -114, "La bandeja de retracción esta llena ", "-1" ),
    /*
     * @since v3.00
     */
    STACKERFULL( -115, "El apilador interno esta lleno ", "-1" ),
    /*
     * @since v3.00
     */
    PAGETURNFAIL( -116, "El dispositivo no pudo pasar la pagina ", "-1" ),
    /*
     * @since v3.00
     */
    MEDIATURNFAIL( -117, "El dispositivo no pudo girar los medios insertados ", "-1" ),
    /*
     * @since v3.00
     */
    SHUTTERFAIL( -118, "Error al abrir o cerrar el shutter ", "-1" ),
    /*
     * @since v3.00
     */
    MEDIAJAMMED( -119, "El dispositivo esta atascado ", "-2" ),
    /*
     * @since v3.00
     */
    FILE_IO_ERROR( -120, "Archivo no encontrado ", "-1" ),
    /*
     * @since v3.00
     */
    CHARSETDATA( -121, "Caracter no valido por la impresora ", "-1" ),
    /*
     * @since v3.00
     */
    PAPERJAMMED( -122, "El papel se encuentra atascado ", "-2" ),
    /*
     * @since v3.00
     */
    PAPEROUT( -123, "No hay papel ", "-2" ),
    /*
     * @since v3.00
     */
    INKOUT( -124, "No hay tinta ", "-2" ),
    /*
     * @since v3.00
     */
    TONEROUT( -125, "No hay tinta ", "-2" ),
    /*
     * @since v3.00
     */
    LAMPINOP( -126, "La lampara de imagen no funciona ", "-1" ),
    /*
     * @since v3.00
     */
    SOURCEINVALID( -127, "Fuente de papel o tinta no soportada ", "-1" ),
    /*
     * @since v3.00
     */
    SEQUENCEINVALID( -128, "Error de secuencia de comandos ", "-1" ),
    /*
     * @since v3.00
     */
    MEDIASIZE( -129, "Tamaño de papel incorrecto ", "-2" ),
    /*
     * @since v3.10
     */
    INVALID_PORT( -130, "No existe la luz guía ", "-1" ),
    /*
     * @since v3.10
     */
    MEDIARETAINED( -131, "Ticket retraido cuando se intentó expulsar", "-1" ),
    /*
     * @since v3.10
     */
    BLACKMARK( -132, "La detección de la marca negra ha fallado, no se imprimió nada ", "-1" ),
    /*
     * @since v3.10
     */
    DEFINITIONEXISTS( -133, " El formato definido ya existe", "-1" ),
    /*
     * @since v3.10
     */
    MEDIAREJECTED( -134, "El papel ha sido rechazado, no se imprimio nada ", "-1" ),
    /*
     * @since v3.10
     */
    MEDIARETRACTED( -135, "El ticket fue retraido antes de presentarse al usuario ", "-1" ),
    /*
     * @since v3.10
     */
    MSFERROR( -136, "No se pudo leer el medio ", "-1" ),
    /*
     * @since v3.10
     */
    NOMSF( -137, "No se encontró una banda magnetica ", "-1" ),
    /*
     * @since v3.10
     */
    FILENOTFOUND( -138, "Archivo no encontrado ", "-1" ),
    /*
     * @since v3.
     *
     */
    POWERSAVETOOSHORT( -139, "Modo de ahorro de energía no activado por tiempo", "-1" ),
    /*
     * @since v3.10
     */
    POWERSAVEMEDIAPRESENT( -140, "Modo de ahorro de energía no activado por medios presentes ", "-1" ),
    /*
     * @since v3.20
     */
    PASSBOOKCLOSED( -141, "Quedaban menos paginas de las indicadas al imprimir ", "-2" ),
    /*
     * @since v3.20
     */
    LASTORFIRSTPAGEREACHED( -142, "La impresora no puede tratar el papel ", "-2" ),
    /*
     * @since v3.30
     */
    COMMANDUNSUPP( -143, "Comando no soportado por el dispositivo ", "-1" ),
    /*
     * @since v3.30
     */
    SYNCHRONIZEUNSUPP( -144, "Sincronización no soportada", "-1" );
    private final int value;
    private String message;
    private String severity;

    private PTRResponse( final int value, String message, String severity ) {
        this.value = value;
        this.message = message;
        this.severity = severity;
    }

    public static AtmResponse getResponseByCode(int code ) {
        for ( PTRResponse d : values() ) {
            if ( d.getValue() == code ) {
                return d;
            }
        }
        return XFSError.getErrorByCode( code );
    }


    public int getValue() {
        return value;
    }

    @Override
    public String getSeverity() {
        return severity;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
