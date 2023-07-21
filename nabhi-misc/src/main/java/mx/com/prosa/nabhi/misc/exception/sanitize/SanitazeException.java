package mx.com.prosa.nabhi.misc.exception.sanitize;

import mx.com.prosa.nabhi.misc.exception.ErrorCode;
import mx.com.prosa.nabhi.misc.exception.GeneralException;

public class SanitazeException extends GeneralException {

    public SanitazeException( ErrorCode error ) {
        super( error );
    }

    public SanitazeException( ErrorCode error, String additional ) {
        super( error, additional );
    }
}
