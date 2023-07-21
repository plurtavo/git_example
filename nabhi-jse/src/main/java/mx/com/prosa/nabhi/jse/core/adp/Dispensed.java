package mx.com.prosa.nabhi.jse.core.adp;

import mx.com.prosa.nabhi.jse.core.adp.algorithm.Algorithm;
import mx.com.prosa.nabhi.jse.core.adp.algorithm.container.CassetteHealth;
import mx.com.prosa.nabhi.jse.core.adp.algorithm.container.CassetteUnitHealth;
import mx.com.prosa.nabhi.jse.core.adp.algorithm.container.Thresholds;
import mx.com.prosa.nabhi.jse.core.adp.algorithm.util.MasterAlgorithm;
import mx.com.prosa.nabhi.jse.core.adp.algorithm.util.ValidationCassettes;
import mx.com.prosa.nabhi.jse.core.database.memory.atm.ATMSearch;
import mx.com.prosa.nabhi.misc.domain.algorithm.dto.DispensedAlgorithm;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.model.adp.BillsModel;
import mx.com.prosa.nabhi.misc.model.devices.cdm.Cassette;
import mx.com.prosa.nabhi.misc.model.devices.cdm.Dispenser;
import mx.com.prosa.nabhi.misc.model.jdb.personalized.ATDDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Dispensed {

    private static final Logger LOG = LoggerFactory.getLogger( Dispensed.class );
    //Cambio release/algoritmos
    private final ATMSearch atmSearch;
    private final ValidationCassettes validation;
    private final Algorithm lessNumberOfNotes;
    private final Algorithm lessNumberOfNotesOneMinor;
    private final Algorithm lessNumberOfNotesThreeMinor;
    private final Algorithm mixedNumberOfNotes;
    private final Algorithm numberOfNotesByThresholds;
    //Cambio release/algoritmos

    @Autowired
    public Dispensed( ATMSearch atmSearch, ValidationCassettes validation, Algorithm lessNumberOfNotes, Algorithm lessNumberOfNotesOneMinor, Algorithm lessNumberOfNotesThreeMinor, Algorithm mixedNumberOfNotes, Algorithm numberOfNotesByThresholds ) {
        this.atmSearch = atmSearch;
        this.validation = validation;
        this.lessNumberOfNotes = lessNumberOfNotes;
        this.lessNumberOfNotesOneMinor = lessNumberOfNotesOneMinor;
        this.lessNumberOfNotesThreeMinor = lessNumberOfNotesThreeMinor;
        this.mixedNumberOfNotes = mixedNumberOfNotes;
        this.numberOfNotesByThresholds = numberOfNotesByThresholds;
    }

    //Cambio release/algoritmos
    public BillsModel dispenseFourUnits( String terminal, int amount ) throws ATMException {
        ATDDevice atdDevice = atmSearch.searchDeviceAlgorithm( terminal );
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "Calculado el numero de billetes a dispensar para el cajero %s y monto %d", terminal, amount ) );
        }
        return doExecuteAlgorithm( atdDevice, amount );
    }

    public Cassette getMinimumAmount( String ip ) throws ATMException {
        Dispenser dispenser = atmSearch.searchDeviceAlgorithm( ip ).getTerminalDevices().getDispenser();
        return validation.getLowCassette( dispenser.getCassettes() );
    }

    //Cambio release/algoritmos
    private BillsModel doExecuteAlgorithm( ATDDevice atdDevice, int amount ) throws ATMException {
        MasterAlgorithm.Algorithm algorithm = MasterAlgorithm.Algorithm.valueOf( atdDevice.getDispensedAlgorithm().getDispensedType() );
        CassetteUnitHealth cassetteUnitHealth = validation.validateCashUnits( atdDevice.getTerminalDevices().getDispenser().getCassettes() );
        String bills;
        switch ( algorithm ) {
            case MIXED:
                bills = mixedNumberOfNotes.dispensed( amount, cassetteUnitHealth );
                break;
            case LESS_NUMBER_OF_NOTES:
                bills = lessNumberOfNotes.dispensed( amount, cassetteUnitHealth );
                break;
            case LESS_NUMBER_OF_NOTES_AND_THREE_NOTE_MINOR:
                bills = lessNumberOfNotesThreeMinor.dispensed( amount, cassetteUnitHealth );
                break;
            case THRESHOLDS:
                buildTThresholds( cassetteUnitHealth, atdDevice.getDispensedAlgorithm() );
                bills = numberOfNotesByThresholds.dispensed( amount, cassetteUnitHealth );
                break;
            case LESS_NUMBER_OF_NOTES_AND_ONE_NOTE_MINOR:
            default:
                bills = lessNumberOfNotesOneMinor.dispensed( amount, cassetteUnitHealth );
                break;
        }
        BillsModel billsModel = new BillsModel();

        billsModel.setBills( bills );
        return billsModel;
    }


    private void buildTThresholds( CassetteUnitHealth cassetteUnitHealth, DispensedAlgorithm dispensedAlgorithm ) throws ATMException {
        for ( CassetteHealth cassetteHealth : cassetteUnitHealth.getCassetteHealths() ) {
            Thresholds thresholds = new Thresholds();
            switch ( cassetteHealth.getDenomination() ) {
                case 20:
                    thresholds.setMaxOfNotes( dispensedAlgorithm.getNotesLimit20() );
                    thresholds.setMinimumAmount( dispensedAlgorithm.getMinimumAmount20() );
                    break;
                case 50:
                    thresholds.setMaxOfNotes( dispensedAlgorithm.getNotesLimit50() );
                    thresholds.setMinimumAmount( dispensedAlgorithm.getMinimumAmount50() );
                    break;
                case 100:
                    thresholds.setMaxOfNotes( dispensedAlgorithm.getNotesLimit100() );
                    thresholds.setMinimumAmount( dispensedAlgorithm.getMinimumAmount100() );
                    break;
                case 200:
                    thresholds.setMaxOfNotes( dispensedAlgorithm.getNotesLimit200() );
                    thresholds.setMinimumAmount( dispensedAlgorithm.getMinimumAmount200() );
                    break;
                case 500:
                    thresholds.setMaxOfNotes( dispensedAlgorithm.getNotesLimit500() );
                    thresholds.setMinimumAmount( dispensedAlgorithm.getMinimumAmount500() );
                    break;
                case 1000:
                    thresholds.setMaxOfNotes( dispensedAlgorithm.getNotesLimit1000() );
                    thresholds.setMinimumAmount( dispensedAlgorithm.getMinimumAmount1000() );
                    break;
                default:
                    throw new ATMException( CatalogError.UMBRALES_ERROR );
            }
            cassetteHealth.setThresholds( thresholds );
        }
    }
    //Cambio release/algoritmos

}
