package us.gonet.nabhi.misc.model.fx.buffer;

public enum BufferIDs {

    RESPONSE_DATA( "063" ),
    PIN_DATA( "064" ),
    A_DOLLAR_BUFFER( "065" ),
    B_GENERAL_BUFFER( "066" ),
    C_GENERAL_BUFFER( "067" ),
    E_GENERAL_EMV( "068" ),
    F_ENTRY_MODE( "069" ),
    G_TRAN_CODE( "070" ),
    H_SURCHARGE( "071" ),
    L_PRINTER_LINE( "076" ),
    N_CONSUMER_NAME( "078" ),
    O_FROM_ACCT( "079" ),
    P_RECEIPT_PRINTER( "080" ),
    Q_DEPOSITORY_PRINTER( "081" ),
    R_JOURNAL_PRINTER( "082" ),
    S_STATEMENT_PRINTER( "083" ),
    T_TRANSACTION_SERIAL( "084" ),
    U_OAR( "085" ),
    V_PASSBOCK_PRINTER( "086" ),
    W_MICR( "087" ),
    X_TRACK1( "088" ),
    Y_TRACK2( "089" ),
    Z_TRACK3( "090" ),
    MISC_1( "991" ),
    MISC_2( "992" ),
    MISC_3( "993" ),
    MISC_4( "994" ),
    MISC_5( "995" ),
    MISC_6( "996" ),
    INVOKED_TIMEOUT( "997" ),
    TIMEOUT( "998" ),
    ERROR_MESSAGE( "999" ),
    INVALID( "000" );

    private String id;

    BufferIDs( String id ) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    protected static String getValue( String key ) {
        for ( BufferIDs buffer : BufferIDs.values() ) {
            if ( buffer.getID().equals( key ) ) {
                return buffer.getID();
            }
        }
        return key;
    }

    public static BufferIDs getBuffer( String value ) {
        for ( BufferIDs buffer : BufferIDs.values() ) {
            if ( buffer.getID().equals( value ) ) {
                return buffer;
            }
        }
        return INVALID;
    }
}
