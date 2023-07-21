package mx.com.prosa.nabhi.misc.exception;

public class ReceiptException extends GeneralException {

    public ReceiptException( ErrorCode error ) {
        super( error );
    }

    public ReceiptException( ErrorCode error, String additional ) {
        super( error, additional );
    }

}
