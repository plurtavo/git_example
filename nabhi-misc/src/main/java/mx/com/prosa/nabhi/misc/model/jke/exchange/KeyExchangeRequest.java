package mx.com.prosa.nabhi.misc.model.jke.exchange;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "Json DTO representación de una solicitud de llave" )
public class KeyExchangeRequest implements KeyRequest {

    private static final String CLASS = "9";
    private static final String COMMAND = "1";
    @ApiModelProperty( value = "Tipo de cajero", example = "22" )
    private final String termType;
    @ApiModelProperty( value = "Nemotecnico del cajero", example = "ABCCAP" )
    private final String atmRemote;
    @ApiModelProperty( value = "", example = "" )
    private final String atmLocal;
    @ApiModelProperty( value = "Secuencia de solicitud", example = "1" )
    private final String sequence;

    public KeyExchangeRequest( String termType, String atmRemote, String atmLocal, String sequence ) {
        this.termType = termType;
        this.atmRemote = atmRemote;
        this.atmLocal = atmLocal;
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
                sequence;

    }
}
