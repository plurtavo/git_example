package mx.com.prosa.nabhi.misc.model.devices.constants;
//Cambio release/redcat: Cambio de paquete

public enum XFSError implements AtmResponse {

    SUCCESS( 0, "Comando exitoso", "0" ),
    ALREADY_STARTED( -1, "El dispositivo ya fue iniciado y se trata de volver a iniciar", "0" ),
    API_VER_TOO_HIGH( -2, "La versión de XFS que usa la aplicación es superior a la soportada por la API XFS", "-2" ),
    API_VER_TOO_LOW( -3, "La versión de XFS que usa la aplicación es anterior a la soportada por la API XFS", "-2" ),
    CANCELED( -4, "El comando fue cancelado", "0" ),
    CFG_INVALID_HKEY( -5, "El parámetro especificado no corresponde a ningún registro del sistema abierto en el momento", "-1" ),
    CFG_INVALID_NAME( -6, "El nombre especificado no existe en el registro actual", "-1" ),
    CFG_INVALID_SUBKEY( -7, "La llave especificada no existe", "-1" ),
    CFG_INVALID_VALUE( -8, "El valor especificado no existe en el registro abierto", "-1" ),
    CFG_KEY_NOT_EMPTY( -9, "La llave y sub llaves no pueden ser borradas, primero deben borrarse las sub llaves", "-1" ),
    CFG_NAME_TOO_LONG( -10, "La longitud del nombre a ser retornado excede la longitud del buffer", "-1" ),
    CFG_NO_MORE_ITEMS( -11, "No hay más sub llaves para retornar", "-1" ),
    CFG_VALUE_TOO_LONG( -12, "La longitud del valor a ser retornado excede la longitud del buffer", "-1" ),
    DEV_NOT_READY( -13, "La función requería acceso al dispositivo y  el dispositivo no estaba listo.", "-2" ),
    HARDWARE_ERROR( -14, "La función requería acceso al dispositivo y ocurrió un error en el dispositivo", "-2" ),
    INTERNAL_ERROR( -15, "Se produjo una inconsistencia interna u otro error inesperado en el subsistema XFS", "-2" ),
    INVALID_ADDRESS( -16, "Se esta apuntando a un puntero donde el buffer no fue previamente alojado en la memoria.", "-1" ),
    INVALID_APP_HANDLE( -17, "El identificador de aplicación especificado no es válido, no fue creado por una llamada de creación anterior.", "-1" ),
    INVALID_BUFFER( -18, "El parámetro de salida no es un  puntero a una estructura de buffer asignada", "-1" ),
    INVALID_CATEGORY( -19, "La categoria solicitada no es compatible con la clase de servicio", "-1" ), // TO CHANGE
    INVALID_COMMAND( -20, "El comando solicitado no es compatible con el dispositivo", "-1" ),
    INVALID_EVENT_CLASS( -21, "El evento generado no es soportado por el dispositivo", "-1" ),
    INVALID_HSERVICE( -22, "El parámetro hService no es un identificador de servicio valido", "-1" ),
    INVALID_HPROVIDER( -23, "El parámetro hProvider no es un identificador de proveedor valido ", "-1" ),
    INVALID_HWND( -24, "El parámetro hWnd no es un registro de Windows válido", "-1" ),
    INVALID_HWNDREG( -25, "El parámetro hWndReg no es un registro de Windows valido", "-1" ),
    INVALID_POINTER( -26, "El puntero no apunta a un bloque de memoria  accesible", "-1" ),
    INVALID_REQ_ID( -27, "El parámetro RequestId no corresponde a una solicitud activa de servicio", "-" ),
    INVALID_RESULT( -28, "El parámetro lpResult no es un puntero a una estructura WFSRESULT", "-1" ),
    INVALID_SERVPROV( -29, "El archivo que contiene el proveedor de servicios no es válido o está dañado", "-2" ),
    INVALID_TIMER( -30, "Los parámetros hWnd y wTimerID no corresponden a un temporizador activo actualmente", "-1" ),
    INVALID_TRACELEVEL( -31, "El parámetro dwTraceLevel no corresponde a un nivel de seguimiento válido o conjunto de niveles", "-1" ),
    LOCKED( -32, "El dispositivo está bloqueado bajo un hService diferente", "-2" ),
    NO_BLOCKING_CALL( -33, "No hay una llamada de bloqueo pendiente para el hilo especificado", "-1" ),
    NO_SERVPROV( -34, "El archivo que contiene el proveedor de servicios no existe", "-2" ),
    NO_SUCH_THREAD( -35, "El hilo especificado no existe", "-1" ),
    NO_TIMER( -36, " El temporizador solicitado no existe", "-1" ),
    NOT_LOCKED( -37, "La aplicación que solicita el desbloqueo de un servicio no había realizado previamente un WFSLock o WFSAsyncLock exitoso", "-1" ),
    NOT_OK_TO_UNLOAD( -38, "El Administrador de XFS no puede descargar la DLL del proveedor de servicios", "-2" ),
    NOT_STARTED( -39, "La aplicación no ha realizado previamente un WFSStartUp exitoso del dispositivo", "-1" ),
    NOT_REGISTERED( -40, "El registro de Windows hWndReg especificado no se registró para recibir mensajes para ninguna clase de evento", "-2" ),
    OP_IN_PROGRESS( -41, "Una operación de bloqueo está en progreso en el hilo; solo WFSCancelBlockingCall y WFSIsBlocking están permitidos en este momento", "-1" ),
    OUT_OF_MEMORY( -42, "No hay suficiente memoria disponible para satisfacer la solicitud", "-2" ),
    SERVICE_NOT_FOUND( -43, "El nombre lógico no es un nombre de proveedor de servicios válido", "-2" ),
    SPI_VER_TOO_HIGH( -44, "El rango de versiones de compatibilidad con SPI de XFS solicitada por el Administrador de XFS es mayor que cualquier otra compatibilidad con el Proveedor de servicios para el servicio lógico que se abre", "-2" ),
    SPI_VER_TOO_LOW( -45, "El rango de versiones de compatibilidad con SPI de XFS solicitada por el Administrador de XFS es menor que cualquier otra compatibilidad con el Proveedor de servicios para el servicio lógico que se abre", "-2" ),
    SRVC_VER_TOO_HIGH( -46, "", "-2" ),
    SRVC_VER_TOO_LOW( -47, "", "-2" ),
    TIMEOUT( -48, "El tiempo de espera ha terminado", "-1" ),
    UNSUPP_CATEGORY( -49, "El dwCategory emitido, aunque válido para esta clase de servicio, no es compatible con este proveedor de servicios", "-1" ),
    UNSUPP_COMMAND( -50, "El dwCommand emitido, aunque válido para esta clase de servicio, no es compatible con este proveedor de servicios", "0" ),
    VERSION_ERROR_IN_SRVC( -51, "", "-2" ),
    INVALID_DATA( -52, "La estructura de datos pasada como parámetro de entrada contiene datos no válidos", "-1" ),
    SOFTWARE_ERROR( -53, "La función requería acceso a la información de configuración y se produjo un error en el software", "-2" ),
    CONNECTION_LOST( -54, "", "-2" ),
    /**
     * A user is preventing proper operation of the device.
     *
     * @since 3.00
     */
    USER_ERROR( -55, "El usuario está impidiendo el correcto funcionamiento del dispositivo", "-2" ),
    /**
     * The data structure passed as an input parameter although valid for this
     * service class, is not supported by this service provider or device.
     *
     * @since 3.00
     */
    UNSUPP_DATA( -56, "La estructura de datos que se pasa como parámetro de entrada, aunque es válida para esta clase de servicio, no es compatible con este proveedor de servicios o dispositivo", "-1" );

    private final int value;
    private final String message;
    private final String severity;

    private XFSError( final int value, String message, String severity ) {
        this.value = value;
        this.message = message;
        this.severity = severity;
    }

    public static XFSError getErrorByCode(int code ) {
        for ( XFSError d : values() ) {
            if ( d.getValue() == code ) {
                return d;
            }
        }
        return UNSUPP_COMMAND;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getSeverity() {
        return severity;
    }
}
