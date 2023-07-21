package mx.com.prosa.nabhi.misc.model.jdb.personalized;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.domain.algorithm.dto.DispensedAlgorithm;
import mx.com.prosa.nabhi.misc.model.devices.DevicesWrapper;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de un ATD (Para consultar dispositivos)" )
public class ATDDevice implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Nemotécnico del cajero", example = "ABCCAP" )
    private String terminalId;

    @ApiModelProperty( value = "Dispositivos del cajero", example = "" )
    private DevicesWrapper terminalDevices;

    //Cambio release/algoritmos
    @ApiModelProperty( value = "Tipo de algoritmo de dispensado", example = "UMBRALES" )
    private DispensedAlgorithm dispensedAlgorithm;
    //Cambio release/algoritmos

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public DevicesWrapper getTerminalDevices() {
        return terminalDevices;
    }

    public void setTerminalDevices( DevicesWrapper terminalDevices ) {
        this.terminalDevices = terminalDevices;
    }

    //Cambio release/algoritmos
    public DispensedAlgorithm getDispensedAlgorithm() {
        return dispensedAlgorithm;
    }

    public void setDispensedAlgorithm( DispensedAlgorithm dispensedAlgorithm ) {
        this.dispensedAlgorithm = dispensedAlgorithm;
    }
    //Cambio release/algoritmos

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
