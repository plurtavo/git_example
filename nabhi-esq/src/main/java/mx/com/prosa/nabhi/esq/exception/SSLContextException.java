package mx.com.prosa.nabhi.esq.exception;


public class SSLContextException extends Exception {

    private final String errorMessage;

    public SSLContextException( String errorMessage ) {
        super();
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }

}
