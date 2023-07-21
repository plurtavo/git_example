package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.jdb.composite.UpTimeId;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de las conexiones del cajero con el servidor " )
public class UpTimes implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "ID de conexión del cajero", example = "" )
    private UpTimeId upTimeId;
    @ApiModelProperty( value = "Tiempo total del cajero en línea", example = "24697" )
    private int upTime;

    public UpTimeId getUpTimeIdentity() {
        return upTimeId;
    }

    public void setUpTimeIdentity( UpTimeId upTimeId ) {
        this.upTimeId = upTimeId;
    }

    public int getUpTime() {
        return upTime;
    }

    public void setUpTime( int upTime ) {
        this.upTime = upTime;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
