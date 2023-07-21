package mx.com.prosa.nabhi.misc.exception.socket;

import mx.com.prosa.nabhi.misc.exception.ErrorCode;
import mx.com.prosa.nabhi.misc.exception.GeneralException;

public class SocketException extends GeneralException {

    public SocketException( ErrorCode error ) {
        super( error );
    }

    public SocketException( ErrorCode error, String additional ) {
        super( error, additional );
    }
}
