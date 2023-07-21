package mx.com.prosa.nabhi.misc.exception;

public class TransactionException extends GeneralException {

    public TransactionException( ErrorCode error ) {
        super( error );
    }

    public TransactionException( ErrorCode error, String additional ) {
        super( error, additional );
    }

}
