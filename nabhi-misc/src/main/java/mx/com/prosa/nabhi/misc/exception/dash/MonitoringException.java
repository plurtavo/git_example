package mx.com.prosa.nabhi.misc.exception.dash;


import mx.com.prosa.nabhi.misc.exception.ErrorCode;
import mx.com.prosa.nabhi.misc.exception.GeneralException;

public class MonitoringException extends GeneralException {

    public MonitoringException( ErrorCode error ) {
        super( error );
    }

    public MonitoringException( ErrorCode error, String additional ) {
        super( error, additional );
    }

}
