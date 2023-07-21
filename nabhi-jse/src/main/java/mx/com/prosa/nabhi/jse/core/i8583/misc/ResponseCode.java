package mx.com.prosa.nabhi.jse.core.i8583.misc;

public enum ResponseCode {

    APPROVED( "ISO00", "APROBADO" ),
    REFER_TO_CARD_ISSUER( "ISO01", "" ),
    REFER_TO_SPECIAL( "ISO02", ResponseMessage.CONTACT ),
    INVALID_MERCHANT( "ISO03", "COMERCIO INVALIDO" ),
    PICK_UP( "ISO04", ResponseMessage.CONTACT ),
    DO_NOT_HONOR( "ISO05", ResponseMessage.CONTACT ),
    ERROR( "ISO06", "ERROR AL PROCESAR SU TRANSACCIÓN" ),
    PICK_UP_CARD( "ISO07", ResponseMessage.CONTACT ),
    HONOR_WITH( "ISO08", ResponseMessage.CONTACT ),
    REQUEST_IN_PROGRESS( "ISO09", "TRANSACCION CANCELADA, YA EXISTE UN SOLICITUD ACTUALMENTE" ),
    APPROVED_FOR_PARTIAL( "ISO10", "APROBADO POR IMPORTE PARCIAL" ),
    APPROVED_VIP( "ISO11", "APROBADO VIP" ),
    INVALID_TRANSACTION( "ISO12", "TRANSACCION NO PERMITIDA POR SU BANCO" ),
    INVALID_AMOUNT( "ISO13", "MONTO INVALIDO" ),
    INVALID_CARD_NUMBER( "ISO14", ResponseMessage.INVALID_CARD ),
    NO_SUCH_ISSUER( "ISO15", "NO SE PUEDE CONTACTAR AL EMISOR" ),
    APPROVED_UPDATE( "ISO16", ResponseMessage.INVALID_CARD ),
    CUSTOMER_CANCELLATION( "ISO17", "TRANSACCIÓN CANCELADA POR EL CLIENTE" ),
    CUSTOMER_DISPUTE( "ISO18", ResponseMessage.CONTACT ),
    RE_ENTER_TRANSACTION( "ISO19", ResponseMessage.CONTACT ),
    INVALID_RESPONSE( "ISO20", "RESPUESTA INVALIDA POR PARTE DEL EMISOR" ),
    NO_ACTION_TAKEN( "ISO21", ResponseMessage.CONTACT ),
    SUSPECTED_MALFUNCTION( "ISO22", "SOSPECHA DE MALFUNCIÓN EN EL SISTEMA" ),
    UNACCEPTABLE( "ISO23", "CONTACTE A SU BANCO, NO SE PUEDE AGREGAR LA COMSIÓN" ),
    FORMAT_ERROR( "ISO30", "ERROR EN EL FORMATO" ),
    BANK_NOT_SUPPORTED_BY( "ISO31", "EL BANCO EMISOR NO ES SOPORTADO" ),
    COMPLETED_PARTIALLY( "ISO32", ResponseMessage.CONTACT ),
    EXPIRED_CARD( "ISO33", "LA TARJETA INTRODUCIDA HA EXPIRADO" ),
    SUSPECTED_FRAUD( "ISO34", "SE SOSPECHA DE FRAUDE" ),
    CARD_ACCEPTOR_CONTACT( "ISO35", ResponseMessage.CONTACT ),
    RESTRICTED_CARD( "ISO36", "TARJETA NO PERMITIDA" ),
    CARD_ACCEPTOR_CALL( "ISO37", "POR EL MOMENTO NO SE PUEDE PROCESAR LA TRANSACCIÃ“N" ),
    ALLOWABLE_PIN_TRIES( "ISO38", "LIMITE DE INTENTOS DE NIP EXCEDIDO" ),
    NO_CREDIT_ACCOUNT( "ISO39", "CUENTA DE CREDITO NO ENCONTRADA" ),
    REQUESTED_FUNCTION( "ISO40", "TRANSACCION NO PERMITIDA" ),
    LOST_CARD( "ISO41", "TRANSACCION RECHAZADA POR UN REPORTE EN LA TARJETA" ),
    NO_UNIVERSAL_ACCOUNT( "ISO42", ResponseMessage.CONTACT ),
    STOLEN_CARD_PICK_UP( "ISO43", "TRANSACCION RECHAZADA POR UN REPORTE EN LA TARJETA" ),
    NO_INVESTMENT( "ISO44", "CUENTA DE INVERSION NO ENCONTRADA" ),
    NOT_SUFFICIENT_FUNDS( "ISO51", "FONDOS NO SUFICIENTES" ),
    NO_CHEQUING_ACCOUNT( "ISO52", "CUENTA DE CHEQUES NO ENCONTRADA" ),
    NO_SAVINGS_ACCOUNT( "ISO53", "CUENTA DE AHORROS NO ENCONTRADA" ),
    EXPIRED_CARD_1( "ISO54", "LA TARJETA INTRODUCIDA HA EXPIRADO" ),
    INCORRECT_PIN( "ISO55", "NIP INVALIDO" ),
    NO_CARD_RECORD( "ISO56", ResponseMessage.INVALID_CARD ),
    TRANSACTION_NOT_CARD( "ISO57", ResponseMessage.CONTACT ),
    TRANSACTION_NOT_TERMINAL( "ISO58", "TRANSACCION NO PERMITITDA" ),
    SUSPECTED_FRAUD_1( "ISO59", "TRANSACCION RECHAZADA POR SOSPECHA DE FRAUDE" ),
    CARD_ACCEPTOR_CONTACT_A( "ISO60", ResponseMessage.CONTACT ),
    EXCEEDS_WITHDRAWAL( "ISO61", "MONTO MAXIMO DE RETIROS ALCANZADO" ),
    RESTRICTED_CARD_A( "ISO62", "TARJETA NO VALIDA" ),
    SECURITY_VIOLATION( "ISO63", ResponseMessage.CONTACT ),
    EXCEEDS_WITHDRAWAL_FL( "ISO65", "LIMITE DE RETIROS ALCANZADO" ),
    CARD_ACCEPTOR_CALL_SEC( "ISO66", ResponseMessage.CONTACT ),
    HARD_CAPTURE( "ISO67", ResponseMessage.CONTACT ),
    PIN_TRIES_EXCEEDED( "ISO75", "INTENTOS DE NIP EXCEDIDOS" ),
    RESERVED_FOR_PRIVATE_76( "ISO76", ResponseMessage.CONTACT ),
    RESERVED_FOR_PRIVATE_77( "ISO77", ResponseMessage.CONTACT ),
    RESERVED_FOR_PRIVATE_78( "ISO78", ResponseMessage.CONTACT ),
    RESERVED_FOR_PRIVATE_79( "ISO79", ResponseMessage.CONTACT ),
    RESERVED_FOR_PRIVATE_80( "ISO80", ResponseMessage.CONTACT ),
    RESERVED_FOR_PRIVATE_81( "ISO81", ResponseMessage.CONTACT ),
    RESERVED_FOR_PRIVATE_83( "ISO83", "EL NÚMERO TELEFÓNICO NO SE ENCUENTRA ACTIVO" ),
    RESERVED_FOR_PRIVATE_86( "ISO86", ResponseMessage.CONTACT ),
    RESERVED_FOR_PRIVATE_87( "ISO87", ResponseMessage.CONTACT ),
    RESERVED_FOR_PRIVATE_88( "ISO88", ResponseMessage.CONTACT ),
    RESERVED_FOR_PRIVATE_89( "ISO89", ResponseMessage.CONTACT ),
    CUTOFF_IS_IN_PROCESS_90( "ISO90", ResponseMessage.CONTACT ),
    ISSUER_INOPERATIVE( "ISO91", ResponseMessage.CONTACT ),
    NOT_ROUTING( "ISO92", ResponseMessage.CONTACT ),
    VIOLATION_OF_LAW( "ISO93", ResponseMessage.CONTACT ),
    DUPLICATE_TRANSMISSION( "ISO94", "TRANSACCION DUPLICADA" ),
    RECONCILE_ERROR( "ISO95", ResponseMessage.CONTACT ),
    SYSTEM_MALFUNCTION( "ISO96", ResponseMessage.CONTACT ),
    ARQC_FAILURE_DECLINE( "ISOU0", ResponseMessage.CONTACT ),
    SECURITY_MODULE_INV_PARAM( "ISOU1", ResponseMessage.CONTACT ),
    SECURITY_MODULE_FAILURE( "ISOU2", ResponseMessage.CONTACT ),
    KEY1_RECORD_NOT( "ISOU3", ResponseMessage.CONTACT ),
    ATC_CHECK_FALURE( "ISOU4", ResponseMessage.CONTACT ),
    CVR_DECLINE( "ISOU5", ResponseMessage.CONTACT ),
    TVR_DECLINE( "ISOU6", ResponseMessage.CONTACT ),
    FALLBACK_DECLINE( "ISOU8", ResponseMessage.CONTACT ),
    ARQC_FAILURE_CAPTURE( "ISOV7", ResponseMessage.CONTACT ),
    CVR_CAPTURE( "ISOV8", ResponseMessage.CONTACT ),
    UN_SUPPORT( "ISO##", ResponseMessage.CONTACT );

    private final String value;
    private final String message;

    ResponseCode( String value, String message ) {
        this.value = value;
        this.message = message;
    }

    public String getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }


    public static String getValueOf( String value ) {
        for ( ResponseCode r : ResponseCode.values() ) {
            if ( r.getValue().equals( value ) ) {
                return r.name();
            }
        }
        return UN_SUPPORT.name();
    }

    public static String getMessageFromCode( String code ) {
        for ( ResponseCode c : values() ) {
            if ( c.getValue().equals( code ) ) {
                return c.getMessage();
            }
        }
        return UN_SUPPORT.getMessage();
    }

    public static String geValueFromCode( String code ) {
        for ( ResponseCode c : values() ) {
            if ( c.getValue().equals( code ) ) {
                return c.getValue();
            }
        }
        return UN_SUPPORT.getMessage();
    }
    
    private static final class ResponseMessage{
        
        private static final String CONTACT = "CONTACTE A SU BANCO";
        private static final String INVALID_CARD = "TARJETA INVALIDA";
    }

}
