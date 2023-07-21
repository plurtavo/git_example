package mx.com.prosa.nabhi.misc.model.jse.response;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.jse.StylesBank;

@ApiModel( description = "Json DTO representación una respuesta para consultar estilos de pantalla según el bin de la tarjeta" )
public class CardInfo {

    @ApiModelProperty( value = "Nombre del banco", example = "Prosa" )
    private String bank;
    @ApiModelProperty( value = "Tipo de tarjeta", example = "CHIP" )
    private String message;
    @ApiModelProperty( value = "Estilos a presentar en pantalla según el banco", example = "" )
    private StylesBank styles;

    public String getBank() {
        return bank;
    }

    public void setBank( String bank ) {
        this.bank = bank;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public StylesBank getStyles() {
        return styles;
    }

    public void setStyles( StylesBank styles ) {
        this.styles = styles;
    }

    public CardInfo() {
    }

    public CardInfo( String bank, String message, StylesBank styles ) {
        this.bank = bank;
        this.message = message;
        this.styles = styles;
    }

    public CardInfo( String bank, String message ) {
        this.bank = bank;
        this.message = message;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
