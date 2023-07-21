package mx.com.prosa.nabhi.misc.model;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representación de una respuesta de cada controlador" )
public final class ResponsePayload< T > {

    @ApiModelProperty( value = "Modelo de respuesta según la petición", example = "" )
    private T body;
    @ApiModelProperty( value = "Mensaje de error de respuesta", example = "No se encuentra el cajero" )
    private String error;
    @ApiModelProperty( value = "Código de error de respuesta", example = "02" )
    private int errorCode;

    public ResponsePayload() {
    }

    private ResponsePayload( T body ) {
        this.body = body;
    }

    public ResponsePayload( String error ) {
        this.error = error;
    }

    public ResponsePayload( String error, int errorCode ) {
        this.error = error;
        this.errorCode = errorCode;
    }

    public T getBody() {
        return body;
    }

    public static < T > ResponsePayload < T > of( T data ) {
        return new ResponsePayload <>( data );
    }

    public static < T > ResponsePayload < T > setError( String error ) {
        return new ResponsePayload <>( error );
    }

    public static < T > ResponsePayload < T > setError( String error, int errorCode ) {
        return new ResponsePayload <>( error, errorCode );
    }

    public String getError() {
        return error;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
