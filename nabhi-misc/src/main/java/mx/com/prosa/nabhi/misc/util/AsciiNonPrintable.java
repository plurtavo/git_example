package mx.com.prosa.nabhi.misc.util;

public enum AsciiNonPrintable {

    NUL( ( byte ) 0 ),
    SOH( ( byte ) 1 ),
    STX( ( byte ) 2 ),
    ETX( ( byte ) 3 ),
    EOT( ( byte ) 4 ),
    ENQ( ( byte ) 5 ),
    ACK( ( byte ) 6 ),
    BEL( ( byte ) 7 ),
    BS( ( byte ) 8 ),
    HT( ( byte ) 9 ),
    LF( ( byte ) 10 ),
    VT( ( byte ) 11 ),
    FF( ( byte ) 12 ),
    CR( ( byte ) 13 ),
    SO( ( byte ) 14 ),
    SI( ( byte ) 15 ),
    DLE( ( byte ) 16 ),
    DC1( ( byte ) 17 ),
    DC2( ( byte ) 18 ),
    DC3( ( byte ) 19 ),
    DC4( ( byte ) 20 ),
    NAK( ( byte ) 21 ),
    SYN( ( byte ) 22 ),
    ETB( ( byte ) 23 ),
    CAN( ( byte ) 24 ),
    EM( ( byte ) 25 ),
    SUB( ( byte ) 26 ),
    ESC( ( byte ) 27 ),
    FS( ( byte ) 28 ),
    GS( ( byte ) 29 ),
    RS( ( byte ) 30 ),
    US( ( byte ) 31 ),
    DEL( ( byte ) 127 );

    private byte value;

    AsciiNonPrintable( byte value ) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static boolean isNonPrintable( byte newValue ) {
        for ( AsciiNonPrintable nonPrint : AsciiNonPrintable.values() ) {
            if ( newValue == nonPrint.getValue() ) {
                return true;
            }
        }
        return false;
    }

    public static byte getValue( byte newValue ) {
        for ( AsciiNonPrintable nonPrint : AsciiNonPrintable.values() ) {
            if ( newValue == nonPrint.getValue() ) {
                return nonPrint.getValue();
            }
        }
        return ( byte ) 255;
    }


    public static byte getNextNonPrintable( String str ) {
        for ( int i = 0; i < str.getBytes().length; i++ ) {
            if ( isNonPrintable( str.getBytes()[ i ] ) ) {
                return str.getBytes()[ i ];
            }
        }
        return ( byte ) 255;
    }

    public static int getIndexNextNonPrintable( String str ) {
        for ( int i = 0; i < str.getBytes().length; i++ ) {
            if ( isNonPrintable( str.getBytes()[ i ] ) ) {
                return i;
            }
        }
        return str.length();
    }

    public static String scand( String str ) {
        String value = "" + ( char ) getNextNonPrintable( str );
        int indexOf = str.indexOf( value );
        return str.substring( indexOf );
    }

}
