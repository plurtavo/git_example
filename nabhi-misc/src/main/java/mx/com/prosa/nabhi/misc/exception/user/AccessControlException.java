package mx.com.prosa.nabhi.misc.exception.user;

import mx.com.prosa.nabhi.misc.exception.ErrorCode;
import mx.com.prosa.nabhi.misc.exception.GeneralException;

public class AccessControlException extends GeneralException {

    public AccessControlException( ErrorCode error ) {
        super( error );
    }

    public AccessControlException( ErrorCode error, String additional ) {
        super( error, additional );
    }
}
