package mx.com.prosa.nabhi.misc.exception;

public class ATMException extends GeneralException {

    public ATMException( ErrorCode error ) {
        super( error );
    }

    public ATMException( ErrorCode error, String additional ) {
        super( error, additional );
    }

}
