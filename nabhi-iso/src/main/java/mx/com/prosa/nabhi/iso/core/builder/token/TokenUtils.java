package mx.com.prosa.nabhi.iso.core.builder.token;

import org.springframework.stereotype.Component;

@Component
public class TokenUtils {

    public String fillLeft( int length, String fill, String data ) {
        StringBuilder stringBuilder = new StringBuilder( data );
        int count = length - data.length();
        for ( ; count < length; count++ ) {
            stringBuilder.append( fill );
        }
        return stringBuilder.toString();
    }

    public String fillRight( int length, String fill, String data ) {
        StringBuilder stringBuilder = new StringBuilder();
        int count = length - data.length();
        for ( ; count < length; count++ ) {
            stringBuilder.append( fill );
        }
        stringBuilder.append( data );
        return stringBuilder.toString();
    }

    public String formatISOAmount( int lenght, String data ) {
        if ( !data.contains( "." ) ) {
            return data;
        }
        String integer = data.substring( 0, data.indexOf( '.' ) );
        String decimal = data.substring( data.indexOf( '.' ) + 1, data.length() );
        int isoAmount = 0;
        if ( isNumeric( integer + decimal ) ) {
            isoAmount = Integer.parseInt( integer + decimal );
        }
        return zeroPaddingLeft( lenght, isoAmount );
    }

    public boolean isNumeric( String str ) {
        try {
            Integer.parseInt( str );
            return true;
        } catch ( NumberFormatException e ) {
            return false;
        }
    }

    public String zeroPaddingLeft( int lenght, int value ) {
        return String.format( ( "%0" + lenght + "d" ), value );
    }

    public String zeroPaddingRight( int lenght, int value ) {
        return String.format( ( "%0" + lenght + "d" ), value );
    }
}
