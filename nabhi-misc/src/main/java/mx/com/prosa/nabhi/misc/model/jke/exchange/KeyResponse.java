package mx.com.prosa.nabhi.misc.model.jke.exchange;

public interface KeyResponse {

    String getSequence();

    String getMODE();

    String getBLOCK();

    void setError( String error );

}
