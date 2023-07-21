package mx.com.prosa.nabhi.esq.exception;


public class SocketException extends Exception {

    private final String errorMessage;

    public SocketException( String errorMessage ) {
        super();
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }

}
