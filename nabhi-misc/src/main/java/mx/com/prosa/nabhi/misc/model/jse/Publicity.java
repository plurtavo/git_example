package mx.com.prosa.nabhi.misc.model.jse;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.jdb.BankStyle;

@ApiModel( description = "Json DTO representación una respuesta para solicitar publicidad para el cajero" )
public class Publicity {

    @ApiModelProperty( value = "Tipo de pantalla del cajero", example = "TOUCH" )
    private String screenType;
    @ApiModelProperty( value = "Nombre de la publicidad a mostrar", example = "B000_TEST_01" )
    private String namePublicity;
    @ApiModelProperty( value = "Estilo de botones y fuente", example = "" )
    private BankStyle bankStyle;


    public String getScreenType() {
        return screenType;
    }

    public void setScreenType( String screenType ) {
        this.screenType = screenType;
    }

    public String getNamePublicity() {
        return namePublicity;
    }

    public void setNamePublicity( String namePublicity ) {
        this.namePublicity = namePublicity;
    }

    public BankStyle getBankStyle() {
        return bankStyle;
    }

    public void setBankStyle( BankStyle bankStyle ) {
        this.bankStyle = bankStyle;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
