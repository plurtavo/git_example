package mx.com.prosa.nabhi.misc.exception.dash;


import mx.com.prosa.nabhi.misc.exception.ErrorCode;
import mx.com.prosa.nabhi.misc.exception.GeneralException;

public class UserNotFoundException extends GeneralException {

    public UserNotFoundException( ErrorCode error ) {
        super( error );
    }

    public UserNotFoundException( ErrorCode error, String additional ) {
        super( error, additional );
    }



}
