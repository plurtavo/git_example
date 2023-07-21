package mx.com.prosa.nabhi.misc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.gonet.iso8583.constants.MessageTypes;
import us.gonet.iso8583.constants.NetworkManagement;
import us.gonet.iso8583.constants.Product;
import us.gonet.iso8583.constants.atm.ResponseCodes;
import us.gonet.iso8583.constants.atm.TranCodes;
import us.gonet.iso8583.message.Request0800;
import us.gonet.serializable.data.ISO;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class ISOUtils {

    private static final Logger LOG = LoggerFactory.getLogger( ISOUtils.class );

    public boolean isRed( ISO iso ) {
        Product red = Product.NMM_PRODUCT;
        return red.getValue().equals( iso.getHeader().get( 1 ).getContentField() );
    }

    public boolean isNetworkRequest( ISO iso ) {
        MessageTypes networkRequest = MessageTypes.NETWORK_MANAGEMENT_REQUEST;
        return networkRequest.getValue().equals( iso.getHeader().get( 6 ).getContentField() );
    }

    public boolean isLogon( ISO iso ) {
        NetworkManagement logon = NetworkManagement.LOGON;
        return logon.getValue().equals( iso.getDataElements().get( 69 ).getContentField() );
    }

    public boolean isLogoff( ISO iso ) {
        NetworkManagement loggff = NetworkManagement.LOGOFF;
        return loggff.getValue().equals( iso.getDataElements().get( 69 ).getContentField() );
    }

    public boolean isFinancialResponse( ISO iso ) {
        MessageTypes financialResponse = MessageTypes.FINALCIAL_RESPONSE_MSG;
        TranCodes wdl = TranCodes.WITHDRAWAL;
        ResponseCodes approved = ResponseCodes.APPROVED;
        return financialResponse.getValue().equals( iso.getHeader().get( 6 ).getContentField() ) &&
                wdl.getValue().equals( iso.getDataElements().get( 3 ).getContentField().substring( 0, 2 ) ) &&
                approved.getValue().equals( iso.getDataElements().get( 38 ).getContentField() );
    }

    public ISO buildNetworkLogon() {
        Map < String, String > dataElements = new LinkedHashMap <>();
        dataElements.put( "p11", "200000" );
        dataElements.put( "s70", "Logon" );
        Request0800 rq0800 = new Request0800( dataElements );
        return rq0800.getIso();
    }

    public ISO buildNetworkLogoff() {
        Map < String, String > dataElements = new LinkedHashMap <>();
        dataElements.put( "p11", "299999" );
        dataElements.put( "s70", "Logoff" );
        Request0800 rq0800 = new Request0800( dataElements );
        return rq0800.getIso();
    }

    //Cambio release/monitoreoatm
    public String randomSequence() {
        StringBuilder str = new StringBuilder();
        try {
            Random random = SecureRandom.getInstance( "SHA1PRNG" );
            final long streamSize = 6;
            SecureRandom ranGen = SecureRandom.getInstance( "SHA1PRNG" );
            final int min = ranGen.nextInt( 2 );
            int max = ranGen.nextInt( 11 ) + min + 1;
            if ( max > 10 || max < 4 ) {
                max = 10;
            }
            IntStream intStream = random.ints( streamSize, min, max );
            Iterator < Integer > iterator = intStream.iterator();
            while ( iterator.hasNext() ) {
                str.append( iterator.next() );
            }
            return str.toString();
        } catch ( NoSuchAlgorithmException e ) {
            LOG.error( e.getMessage() );
            return "184563";
        }
    }
    //Cambio release/monitoreoatm

    public String formatKey( String key, String keyLen ) {
        StringBuilder builderKey = new StringBuilder();
        int len = Integer.parseInt( keyLen, 16 );
        for ( int i = 0, j = 3; i < len; i = j, j = j + 3 ) {
            int hex = Integer.parseInt( key.substring( i, j ) );
            if ( hex < 0 ) {
                builderKey.append( String.format( "%02X", hex ) );
            } else if ( hex <= 15 ) {
                builderKey.append( "0" ).append( Integer.toHexString( hex ) );
            } else {
                builderKey.append( Integer.toHexString( hex ) );
            }
        }
        return builderKey.toString().toUpperCase();
    }

    public String fillSpaces( String str ) {
        StringBuilder stringBuilder = new StringBuilder( str );
        int count = str.length();
        for ( ; count < 16; count++ ) {
            stringBuilder.append( " " );

        }
        return stringBuilder.toString();
    }

    public String extract( String ptr ) {
        int indexOf = ptr.indexOf( ( char ) 28 );

        if ( indexOf == -1 ) {
            return ptr;
        }
        return ptr.substring( 0, indexOf );
    }

    public String scanFieldSeparator( String ptr ) {
        int indexOf = ptr.indexOf( ( char ) 28 );
        return ptr.substring( indexOf + 1 );
    }
}
