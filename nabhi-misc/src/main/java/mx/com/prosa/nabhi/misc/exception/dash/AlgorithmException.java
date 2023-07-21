package mx.com.prosa.nabhi.misc.exception.dash;


import mx.com.prosa.nabhi.misc.exception.ErrorCode;
import mx.com.prosa.nabhi.misc.exception.GeneralException;

public class AlgorithmException extends GeneralException {

    public AlgorithmException( ErrorCode error ) {
        super( error );
    }

    public AlgorithmException( ErrorCode error, String additional ) {
        super( error, additional );
    }


}
