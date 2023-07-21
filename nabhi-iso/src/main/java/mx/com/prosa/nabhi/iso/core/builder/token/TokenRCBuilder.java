package mx.com.prosa.nabhi.iso.core.builder.token;

import us.gonet.token.Token;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TokenRCBuilder implements ITokenBuilder {

    @Size( min = 4, max = 4, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String idService;
    @Size( min = 15, max = 15, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String serviceDesc;
    @Size( min = 2, max = 2, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String pasTran;
    @Size( min = 32, max = 32, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String reference;
    @Size( min = 12, max = 12, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String amount;
    @Size( min = 12, max = 12, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String comAdq;
    @Size( min = 12, max = 12, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String comService;
    @Size( min = 12, max = 12, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String totalAmount;
    @Size( min = 12, max = 12, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String oldBalance;
    @Size( min = 12, max = 12, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String newBalance;
    @Size( min = 12, max = 12, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String authCode;
    @Size( min = 2, max = 2, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String respCode;
    @Size( min = 32, max = 32, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String error;
    @Size( min = 20, max = 20, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String invoice;
    @Size( min = 12, max = 12, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String sequence;
    @Size( min = 20, max = 20, message = "invalid length" )
    @NotNull( message = "Invalid Data" )
    private String location;



    TokenRCBuilder withIdService( String idService ) {
        this.idService = idService;
        return this;
    }

    TokenRCBuilder withServiceDesc( String serviceDesc ) {
        this.serviceDesc = serviceDesc;
        return this;
    }

    TokenRCBuilder withPasTran( String pasTran ) {
        this.pasTran = pasTran;
        return this;
    }

    TokenRCBuilder withReference( String reference ) {
        this.reference = reference;
        return this;
    }

    TokenRCBuilder withAmount( String amount ) {
        this.amount = amount;
        return this;
    }

    TokenRCBuilder withComAdq( String comAdq ) {
        this.comAdq = comAdq;
        return this;
    }

    TokenRCBuilder withComService( String comService ) {
        this.comService = comService;
        return this;
    }

    TokenRCBuilder withTotalAmount( String totalAmount ) {
        this.totalAmount = totalAmount;
        return this;
    }

    TokenRCBuilder withOldBalance( String oldBalance ) {
        this.oldBalance = oldBalance;
        return this;
    }

    TokenRCBuilder withNewBalance( String newBalance ) {
        this.newBalance = newBalance;
        return this;
    }

    TokenRCBuilder withAuthCode( String authCode ) {
        this.authCode = authCode;
        return this;
    }

    TokenRCBuilder withRespCode( String respCode ) {
        this.respCode = respCode;
        return this;
    }

    TokenRCBuilder withError( String error ) {
        this.error = error;
        return this;
    }

    TokenRCBuilder withInvoice( String invoice ) {
        this.invoice = invoice;
        return this;
    }

    TokenRCBuilder withSequence( String sequence ) {
        this.sequence = sequence;
        return this;
    }

    TokenRCBuilder withLocation( String location ) {
        this.location = location;
        return this;
    }

    @Override
    public Token build() {
        Token tokenRC = new Token();
        tokenRC.setId( "RC" );
        tokenRC.add( 0, idService );
        tokenRC.add( 1, serviceDesc );
        tokenRC.add( 2, pasTran );
        tokenRC.add( 3, reference );
        tokenRC.add( 4, amount );
        tokenRC.add( 5, comAdq );
        tokenRC.add( 6, comService );
        tokenRC.add( 7, totalAmount );
        tokenRC.add( 8, oldBalance );
        tokenRC.add( 9, newBalance );
        tokenRC.add( 10, authCode );
        tokenRC.add( 11, respCode );
        tokenRC.add( 12, error );
        tokenRC.add( 12, invoice );
        tokenRC.add( 14, sequence );
        tokenRC.add( 15, location );
        return tokenRC;
    }


}
