package mx.com.prosa.nabhi.misc.util;

import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.sanitize.SanitazeException;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.regex.Pattern;

@Component
public class Sanitizer {

    private static final String REGEX = "[a-zA-Z0-9-.=*:,/_?@^; ]+";

    public String isCharacter( String search ) {
        StringBuilder sb = new StringBuilder( search.length() );
        for ( int i = 0; i < search.length(); ++i ) {
            char ch = search.charAt( i );
            if ( Character.isLetterOrDigit( ch ) || ch == ' ' || ch == '=' || ch == '-' ) {
                sb.append( ch );
            }
        }
        return sb.toString();
    }

    public String sanitize( String input ) throws SanitazeException {
        if ( input == null || input.length() == 0 ){
            return null;
        }else {
            if ( Pattern.matches( REGEX, input ) ) {
                String newVal = Pattern.quote( input );
                return newVal.substring( 2, newVal.length() - 2 );
            } else {
                throw new SanitazeException( CatalogError.SANITIZE_INPUT_ERROR );
            }
        }
    }

    public boolean urlWhiteList( URI url ) {
        return url.getScheme().startsWith( "http" );
    }

}
