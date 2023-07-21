package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de transacciones permitidas y a que nivel" )
public class TranAllowed implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Codigo que permite identificar si la transaccion es permitida y a que nivel", example = "4" )
    private String allowedCode;


    public TranAllowed() {
    }

    public TranAllowed( String allowedCode ) {
        this.allowedCode = allowedCode;
    }

    public String getAllowedCode() {
        return allowedCode;
    }

    public void setAllowedCode( String allowedCode ) {
        this.allowedCode = allowedCode;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
