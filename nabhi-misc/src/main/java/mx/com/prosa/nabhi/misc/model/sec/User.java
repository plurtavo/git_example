package mx.com.prosa.nabhi.misc.model.sec;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representación de un Usuario para su autenticación" )
public class User {

    @ApiModelProperty( value = "ID o Nombre del usuario", example = "testQA" )
    private String id;
    @ApiModelProperty( value = "Contraseña del usuario", example = "123456" )
    private String p;
    @ApiModelProperty( value = "Bandera que indica si es cajero o usuario", example = "1" )
    private byte q;

    public User() {
    }

    public User( String id, String p, byte q ) {
        this.id = id;
        this.p = p;
        this.q = q;
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getP() {
        return p;
    }

    public void setP( String p ) {
        this.p = p;
    }

    public byte getQ() {
        return q;
    }

    public void setQ( byte q ) {
        this.q = q;
    }

    @Override
    public String toString() {
        return id + ( char ) 28 + p + ( char ) 28 + q;
    }
}
