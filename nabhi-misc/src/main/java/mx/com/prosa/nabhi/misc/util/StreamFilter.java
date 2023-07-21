package mx.com.prosa.nabhi.misc.util;

import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.sanitize.SanitazeException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class StreamFilter {


    String sanitizeString( String val, String... customRegex ) throws SanitazeException {
        String regex = "[a-zA-Z0-9 -.=*:,/_]+";
        if ( customRegex != null && customRegex.length > 0 ) {
            regex = customRegex[ 0 ];
        }
        if ( val == null || val.length() == 0 ){
            return null;
        }else {
            if ( Pattern.matches( regex, val ) ) {
                String newVal = Pattern.quote( val );
                return newVal.substring( 2, newVal.length() - 2 );
            } else {
                throw new SanitazeException( CatalogError.SANITIZE_INPUT_ERROR, String.format( "Parámetro invalido %s", val ) );
            }
        }
    }

}
