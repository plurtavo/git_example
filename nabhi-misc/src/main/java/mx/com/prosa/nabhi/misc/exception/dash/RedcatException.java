package mx.com.prosa.nabhi.misc.exception.dash;


import mx.com.prosa.nabhi.misc.exception.ErrorCode;
import mx.com.prosa.nabhi.misc.exception.GeneralException;

public class RedcatException extends GeneralException {

    public RedcatException( ErrorCode error ) {
        super( error );
    }

    public RedcatException( ErrorCode error, String additional ) {
        super( error, additional );
    }

}
