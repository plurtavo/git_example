package mx.com.prosa.nabhi.misc.domain.algorithm.dto;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel( description = "Json DTO representación de Algoritmo de dispensado" )
public class DispensedAlgorithm implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "ID del algoritmo", example = "1" )
    private int id;
    @ApiModelProperty( value = "Nombre del algoritmo", example = "Umbrales para 30Xa" )
    private String name;
    @ApiModelProperty( value = "Tipo de algoritmo", example = "Umbrales" )
    private String dispensedType;
    @ApiModelProperty( value = "Numero limite de billetes para la denominación de 20 pesos", example = "4" )
    private int notesLimit20;
    @ApiModelProperty( value = "Monto mínimo para entrar en el algoritmo para la denominación de 20 pesos", example = "20" )
    private int minimumAmount20;
    @ApiModelProperty( value = "Numero limite de billetes para la denominación de 50 pesos", example = "4" )
    private int notesLimit50;
    @ApiModelProperty( value = "Monto mínimo para entrar en el algoritmo para la denominación de 50 pesos", example = "20" )
    private int minimumAmount50;
    @ApiModelProperty( value = "Numero limite de billetes para la denominación de 100 pesos", example = "4" )
    private int notesLimit100;
    @ApiModelProperty( value = "Monto mínimo para entrar en el algoritmo para la denominación de 100 pesos", example = "20" )
    private int minimumAmount100;
    @ApiModelProperty( value = "Numero limite de billetes para la denominación de 200 pesos", example = "4" )
    private int notesLimit200;
    @ApiModelProperty( value = "Monto mínimo para entrar en el algoritmo para la denominación de 200 pesos", example = "20" )
    private int minimumAmount200;
    @ApiModelProperty( value = "Numero limite de billetes para la denominación de 500 pesos", example = "4" )
    private int notesLimit500;
    @ApiModelProperty( value = "Monto mínimo para entrar en el algoritmo para la denominación de 500 pesos", example = "20" )
    private int minimumAmount500;
    @ApiModelProperty( value = "Numero limite de billetes para la denominación de 1000 pesos", example = "4" )
    private int notesLimit1000;
    @ApiModelProperty( value = "Monto mínimo para entrar en el algoritmo para la denominación de 1000 pesos", example = "20" )
    private int minimumAmount1000;
    @ApiModelProperty( value = "IDF dueña del grupo", example = "B000" )
    private IDF idf;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getDispensedType() {
        return dispensedType;
    }

    public void setDispensedType( String dispensedType ) {
        this.dispensedType = dispensedType;
    }

    public int getNotesLimit20() {
        return notesLimit20;
    }

    public void setNotesLimit20( int notesLimit20 ) {
        this.notesLimit20 = notesLimit20;
    }

    public int getMinimumAmount20() {
        return minimumAmount20;
    }

    public void setMinimumAmount20( int minimumAmount20 ) {
        this.minimumAmount20 = minimumAmount20;
    }

    public int getNotesLimit50() {
        return notesLimit50;
    }

    public void setNotesLimit50( int notesLimit50 ) {
        this.notesLimit50 = notesLimit50;
    }

    public int getMinimumAmount50() {
        return minimumAmount50;
    }

    public void setMinimumAmount50( int minimumAmount50 ) {
        this.minimumAmount50 = minimumAmount50;
    }

    public int getNotesLimit100() {
        return notesLimit100;
    }

    public void setNotesLimit100( int notesLimit100 ) {
        this.notesLimit100 = notesLimit100;
    }

    public int getMinimumAmount100() {
        return minimumAmount100;
    }

    public void setMinimumAmount100( int minimumAmount100 ) {
        this.minimumAmount100 = minimumAmount100;
    }

    public int getNotesLimit200() {
        return notesLimit200;
    }

    public void setNotesLimit200( int notesLimit200 ) {
        this.notesLimit200 = notesLimit200;
    }

    public int getMinimumAmount200() {
        return minimumAmount200;
    }

    public void setMinimumAmount200( int minimumAmount200 ) {
        this.minimumAmount200 = minimumAmount200;
    }

    public int getNotesLimit500() {
        return notesLimit500;
    }

    public void setNotesLimit500( int notesLimit500 ) {
        this.notesLimit500 = notesLimit500;
    }

    public int getMinimumAmount500() {
        return minimumAmount500;
    }

    public void setMinimumAmount500( int minimumAmount500 ) {
        this.minimumAmount500 = minimumAmount500;
    }

    public int getNotesLimit1000() {
        return notesLimit1000;
    }

    public void setNotesLimit1000( int notesLimit1000 ) {
        this.notesLimit1000 = notesLimit1000;
    }

    public int getMinimumAmount1000() {
        return minimumAmount1000;
    }

    public void setMinimumAmount1000( int minimumAmount1000 ) {
        this.minimumAmount1000 = minimumAmount1000;
    }

    public IDF getIdf() {
        return idf;
    }

    public void setIdf( IDF idf ) {
        this.idf = idf;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
