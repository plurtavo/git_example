package mx.com.prosa.nabhi.misc.exception.dash;


import mx.com.prosa.nabhi.misc.exception.ErrorCode;
import mx.com.prosa.nabhi.misc.exception.GeneralException;

public class UptimeException extends GeneralException {

    public UptimeException( ErrorCode error ) {
        super( error );
    }

    public UptimeException( ErrorCode error, String additional ) {
        super( error, additional );
    }


}
