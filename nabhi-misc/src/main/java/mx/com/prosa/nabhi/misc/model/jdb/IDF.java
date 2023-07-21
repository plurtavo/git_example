package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel( description = "Json DTO representación de un IDF" )
public class IDF implements Serializable {

    @ApiModelProperty( value = "ID del FIID", example = "B000" )
    private String fiid;
    @ApiModelProperty( value = "Red lógica por la cual viaja la transacción en Base24", example = "PROS1" )
    private String logicalNet;
    @ApiModelProperty( value = "Nombre de la institución", example = "Prosa" )
    private String name;
    @ApiModelProperty( value = "ID adquiriente", example = "00000000001" )
    private String acquiringId;
    @ApiModelProperty( value = "Nombre completo de la institución ", example = "Promocion y Operacion S.A. de C.V." )
    private String nameLong;
    @ApiModelProperty( value = "Fecha de inicio de corte actual", example = "2020-05-19" )
    private String currentBusinessDay;
    @ApiModelProperty( value = "Fecha de fin de corte actual", example = "2020-06-19" )
    private String nextBusinessDay;
    @ApiModelProperty( value = "Hora de corte", example = "22:00" )
    private String forcedCutOver;
    @ApiModelProperty( value = "Muestra los acuerdos con otras instituciones", example = "B001, B002" )
    private String agreement;
    @ApiModelProperty( value = "Pais de origen de la institución según el estandar ISO 3166-1", example = "484 (Mexico)" )
    private Country country;
    @ApiModelProperty( value = "Lista de comisiones asociadas a esta institución", example = "" )
    private List < Surcharge > surcharges;
    @ApiModelProperty( value = "GROUP Adquiriente de la institución", example = "" )
    private List < APC > apcs;
    @ApiModelProperty( value = "Bines pertenecientes a la institución", example = "123456" )
    private List < Prefix > prefixes;

    public IDF( String fiid ) {
        this.fiid = fiid;
    }

    public IDF() {
    }

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }

    public String getLogicalNet() {
        return logicalNet;
    }

    public void setLogicalNet( String logicalNet ) {
        this.logicalNet = logicalNet;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getAcquiringId() {
        return acquiringId;
    }

    public void setAcquiringId( String acquiringId ) {
        this.acquiringId = acquiringId;
    }

    public String getNameLong() {
        return nameLong;
    }

    public void setNameLong( String nameLong ) {
        this.nameLong = nameLong;
    }

    public String getCurrentBusinessDay() {
        return currentBusinessDay;
    }

    public void setCurrentBusinessDay( String currentBusinessDay ) {
        this.currentBusinessDay = currentBusinessDay;
    }

    public String getNextBusinessDay() {
        return nextBusinessDay;
    }

    public void setNextBusinessDay( String nextBusinessDay ) {
        this.nextBusinessDay = nextBusinessDay;
    }

    public String getForcedCutOver() {
        return forcedCutOver;
    }

    public void setForcedCutOver( String forcedCutOver ) {
        this.forcedCutOver = forcedCutOver;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement( String agreement ) {
        this.agreement = agreement;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry( Country country ) {
        this.country = country;
    }

    public List < Surcharge > getSurcharges() {
        return surcharges;
    }

    public void setSurcharges( List < Surcharge > surcharges ) {
        this.surcharges = surcharges;
    }

    public List < APC > getApcs() {
        return apcs;
    }

    public void setApcs( List < APC > apcs ) {
        this.apcs = apcs;
    }

    public List < Prefix > getBins() {
        return prefixes;
    }

    public void setBins( List < Prefix > prefixes ) {
        this.prefixes = prefixes;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }

}
