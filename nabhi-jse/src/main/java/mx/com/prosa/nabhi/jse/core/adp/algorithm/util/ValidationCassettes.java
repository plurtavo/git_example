package mx.com.prosa.nabhi.jse.core.adp.algorithm.util;

//Cambio release/algoritmos

import mx.com.prosa.nabhi.jse.core.adp.algorithm.container.CassetteHealth;
import mx.com.prosa.nabhi.jse.core.adp.algorithm.container.CassetteUnitHealth;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.model.devices.cdm.Cassette;
import mx.com.prosa.nabhi.misc.model.devices.constants.cdm.cassette.CassetteStatus;
import mx.com.prosa.nabhi.misc.model.devices.constants.cdm.cassette.CassetteType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static mx.com.prosa.nabhi.misc.model.devices.constants.cdm.cassette.CassetteStatus.*;
import static mx.com.prosa.nabhi.misc.model.devices.constants.cdm.cassette.CassetteType.BILLCASSETTE;
import static mx.com.prosa.nabhi.misc.model.devices.constants.cdm.cassette.CassetteType.RECYCLING;

@Component
public class ValidationCassettes {

    //Cambio release/algoritmos
    public static final Logger LOGGER = LoggerFactory.getLogger( ValidationCassettes.class );


    public CassetteUnitHealth validateCashUnits( List < Cassette > cassettes ) {
        List < CassetteHealth > cassetteHealths = new ArrayList <>();
        for ( Cassette cassette : cassettes ) {
            CassetteType type = CassetteType.valueOfCompose( cassette.getType() );
            if ( type == BILLCASSETTE || type == RECYCLING ) {
                CassetteHealth cassetteHealth = new CassetteHealth( cassette.getCassetteIndex(), cassette.getDenomination(), validateStatusForSingleCassette( cassette ), cassette.getPhysical().getCurrent() );
                cassetteHealths.add( cassetteHealth );

            }
        }
        mirrorCassette( cassetteHealths );
        Collections.sort( cassetteHealths );
        if ( LOGGER.isDebugEnabled() ) {
            for ( CassetteHealth cassetteHealth : cassetteHealths ) {
                LOGGER.debug( String.format( "Se agrega casetera numero %d, con denominación %s y estatus %s", cassetteHealth.getCassetteIndex(), cassetteHealth.getDenomination(), cassetteHealth.isStatus() ) );
            }
        }
        return new CassetteUnitHealth( cassetteHealths, cassettes.size() );
    }

    private boolean validateStatusForSingleCassette( Cassette cassette ) {
        CassetteStatus s = CassetteStatus.valueOfCompose( cassette.getPhysical().getStatus() );
        return s == OK || ( s == LOW || s == FULL || s == HIGH );
    }

    private void mirrorCassette( List < CassetteHealth > healths ) {
        for ( int i = 1; i < healths.size(); i++ ) {
            boolean status1 = healths.get( i - 1 ).isStatus();
            boolean status2 = healths.get( i ).isStatus();
            int denomination1 = healths.get( i - 1 ).getDenomination();
            int denomination2 = healths.get( i ).getDenomination();
            if ( ( status1 == status2 ) && ( denomination1 == denomination2 ) ) {
                if ( healths.get( i ).getCurrent() > 5 ) {
                    healths.get( i - 1 ).setStatus( false );
                } else {
                    healths.get( i ).setStatus( false );
                }
                if ( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( String.format( "La casetera %d y la casetera %d son espejo", healths.get( i - 1 ).getCassetteIndex(), healths.get( i ).getCassetteIndex() ) );
                }
            }
        }
    }

    public Cassette getLowCassette( List < Cassette > cassettes ) throws ATMException {
        List < CassetteHealth > cassetteHealths = validateCashUnits( cassettes ).getCassetteHealths();
        int multiple = 0;
        for ( CassetteHealth cassetteHealth : cassetteHealths ) {
            if ( cassetteHealth.isStatus() ) {
                multiple = cassetteHealth.getDenomination();
                break;
            }
        }
        return validateMultipleAndDenomination( cassettes, multiple );

    }

    private Cassette validateMultipleAndDenomination( List < Cassette > cassettes, int multiple ) throws ATMException {
        for ( Cassette cassette : cassettes ) {
            CassetteType type = CassetteType.valueOfCompose( cassette.getType() );
            if ( cassette.getDenomination() == multiple && ( type == BILLCASSETTE || type == RECYCLING ) ) {
                return cassette;
            }

        }
        throw new ATMException( CatalogError.DENOMINATION_INVALID );
    }
    //Cambio release/algoritmos

}
