package mx.com.prosa.nabhi.misc.model;

public class ResponseError {

    private String error;
    private int errorCode;

    public String getError() {
        return error;
    }

    public void setError( String error ) {
        this.error = error;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode( int errorCode ) {
        this.errorCode = errorCode;
    }
}