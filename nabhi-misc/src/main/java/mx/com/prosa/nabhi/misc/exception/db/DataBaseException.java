package mx.com.prosa.nabhi.misc.exception.db;

import mx.com.prosa.nabhi.misc.exception.ErrorCode;
import mx.com.prosa.nabhi.misc.exception.GeneralException;

public class DataBaseException extends GeneralException {

    public DataBaseException( ErrorCode error ) {
        super( error );
    }

    public DataBaseException( ErrorCode error, String additional ) {
        super( error, additional );
    }
}
