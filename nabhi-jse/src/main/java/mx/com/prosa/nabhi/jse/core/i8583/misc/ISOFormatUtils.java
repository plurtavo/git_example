package mx.com.prosa.nabhi.jse.core.i8583.misc;

import mx.com.prosa.nabhi.jse.core.srh.SurchargeFinder;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.TransactionException;
import mx.com.prosa.nabhi.misc.model.jdb.APC;
import mx.com.prosa.nabhi.misc.model.jdb.personalized.ATDTransactional;
import mx.com.prosa.nabhi.misc.model.srh.RequestSurcharge;
import mx.com.prosa.nabhi.misc.util.ISOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@Primary
public class ISOFormatUtils extends ISOUtils {

    private static final String ZONE_HOST = "America/Mexico_City";
    private final SurchargeFinder finder;

    @Autowired
    public ISOFormatUtils( SurchargeFinder finder ) {
        this.finder = finder;
    }

    String allowGroup( ATDTransactional atd, APC apc ) {
        return apc.getSharingGroup() + apc.getAllowedCode().getAllowedCode() + atd.getCounty().getState().getStateCode() + atd.getCounty().getCountyCode() + atd.getIdf().getCountry().getCountryCode() + apc.getRoutingGroup();
    }

    String surchargeValue( String ip, String track, String tranCode, String termId ) throws TransactionException, ATMException {
        RequestSurcharge requestSurcharge = new RequestSurcharge();
        requestSurcharge.setIp( ip );
        requestSurcharge.setTrack( track );
        requestSurcharge.setTransactionCode( tranCode );
        requestSurcharge.setTermId( termId );
        return finder.getSurcharge( requestSurcharge ).getSurcharges();
    }

    public String obfuscateCardNumber( String cardNumber ) {
        StringBuilder obs = new StringBuilder();
        int i = 0;
        for ( char c : cardNumber.toCharArray() ) {
            if ( i < cardNumber.length() - 4 ) {
                obs.append( "*" );
            } else {
                obs.append( c );
            }
            i++;
        }
        return obs.toString();
    }

    String verifyUTCDifference( String zone ) {
        ZoneId center = ZoneId.of( ZONE_HOST );
        ZonedDateTime zoneHost = Instant.now().atZone( center );
        int secondsHost = zoneHost.getOffset().getTotalSeconds();
        ZoneId atm = ZoneId.of( zone );
        ZonedDateTime atmZone = Instant.now().atZone( atm );
        int secondsATM = atmZone.getOffset().getTotalSeconds();
        if ( secondsHost == secondsATM ) {
            return "+000";
        }
        if ( secondsHost > secondsATM ) {
            String dif = String.valueOf( ( secondsHost - secondsATM ) / 60 );
            if ( dif.length() < 3 ) {
                dif = "0" + dif;
            }
            return "-" + dif;
        } else {
            String dif = String.valueOf( ( secondsATM - secondsHost ) / 60 );
            if ( dif.length() < 3 ) {
                dif = "0" + dif;
            }
            return "+" + dif;
        }
    }
}
