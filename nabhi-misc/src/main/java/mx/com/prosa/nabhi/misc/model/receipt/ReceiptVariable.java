package mx.com.prosa.nabhi.misc.model.receipt;

public enum ReceiptVariable {

    DATE( "Fecha dd/MM/yy" ),
    DATE_TIME( "Fecha y hora dd/MM/yy HH:mm" ),
    TIME( "Hora HH:mm" ),
    AT( "Mnemotécnico" ),
    TC( "Código de Transacción ISO 2" ),
    PAN( "PAN del usuario ISO 35" ),
    AC( "Código de autorización ISO 38" ),
    FC( "Código de cuenta origen ISO 2" ),
    OB( "Salta anterior ISO 44" ),
    AMT( "Monto de la transacción ISO 3" ),
    SRH( "Comisión de la transacción ISO 126 T25" ),
    IVA( "Iva de la comisión ISO 126 T25 16%" ),
    TT( "Total: Monto de la transacción ISO 3 + Comisión ISO 126 T25" ),
    CB( "Saldo actual ISO 44" ),
    AID( "Application ID EMV TAG 9F06" ),
    ARQC( "Application ID EMV TAG 9F26" ),
    ARPC( "Application ID EMV TAG 9F12" ),
    PHN( "Numero telefónico ARM" ),
    COMP( "Compañía telefonica ARM" ),
    STMT( "Últimos movimientos ISO 125" ),
    OWNER( "Dueño del cajero ARM" ),
    LOC( "Ubicación del cajero ARM" ),
    SEC( "Secuencia de la transacción ISO 37" ),
    ATC( "Numero de cuenta usuario ISO 102" ),
    PEM( "Modo de entrada ARM" ),
    COD( "Datos discrecionales ISO 35" ),
    TCAP( "Capacidades de la terminal ARM" ),
    PROD( "Marca de la tarjeta ISO 35" ),
    EMPTY( "Sin variable" ),
    UNKNOWN( "Variable desconocida" );

    private final String description;

    ReceiptVariable( String description ) {
        this.description = description;
    }

    public static ReceiptVariable getValue( String name ) {
        for ( ReceiptVariable variable : ReceiptVariable.values() ) {
            if ( variable.name().equals( name ) ) {
                return variable;
            }
        }
        return UNKNOWN;
    }
}
