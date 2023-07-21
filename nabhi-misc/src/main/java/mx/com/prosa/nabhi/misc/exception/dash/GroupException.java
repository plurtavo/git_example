package mx.com.prosa.nabhi.misc.exception.dash;


import mx.com.prosa.nabhi.misc.exception.ErrorCode;
import mx.com.prosa.nabhi.misc.exception.GeneralException;

public class GroupException extends GeneralException {

    public GroupException( ErrorCode error ) {
        super( error );
    }

    public GroupException( ErrorCode error, String additional ) {
        super( error, additional );
    }


}
