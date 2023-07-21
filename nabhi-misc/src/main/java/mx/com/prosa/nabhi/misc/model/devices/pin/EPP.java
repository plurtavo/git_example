package mx.com.prosa.nabhi.misc.model.devices.pin;
//Cambio release/redcat: Cambio de paquete

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.devices.IDevice;
import mx.com.prosa.nabhi.misc.model.devices.constants.pin.*;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación del dispositivo pinpad del cajero" )
public class EPP implements IDevice, Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Indica si el modulo pinpad se encuentra disponible", example = "1" )
    private int status;
    @ApiModelProperty( value = "Estado del modulo antifraude del pinpad", example = "AFMOK" )
    private AntiFraudModule fraudModule;
    @ApiModelProperty( value = "Indica la posición del pinpad", example = "INPOSITION" )
    private DevicePosition devicePosition;
    @ApiModelProperty( value = "Indica el estado del pinpad según el estandar XFS", example = "ONLINE" )
    private DeviceState deviceState;
    @ApiModelProperty( value = "Estado del modulo encriptador del pinpad", example = "ENCREADY" )
    private EncStat encStat;
    @ApiModelProperty( value = "Indica el error ocurrido en el pinpad en caso de haber uno", example = "KEY_NOT_FOUND" )
    private PINError error;

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

    public String getDevicePosition() {
        return devicePosition.name();
    }

    public void setDevicePosition( String devicePosition ) {
        this.devicePosition = DevicePosition.valueOfCompose( devicePosition );
    }

    public String getDeviceState() {
        return deviceState.name();
    }

    public void setDeviceState( String deviceState ) {
        this.deviceState = DeviceState.valueOf( deviceState );
    }

    public String getEncStat() {
        return encStat.name();
    }

    public void setEncStat( String encStat ) {
        this.encStat = EncStat.valueOfCompose( encStat );
    }

    public String getError() {
        return error.name();
    }

    public void setError( String error ) {
        this.error = PINError.valueOfCompose( error );
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
