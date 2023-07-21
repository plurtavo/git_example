package mx.com.prosa.nabhi.misc.exception;

public class XFSException extends GeneralException {

    public XFSException( ErrorCode error ) {
        super( error );
    }

    public XFSException( ErrorCode error, String additional ) {
        super( error, additional );
    }

}
