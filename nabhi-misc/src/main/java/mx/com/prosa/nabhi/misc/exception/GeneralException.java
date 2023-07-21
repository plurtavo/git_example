package mx.com.prosa.nabhi.misc.exception;

public abstract class GeneralException extends Exception {

    private final transient ErrorCode error;

    protected GeneralException( ErrorCode error ) {
        super( error.getMessage() );
        this.error = error;
    }

    protected GeneralException( ErrorCode error, String additional ) {
        super( ( error.getMessage() + " " + additional ) );
        this.error = error;
    }

    public ErrorCode getError() {
        return error;
    }
}
