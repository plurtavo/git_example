package mx.com.prosa.nabhi.iso.core.builder.token;

import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.iso.ISO8583Exception;
import mx.com.prosa.nabhi.misc.model.iso.ATMRequestModel;
import org.springframework.stereotype.Component;
import us.gonet.token.Token;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class TokenBuilder {


    public Token build06( ATMRequestModel atm ) throws ISO8583Exception {
        Token06Builder token06Builder = new Token06Builder();
        token06Builder
                .withNewPinFomat( "1" )
                .withNewPinOffSet( "0000000000000000" )
                .withPinCount( "2" )
                .withPinSize( String.format( "%02d", atm.getNewPinBlock().length() ) )
                .withPin1( atm.getNewPinBlock() )
                .withPin2( atm.getNewPinBlock2() );
        return validateToken( token06Builder );
    }

    public Token buildP1( ATMRequestModel atm ) throws ISO8583Exception {
        TokenP1Builder tokenP1Builder = new TokenP1Builder();
        tokenP1Builder.withCompanyCode( "0000" )
                .withCompany( String.format( "%-30s", atm.getCompany() ) );
        return validateToken( tokenP1Builder );
    }

    private Token validateToken( ITokenBuilder token ) throws ISO8583Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set < ConstraintViolation < ITokenBuilder > > violations = validator.validate( token );
        if ( violations.isEmpty() ) {
            return token.build();
        } else {
            String lastError = "";
            for ( ConstraintViolation < ITokenBuilder > violation : violations ) {
                lastError = violation.getMessage();
            }
            throw new ISO8583Exception( CatalogError.TOKEN_BUILD_ERROR, lastError  );
        }
    }

}
