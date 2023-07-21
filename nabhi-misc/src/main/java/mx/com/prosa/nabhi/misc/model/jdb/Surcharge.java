package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.model.jdb.composite.SurchargeId;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de una comisión" )
public class Surcharge implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Tipo de comisión según código de transacción, IDF adquiriente e IDF emisor", example = "09" )
    private SurchargeId surchargeId;

    @ApiModelProperty( value = "Valor de la comisión", example = "20.00" )
    private String surcharges;

    public SurchargeId getSurchargeId() {
        return surchargeId;
    }

    public void setSurchargeId( SurchargeId surchargeId ) {
        this.surchargeId = surchargeId;
    }

    public String getSurcharges() {
        return surcharges;
    }

    public void setSurcharges( String surcharges ) {
        this.surcharges = surcharges;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
