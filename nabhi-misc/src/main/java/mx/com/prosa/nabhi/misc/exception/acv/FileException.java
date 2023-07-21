package mx.com.prosa.nabhi.misc.exception.acv;

import mx.com.prosa.nabhi.misc.exception.ErrorCode;
import mx.com.prosa.nabhi.misc.exception.GeneralException;

public class FileException extends GeneralException {

    public FileException( ErrorCode error ) {
        super( error );
    }

    public FileException( ErrorCode error, String additional ) {
        super( error, additional );
    }
}
