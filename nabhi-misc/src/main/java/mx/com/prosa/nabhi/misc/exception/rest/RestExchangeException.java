package mx.com.prosa.nabhi.misc.exception.rest;

import mx.com.prosa.nabhi.misc.exception.ErrorCode;
import mx.com.prosa.nabhi.misc.exception.GeneralException;

public class RestExchangeException extends GeneralException {

    public RestExchangeException( ErrorCode error ) {
        super( error );
    }

    public RestExchangeException( ErrorCode error, String additional ) {
        super( error, additional );
    }

}
