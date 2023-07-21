package mx.com.prosa.nabhi.iso.core.builder.token;

import us.gonet.token.Token;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TokenP1Builder implements ITokenBuilder {

    @Size( min = 4, max = 4, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String companyCode;
    @Size( min = 30, max = 30, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String company;


    public TokenP1Builder withCompanyCode( String companyCode ) {
        this.companyCode = companyCode;
        return this;
    }

    public TokenP1Builder withCompany( String company ) {
        this.company = company;
        return this;
    }

    @Override
    public Token build() {
        Token tokenP1 = new Token();
        tokenP1.setId( "P1" );
        tokenP1.add( 0, companyCode );
        tokenP1.add( 1, company );
        return tokenP1;
    }


}
