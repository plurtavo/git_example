package mx.com.prosa.nabhi.misc.model.sec;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representación de una respuesta de atuenticación para acceso a la aplicación" )
public class KeyAuth {

    @ApiModelProperty( value = "Código de respuesta", example = "200" )
    private String code;
    @ApiModelProperty( value = "Mensaje de respuesta", example = "OK" )
    private String message;
    @ApiModelProperty( value = "Token de autorización", example = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2MjY4ODYxODYsInN1YiI6InRlc3RRQSIsImZpbmdlciI6eyJuYW1lIjoicmVuYW1lIiwiaXNzdWVUaW1lIjoxNjA2NzgwODAwLCJleHBpcmF0aW9uVGltZSI6MTg5MzQ1NjAwMCwiZXhwaXJhdGlvblRva2VuVGltZSI6ODQ2MDAsImxhc3RVc2FnZSI6MCwib3duZXIiOnsiZmlpZCI6IlBST1MiLCJuYW1lIjoiUHJvc2EifX0sIkNMQUlNX1RPS0VOIjoiQURNSU5JU1RSQURPUixjcmVhdGUtYXBjLGNyZWF0ZS1hdGQsY3JlYXRlLWJhbmstc3R5bGUsY3JlYXRlLWJpbixjcmVhdGUtaWRmLGNyZWF0ZS1pbWFnZSxjcmVhdGUtbm9kZSxjcmVhdGUtcmNwdCxjcmVhdGUtc2NyZWVuLWdyb3VwLGNyZWF0ZS1zdXJjaGFyZ2UsZGVsZXRlLWFwYyxkZWxldGUtYXRkLGRlbGV0ZS1iYW5rLXN0eWxlLGRlbGV0ZS1iaW4sZGVsZXRlLWlkZixkZWxldGUtaW1hZ2UsZGVsZXRlLW5vZGUsZGVsZXRlLXN1cmNoYXJnZSxtb25pdG9yaW5nLHJlYWQtYXBjLHJlYWQtYXRkLHJlYWQtYmFuLWtzdHlsZSxyZWFkLWJpbixyZWFkLWNvdW50cnkscmVhZC1jb3VudHkscmVhZC1pZGYscmVhZC1pbWFnZSxyZWFkLWpvdXJuYWwscmVhZC1ub2RlLHJlYWQtcGFja2FnZSxyZWFkLXJjcHQscmVhZC1zY3JlZW4scmVhZC1zdGF0ZSxyZWFkLXN1cmNoYXJnZSxyZWFkLXR5cGUscmVhZC11cHRpbWUsc29ja2V0LHdyaXRlLWFwYyx3cml0ZS1hdGQsd3JpdGUtYmFuay1zdHlsZSx3cml0ZS1iaW4sd3JpdGUtaWRmLHdyaXRlLW5vZGUsd3JpdGUtcmNwdCx3cml0ZS1zY3JlZW4tZ3JvdXAsd3JpdGUtc3VyY2hhcmdlIn0.S8H5RPOCAsBS4BxSB8uGHq6l04KC5_o6oztdY5VlUpG0DeWkRB1NrECHEw7zoriLxO0oxYElOFgLqjfw9OQe9A" )
    private String accessToken;
    @ApiModelProperty( value = "Tipo de token", example = "" )
    private String tokenType;
    @ApiModelProperty( value = "Fecha de solicitud de nuevo token", example = "Wed Jul 21 16:49:46 GMT 2021" )
    private String refreshToken;
    @ApiModelProperty( value = "Fecha de expiración del token", example = "Wed Jul 21 16:49:46 GMT 2021" )
    private String expiresIn;
    @ApiModelProperty( value = "Tiempo de duración del token", example = "84600" )
    private long jwtExpires;
    @ApiModelProperty( value = "Scope del token", example = "" )
    private String scope;

    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken( String accessToken ) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType( String tokenType ) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken( String refreshToken ) {
        this.refreshToken = refreshToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn( String expiresIn ) {
        this.expiresIn = expiresIn;
    }

    public long getJwtExpires() {
        return jwtExpires;
    }

    public void setJwtExpires( long jwtExpires ) {
        this.jwtExpires = jwtExpires;
    }

    public String getScope() {
        return scope;
    }

    public void setScope( String scope ) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
