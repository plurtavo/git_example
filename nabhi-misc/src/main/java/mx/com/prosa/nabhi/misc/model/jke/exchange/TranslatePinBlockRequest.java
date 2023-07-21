package mx.com.prosa.nabhi.misc.model.jke.exchange;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representación de una solcitud de traslado de pinblock" )
public class TranslatePinBlockRequest implements KeyRequest {

    private static final String CLASS = "9";
    private static final String COMMAND = "2";
    @ApiModelProperty( value = "Nemotecnico del cajero", example = "ABCCAP" )
    private final String atmRemote;
    @ApiModelProperty( value = "Tipo de cajero", example = "30" )
    private final String termType;
    @ApiModelProperty( value = "", example = "" )
    private final String atmLocal;
    @ApiModelProperty( value = "Pinblock del usuario", example = "0C59E751F2C6228A" )
    private final String pinBlock;
    @ApiModelProperty( value = "Track2 del usuario", example = "4152313249710307=17032010000080300000" )
    private final String track2;
    @ApiModelProperty( value = "Valor del IPK", example = "000000000000" )
    private final String ipk;
    @ApiModelProperty( value = "Número de secuencia de solicitud", example = "3" )
    private final String sequence;

    public TranslatePinBlockRequest( String termType, String atmRemote, String atmLocal, String pinBlock, String track2, String ipk, String sequence ) {
        this.termType = termType;
        this.atmRemote = atmRemote;
        this.atmLocal = atmLocal;
        this.pinBlock = pinBlock;
        this.track2 = ";" + track2 + "?";
        this.ipk = ipk;
        this.sequence = sequence;
    }


    @Override
    public String getSequence() {
        return sequence;
    }

    @Override
    public String toString() {
        String fs = "" + ( char ) 28;
        return CLASS + fs +
                COMMAND + fs +
                atmRemote + fs +
                termType + fs +
                atmLocal + fs +
                pinBlock + fs +
                track2 + fs +
                fs +
                sequence;
    }
}
