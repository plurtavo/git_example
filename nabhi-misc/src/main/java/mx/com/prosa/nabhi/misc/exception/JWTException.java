package mx.com.prosa.nabhi.misc.exception;

public class JWTException extends GeneralException {

    public JWTException( ErrorCode error ) {
        super( error );
    }

    public JWTException( ErrorCode error, String additional ) {
        super( error, additional );
    }

}
