package mx.com.prosa.nabhi.misc.exception.iso;

import mx.com.prosa.nabhi.misc.exception.ErrorCode;
import mx.com.prosa.nabhi.misc.exception.GeneralException;

public class ISO8583Exception extends GeneralException {

    public ISO8583Exception( ErrorCode error ) {
        super( error );
    }

    public ISO8583Exception( ErrorCode error, String additional ) {
        super( error, additional );
    }

}
