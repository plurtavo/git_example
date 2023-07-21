package mx.com.prosa.nabhi.misc.model.devices.cdm;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
//Cambio release/redcat: Cambio de paquete

@ApiModel( description = "Json DTO representación de una casetera lógica" )
public class Logical implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Cantidad de dinero incrementadas durante el día", example = "50000" )
    private int increment;
    @ApiModelProperty( value = "Cantidad todal de dinero dispensado durante el día", example = "12500" )
    private int totalDispensed;
    @ApiModelProperty( value = "Cuenta de dinero inicial del día", example = "45000" )
    private int initialDayCount;
    @ApiModelProperty( value = "Cuenta de dinero al final del día", example = "31000" )
    private int endDayCount;

    public Logical() {
    }

    public Logical( int def ) {
        this.increment = def;
        this.totalDispensed = def;
        this.initialDayCount = def;
        this.endDayCount = def;
    }

    public int getIncrement() {
        return increment;
    }

    public void setIncrement( int increment ) {
        this.increment = increment;
    }

    public int getTotalDispensed() {
        return totalDispensed;
    }

    public void setTotalDispensed( int totalDispensed ) {
        this.totalDispensed = totalDispensed;
    }

    public int getInitialDayCount() {
        return initialDayCount;
    }

    public void setInitialDayCount( int initialDayCount ) {
        this.initialDayCount = initialDayCount;
    }

    public int getEndDayCount() {
        return endDayCount;
    }

    public void setEndDayCount( int endDayCount ) {
        this.endDayCount = endDayCount;
    }


    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}

