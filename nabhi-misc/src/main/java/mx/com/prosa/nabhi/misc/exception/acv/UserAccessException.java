package mx.com.prosa.nabhi.misc.exception.acv;

import mx.com.prosa.nabhi.misc.exception.ErrorCode;
import mx.com.prosa.nabhi.misc.exception.GeneralException;

public class UserAccessException extends GeneralException {

    public UserAccessException( ErrorCode error ) {
        super( error );
    }

    public UserAccessException( ErrorCode error, String additional ) {
        super( error, additional );
    }
}
