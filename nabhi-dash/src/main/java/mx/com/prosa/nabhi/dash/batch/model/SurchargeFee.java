package mx.com.prosa.nabhi.dash.batch.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.GsonBuilder;


@JsonInclude( JsonInclude.Include.NON_NULL )
@JsonPropertyOrder( {
        "código de transacción",
        "id institución adquiriente",
        "id institución emisor",
        "monto comisión",


} )
public class SurchargeFee {

    @JsonProperty( required = true, value = "código de transacción" )
    private String tranCode;
    @JsonProperty( required = true, value = "id institución adquiriente" )
    private String fiidAcquirer;
    @JsonProperty( required = true, value = "id institución emisor" )
    private String fiidIssuing;
    @JsonProperty( required = true, value = "monto comisión" )
    private String surcharges;

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode( String tranCode ) {
        this.tranCode = tranCode;
    }

    public String getFiidAcquirer() {
        return fiidAcquirer;
    }

    public void setFiidAcquirer( String fiidAcquirer ) {
        this.fiidAcquirer = fiidAcquirer;
    }

    public String getFiidIssuing() {
        return fiidIssuing;
    }

    public void setFiidIssuing( String fiidIssuing ) {
        this.fiidIssuing = fiidIssuing;
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