package mx.com.prosa.nabhi.iso.core.builder;

import mx.com.prosa.nabhi.iso.core.builder.token.TokenBuilder;
import mx.com.prosa.nabhi.iso.core.builder.token.TokenID;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.iso.ISO8583Exception;
import mx.com.prosa.nabhi.misc.exception.rest.RestExchangeException;
import mx.com.prosa.nabhi.misc.model.iso.ATMRequestModel;
import mx.com.prosa.nabhi.misc.model.jke.KeyRequest;
import mx.com.prosa.nabhi.misc.rest.jke.JKERequester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.gonet.iso8583.constants.atm.TranCodes;
import us.gonet.token.Token;
import us.gonet.token.Tokens;
import us.gonet.token.emv.TokenEmv;
import us.gonet.token.emv.model.TokenB4;
import us.gonet.utils.Utilities;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ISOBuilder {

    private final TokenBuilder tokenBuilder;
    private final JKERequester jkeRequester;
    private static final Logger LOG = LoggerFactory.getLogger( ISOBuilder.class );

    @Autowired
    public ISOBuilder( TokenBuilder tokenBuilder, JKERequester jkeRequester ) {
        this.tokenBuilder = tokenBuilder;
        this.jkeRequester = jkeRequester;
    }

    Map < String, String > buildDataElement( TranCodes tranCodes, ATMRequestModel atm, int traceNumber, String exchangeNode, String ipk ) throws ISO8583Exception {
        Map < String, String > dataElements = new LinkedHashMap <>();
        try {
            atm.setPinBlock( jkeRequester.translatePinBlock( buildPinBlockExchange( atm, exchangeNode, false, ipk ), "/traslate/{nodeName}", exchangeNode ) );
            if ( tranCodes == TranCodes.PIN_CHANGE ) {
                atm.setNewPinBlock( jkeRequester.translatePinBlock( buildPinBlockExchange( atm, exchangeNode, true, ipk ), "/traslate/{nodeName}", exchangeNode ) );
                atm.setNewPinBlock2( atm.getNewPinBlock() );
            }
        } catch ( RestExchangeException e ) {
            throw new ISO8583Exception( CatalogError.JKE_ERROR, String.format( " Causa: %s", e.getError().getMessage() ) );
        }
        if ( tranCodes == TranCodes.PAYMENT_SERVICE ) {
            tranCodes = TranCodes.WITHDRAWAL;
        }

        dataElements.put( "p3", tranCodes.getValue() + atm.getFromAccount() + atm.getToAccount() );
        DecimalFormat f = new DecimalFormat( "0.00" );
        float amount = Float.parseFloat( atm.getAmount() );
        dataElements.put( "p4", f.format( amount ) );
        dataElements.put( "p11", Utilities.leftPadding( "0", 6, String.valueOf( traceNumber ) ) );
        dataElements.put( "p22", atm.getEntryMode() );
        dataElements.put( "p28", atm.getSurcharge() );
        dataElements.put( "p32", atm.getAcquiringId() );
        dataElements.put( "p35", atm.getTrack2() );
        dataElements.put( "p37", atm.getSequenceNumber() );
        dataElements.put( "p41", atm.getTermId() );
        dataElements.put( "p43", atm.getTermOwnerName() + atm.getTermCity() + atm.getTermState() + atm.getTermCountry() );
        dataElements.put( "p48", atm.getGroupAllow() );
        dataElements.put( "p49", atm.getCurrencyCode() );
        dataElements.put( "p52", atm.getPinBlock() );
        dataElements.put( "p60", atm.getTermFiid() + atm.getlNet() + atm.getTimeOffSet() );
        if ( tranCodes == TranCodes.PAYMENT_ACCOUNTS || tranCodes == TranCodes.CARDHOLDER_ACCOUNTS_TRANSFER ) {
            dataElements.put( "s103", atm.getToAccountP103() );
        }
        return dataElements;
    }

    Map < String, String > addTokens( Map < String, String > dataElements, Tokens tokens, ATMRequestModel atmRequestModel, List < TokenID > ids ) throws ISO8583Exception {
        if ( tokens == null ) {
            tokens = new Tokens();
        }
        final String ON_US = "onUsFlag";
        String onUsFlag = "1";
        if ( !atmRequestModel.getExtra().get( ON_US ).isEmpty() ) {
            onUsFlag = atmRequestModel.getExtra().get( ON_US );
        }
        Token token;
        for ( TokenID id : ids ) {
            switch ( id ) {
                case TOKEN_B2:
                    token = new Token();
                    token.setId( "B2" );
                    token.add( 0, new TokenEmv( atmRequestModel.getEmv() ).getTokenB2() );
                    break;
                case TOKEN_B3:
                    token = new Token();
                    token.setId( "B3" );
                    token.add( 0, new TokenEmv( atmRequestModel.getEmv() ).getTokenB3() );
                    break;
                case TOKEN_B4:
                    token = new Token();
                    token.setId( "B4" );
                    TokenB4 tokenEmv = new TokenB4( atmRequestModel.getEmv() );
                    token.add(0, tokenEmv.getTokenB4() );
                    break;
                case TOKEN_06:
                    token = tokenBuilder.build06( atmRequestModel );
                    break;
                case TOKEN_P1:
                    token = tokenBuilder.buildP1( atmRequestModel );
                    break;
                case TOKEN_QV:
                    token = new Token();
                    token.setId( "QV" );
                    token.add( 0, String.format( "%-4s", CompanyMobile.valueForName( atmRequestModel.getCompany() ) ) );
                    token.add( 1, String.format( "%-15s", atmRequestModel.getPhoneNumber() ) );
                    token.add( 2, String.format( "%-16s", "" ) );
                    token.add( 3, String.format( "%-12s", "" ) );
                    token.add( 4, String.format( "%-1s", "" ) );
                    token.add( 5, String.format( "%-1s", onUsFlag ) );
                    token.add( 6, String.format( "%-4s", atmRequestModel.getTermFiid() ) );
                    token.add( 7, String.format( "%-16s", "" ) );
                    token.add( 8, String.format( "%-2s", "" ) );
                    token.add( 9, String.format( "%-1s", "" ) );
                    token.add( 10, String.format( "%-1s", "" ) );
                    token.add( 11, String.format( "%-1s", "" ) );
                    break;
                default:
                    if ( LOG.isDebugEnabled() ){
                        LOG.debug( String.format( "El token con id %s no es soportado", id.name() ) );
                    }
                    throw new ISO8583Exception( CatalogError.TOKEN_ID_NO_EXIST, id.name() );
            }
            tokens.add( token.getId(), token );
        }
        dataElements.put( "s126", tokens.toString() );
        return dataElements;
    }

    private KeyRequest buildPinBlockExchange( ATMRequestModel atm, String exchangeNode, boolean nipChange, String ipk ) throws ISO8583Exception {
        if ( nipChange && !atm.getNewPinBlock().equals( atm.getNewPinBlock2() ) ) {
            throw new ISO8583Exception( CatalogError.NIP_INVALID );
        }
        KeyRequest keyRequest = new KeyRequest();
        keyRequest.setAtmLocal( exchangeNode );
        keyRequest.setAtmRemote( atm.getTermId() );
        if ( nipChange ){
            keyRequest.setPinBlock( atm.getNewPinBlock() );
        }else {
            keyRequest.setPinBlock( atm.getPinBlock() );
        }
        keyRequest.setTrack2( atm.getTrack2() );
        keyRequest.setTermType( atm.getTermType() );
        keyRequest.setIpk( ipk );
        return keyRequest;
    }

    private enum CompanyMobile {
        UNEFON  ( "UNEF" ),
        MOVISTAR( "PEGA" ),
        TELCEL  ( "TELC" ),
        ATT     ( "IUSC" ),
        INVALID ( "INVL" );

        private final String value;

        CompanyMobile( String value ) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        static String valueForName( String name ){
            for ( CompanyMobile mobile : CompanyMobile.values() ){
                if ( mobile.name().equals( name ) ){
                    return mobile.getValue();
                }
            }
            return INVALID.getValue();
        }
    }

}
