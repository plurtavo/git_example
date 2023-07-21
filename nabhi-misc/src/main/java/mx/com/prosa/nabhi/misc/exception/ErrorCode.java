package mx.com.prosa.nabhi.misc.exception;

public interface ErrorCode {

    int getCode();

    String getMessage();

    boolean isCritical();

    ErrorCode findByCodeAndCritical( int code );

    ErrorCode findByCode( int code );


}
