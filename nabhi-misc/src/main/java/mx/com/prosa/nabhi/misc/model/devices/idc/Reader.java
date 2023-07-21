package mx.com.prosa.nabhi.misc.model.devices.idc;
//Cambio release/redcat: Cambio de paquete

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.devices.IDevice;
import mx.com.prosa.nabhi.misc.model.devices.constants.idc.*;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación del dispositivo de lector de tarjetas del cajero" )
public class Reader implements IDevice, Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Indica si la lectora esta disponible o no", example = "0" )
    private int status;
    @ApiModelProperty( value = "Estado del modulo antifraude de la lectora", example = "DEVICEDETECTED" )
    private AntiFraudModule fraudModule;
    @ApiModelProperty( value = "Estado del lector de chip de la lectora", example = "INOP" )
    private ChipModule chipModule;
    @ApiModelProperty( value = "Indica el error que ocurrio en la lectora (Si existe alguno)", example = "INVALIDMEDIA" )
    private IDCError error;
    @ApiModelProperty( value = "Estado del retractor de tarjetas", example = "BINOK" )
    private RetainBin retainBin;
    @ApiModelProperty( value = "Indica el estado de la lectora según el estandar XFS", example = "ONLINE" )
    private IDCDeviceState deviceState;

    @Override
    public int getStatus() {
        return status;
    }

    public void setStatus( int status ) {
        this.status = status;
    }

    public String getFraudModule() {
        return fraudModule.name();
    }

    public void setFraudModule( String fraudModule ) {
        this.fraudModule = AntiFraudModule.valueOfCompose( fraudModule );
    }

    public String getChipModule() {
        return chipModule.name();
    }

    public void setChipModule( String chipModule ) {
        this.chipModule = ChipModule.valueOfCompose( chipModule );
    }

    public String getError() {
        return error.name();
    }

    public void setError( String error ) {
        this.error = IDCError.valueOfCompose( error );
    }

    public String getRetainBin() {
        return retainBin.name();
    }

    public void setRetainBin( String retainBin ) {
        this.retainBin = RetainBin.valueOfCompose( retainBin );
    }


    public String getDeviceState() {
        return deviceState.name();
    }

    public void setDeviceState( String deviceState ) {
        this.deviceState = IDCDeviceState.valueOfCompose( deviceState );
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
