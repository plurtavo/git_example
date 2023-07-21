package mx.com.prosa.nabhi.esq.exception;


public class XFSException extends Exception {

    private final String errorMessage;

    public XFSException( String errorMessage ) {
        super();
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }

}
