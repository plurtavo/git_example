package mx.com.prosa.nabhi.misc.exception.db;

import mx.com.prosa.nabhi.misc.exception.ErrorCode;
import mx.com.prosa.nabhi.misc.exception.GeneralException;

public class IDFException extends GeneralException {

    public IDFException( ErrorCode error ) {
        super( error );
    }

    public IDFException( ErrorCode error, String additional ) {
        super( error, additional );
    }
}
