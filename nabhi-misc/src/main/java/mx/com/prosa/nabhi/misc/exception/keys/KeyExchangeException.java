package mx.com.prosa.nabhi.misc.exception.keys;

import mx.com.prosa.nabhi.misc.exception.ErrorCode;
import mx.com.prosa.nabhi.misc.exception.GeneralException;

public class KeyExchangeException extends GeneralException {

    public KeyExchangeException( ErrorCode error ) {
        super( error );
    }

    public KeyExchangeException( ErrorCode error, String additional ) {
        super( error, additional );
    }

}
