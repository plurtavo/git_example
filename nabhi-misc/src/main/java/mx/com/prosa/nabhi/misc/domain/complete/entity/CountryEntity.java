package mx.com.prosa.nabhi.misc.domain.complete.entity;

import com.google.gson.GsonBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table( name = "TBL_COUNTRY" )
public class CountryEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;
    @Id
    @Column( length = 3, name = "PK_COUNTRY_CODE" )
    private String countryCode;

    @Column( length = 2, name = "ALPHA_2" )
    private String alpha2;

    @Column( length = 3, name = "ALPHA_3" )
    private String alpha3;

    @Column( length = 20, name = "NAME" )
    private String name;

    @Column( length = 1, name = "SYMBOLS" )
    private String symbols;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode( String countryCode ) {
        this.countryCode = countryCode;
    }

    public String getAlpha2() {
        return alpha2;
    }

    public void setAlpha2( String alpha2 ) {
        this.alpha2 = alpha2;
    }

    public String getAlpha3() {
        return alpha3;
    }

    public void setAlpha3( String alpha3 ) {
        this.alpha3 = alpha3;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getSymbols() {
        return symbols;
    }

    public void setSymbols( String symbols ) {
        this.symbols = symbols;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
