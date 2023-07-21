package mx.com.prosa.nabhi.iso.core.builder.token;

import us.gonet.token.Token;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Token06Builder implements ITokenBuilder {

    @Size( min = 1, max = 1, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String newPinFormat;
    @Size( min = 16, max = 16, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String newPinOffSet;
    @Size( min = 1, max = 1, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String pinCount;
    @Size( min = 2, max = 2, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String newPinSize;
    @Size( min = 16, max = 16, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String newPin1;
    @Size( min = 16, max = 16, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String newPin2;

    public Token06Builder withNewPinFomat( String newPinFormat ) {
        this.newPinFormat = newPinFormat;
        return this;
    }

    public Token06Builder withNewPinOffSet( String newPinOffSet ) {
        this.newPinOffSet = newPinOffSet;
        return this;
    }

    public Token06Builder withPinCount( String pinCount ) {
        this.pinCount = pinCount;
        return this;
    }

    public Token06Builder withPinSize( String newPinSize ) {
        this.newPinSize = newPinSize;
        return this;
    }

    public Token06Builder withPin1( String newPin1 ) {
        this.newPin1 = newPin1;
        return this;
    }

    public Token06Builder withPin2( String newPin2 ) {
        this.newPin2 = newPin2;
        return this;
    }

    @Override
    public Token build() {
        Token token06 = new Token();
        token06.setId( "06" );
        token06.add( 0, newPinFormat );
        token06.add( 1, newPinOffSet );
        token06.add( 2, pinCount );
        token06.add( 3, newPinSize );
        token06.add( 4, newPin1 );
        token06.add( 5, newPin2 );
        return token06;
    }


}
