package mx.com.prosa.nabhi.misc.model.devices.cdm;
//Cambio release/redcat: Cambio de paquete

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.devices.IDevice;
import mx.com.prosa.nabhi.misc.model.devices.constants.cdm.CDMDeviceState;
import mx.com.prosa.nabhi.misc.model.devices.constants.cdm.CDMError;
import mx.com.prosa.nabhi.misc.model.devices.constants.cdm.DispenserStatus;
import mx.com.prosa.nabhi.misc.model.devices.constants.cdm.Shutter;

import java.io.Serializable;
import java.util.List;

@ApiModel( description = "Json DTO representación del dispositivo dispensador del cajero" )
public class Dispenser implements IDevice, Serializable {

    private static final long serialVersionUID = 23453246534L;
    @ApiModelProperty( value = "Indica si el dispensador se encuentra operando", example = "1 " )
    private int status;
    @ApiModelProperty( value = "Lista de caseteras del dispensador", example = "" )
    private List < Cassette > cassettes;
    @ApiModelProperty( value = "Estado del dispositivo según el estandar XFS", example = "HWERROR" )
    private CDMDeviceState deviceState;
    @ApiModelProperty( value = "Indica el mensaje de error según el estandar XFS dentro del dispensador en caso de haber uno", example = "INVALID_CURRENCY" )
    private CDMError error;
    @ApiModelProperty( value = "Indica el estado del dispensador según el estandar XFS", example = "CUSTATE" )
    private DispenserStatus dispenserStatus;
    @ApiModelProperty( value = "Indica el estado del shutter según el estandar XFS", example = "CLOSED" )
    private Shutter shutter;

    @Override
    public int getStatus() {
        return status;
    }

    public void setStatus( int status ) {
        this.status = status;
    }

    public List < Cassette > getCassettes() {
        return cassettes;
    }

    public void setCassettes( List < Cassette > cassettes ) {
        this.cassettes = cassettes;
    }

    public String getDeviceState() {
        return deviceState.name();
    }

    public void setDeviceState( String deviceState ) {
        this.deviceState = CDMDeviceState.valueOfCompose( deviceState );
    }

    public String getError() {
        return error.name();
    }

    public void setError( String error ) {
        this.error = CDMError.valueOfCompose( error );
    }

    public String getDispenserStatus() {
        return dispenserStatus.name();
    }

    public void setDispenserStatus( String dispenserStatus ) {
        this.dispenserStatus = DispenserStatus.valueOfCompose( dispenserStatus );
    }

    public String getShutter() {
        return shutter.name();
    }

    public void setShutter( String shutter ) {
        this.shutter = Shutter.valueOfCompose( shutter );
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
