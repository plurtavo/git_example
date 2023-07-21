package mx.com.prosa.nabhi.misc.model.jdb.personalized;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.jdb.ScreenGroup;
import mx.com.prosa.nabhi.misc.model.jdb.onlykeys.IDFKey;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de un ATD (Para consultar publicidad y transacciones activas)" )
public class ATDScreen implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Nemotécnico del cajero", example = "ABCCAP" )
    private String terminalId;
    @ApiModelProperty( value = "IP del cajero", example = "192.168.100.2" )
    private String ip;
    @ApiModelProperty( value = "Tipo de pantalla del cajero", example = "TOUCH" )
    private String screenType;
    @ApiModelProperty( value = "IDF dueño del cajero", example = "B000" )
    private IDFKey idf;
    @ApiModelProperty( value = "Grupo de pantallas", example = "B00_ATM_01" )
    private ScreenGroup screenGroup;


    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp( String ip ) {
        this.ip = ip;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType( String screenType ) {
        this.screenType = screenType;
    }

    public IDFKey getIdf() {
        return idf;
    }

    public void setIdf( IDFKey idf ) {
        this.idf = idf;
    }

    public ScreenGroup getScreenGroup() {
        return screenGroup;
    }

    public void setScreenGroup( ScreenGroup screenGroup ) {
        this.screenGroup = screenGroup;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
