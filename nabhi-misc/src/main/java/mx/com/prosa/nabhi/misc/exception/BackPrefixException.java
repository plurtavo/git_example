package mx.com.prosa.nabhi.misc.exception;

public class BackPrefixException extends GeneralException {

    public BackPrefixException( ErrorCode error ) {
        super( error );
    }

    public BackPrefixException( ErrorCode error, String additional ) {
        super( error, additional );
    }

}
