package mx.com.prosa.nabhi.iso.core.builder.token;

import us.gonet.token.Token;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TokenPSBuilder implements ITokenBuilder {

    @Size( min = 2, max = 2, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String broker;
    @Size( min = 3, max = 3, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String serviceId;
    @Size( max = 3, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String productId;
    @Size( min = 1, max = 1, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String entryMode;
    @Size( min = 20, max = 20, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String reference;
    @Size( max = 20, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String description;
    @Size( min = 19, max = 19, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String amount;
    @Size( min = 19, max = 19, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String amount2;
    @Size( min = 1, max = 1, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String state;
    @Size( min = 6, max = 6, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String authB24;
    @Size( min = 20, max = 20, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String authBroker;
    @Size( min = 15, max = 15, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String userFld3;

    public TokenPSBuilder withBroker( String broker ) {
        this.broker = broker;
        return this;
    }

    public TokenPSBuilder withServiceId( String serviceId ) {
        this.serviceId = serviceId;
        return this;
    }

    public TokenPSBuilder withProductId( String productId ) {
        this.productId = productId;
        return this;
    }

    public TokenPSBuilder withEntryMode( String entryMode ) {
        this.entryMode = entryMode;
        return this;
    }

    public TokenPSBuilder withReference( String reference ) {
        this.reference = reference;
        return this;
    }

    public TokenPSBuilder withDescription( String description ) {
        this.description = description;
        return this;
    }

    public TokenPSBuilder withAmount( String amount ) {
        this.amount = amount;
        return this;
    }

    public TokenPSBuilder withAmount2( String amount2 ) {
        this.amount2 = amount2;
        return this;
    }

    public TokenPSBuilder withState( String state ) {
        this.state = state;
        return this;
    }

    public TokenPSBuilder withAuthB24( String authB24 ) {
        this.authB24 = authB24;
        return this;
    }

    public TokenPSBuilder withAuthBroker( String authBroker ) {
        this.authBroker = authBroker;
        return this;
    }

    public TokenPSBuilder withUserFld3( String userFld3 ) {
        this.userFld3 = userFld3;
        return this;
    }

    @Override
    public Token build() {
        Token tokenPs = new Token();
        tokenPs.setId( "PS" );
        tokenPs.add( 0, broker );
        tokenPs.add( 1, serviceId );
        tokenPs.add( 2, productId != null ? productId : "   " );
        tokenPs.add( 3, entryMode );
        tokenPs.add( 4, reference );
        tokenPs.add( 5, description != null ? description : "                    " );
        tokenPs.add( 6, amount );
        tokenPs.add( 7, amount2 != null ? amount2 : "                   " );
        tokenPs.add( 8, state );
        tokenPs.add( 9, authB24 != null ? authB24 : "      " );
        tokenPs.add( 10, authBroker != null ? authBroker : "                    " );
        tokenPs.add( 11, userFld3 != null ? userFld3 : "               " );
        return tokenPs;
    }


}
