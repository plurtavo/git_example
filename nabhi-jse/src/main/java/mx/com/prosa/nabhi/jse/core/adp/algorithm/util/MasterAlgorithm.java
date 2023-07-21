package mx.com.prosa.nabhi.jse.core.adp.algorithm.util;

import mx.com.prosa.nabhi.jse.core.adp.algorithm.container.CassetteHealth;
import mx.com.prosa.nabhi.jse.core.adp.algorithm.container.CassetteUnitHealth;
import mx.com.prosa.nabhi.jse.core.adp.algorithm.container.Thresholds;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MasterAlgorithm {

    private static final Logger LOGGER = LoggerFactory.getLogger( MasterAlgorithm.class );


    public String dispensed( int amount, CassetteUnitHealth cassetteUnitHealth, Algorithm algorithm ) throws ATMException {
        List < CassetteHealth > cassetteHealths = cassetteUnitHealth.getCassetteHealths();
        CassetteHealth health3 = cassetteHealths.get( 2 );
        CassetteHealth health2 = cassetteHealths.get( 1 );
        CassetteHealth health1 = cassetteHealths.get( 0 );
        CassetteHealth health4 = cassetteHealths.get( 3 );
        validateThatCanBeDispensed( cassetteHealths, amount );
        if ( health1.isStatus() || health2.isStatus() ) {
            LOGGER.info( "Todas las caseteras OK serán ocupadas" );
            if ( algorithm == Algorithm.THRESHOLDS && !cassetteUnitHealth.globalStatus() ) {
                if ( LOGGER.isInfoEnabled() ) {
                    LOGGER.info( String.format( "Se actualiza a dispensar %d con algoritmo \"Menor numero de notas\" ", amount ) );
                }
                dispensedByAlgorithm( amount, cassetteHealths, Algorithm.LESS_NUMBER_OF_NOTES );
            } else {
                dispensedByAlgorithm( amount, cassetteHealths, algorithm );
            }
        } else if ( health3.isStatus() && health4.isStatus() ) {
            LOGGER.info( "Solo las caseteras de mayor denominación serán ocupadas" );
            highDenominationsOk( amount, cassetteHealths );
        } else if ( health4.isStatus() ) {
            LOGGER.info( "Las casetera 4 sera ocupada" );
            incrementNotes( amount, health4 );
        } else if ( health3.isStatus() ) {
            LOGGER.info( "Las casetera 3 sera ocupada" );
            incrementNotes( amount, health3 );
        }
        return cassetteUnitHealth.getBuffer();
    }

    private void dispensedByAlgorithm( int amount, List < CassetteHealth > cassetteHealths, Algorithm algorithm ) throws ATMException {
        if ( algorithm == Algorithm.MIXED ) {
            mixed( amount, cassetteHealths );
        } else if ( algorithm == Algorithm.LESS_NUMBER_OF_NOTES_AND_ONE_NOTE_MINOR ) {
            lessNotesOneMinor( amount, cassetteHealths );
        } else if ( algorithm == Algorithm.LESS_NUMBER_OF_NOTES_AND_THREE_NOTE_MINOR ) {
            lessNoteThreeMinor( amount, cassetteHealths );
        } else if ( algorithm == Algorithm.LESS_NUMBER_OF_NOTES ) {
            lessNote( amount, cassetteHealths );
        } else if ( algorithm == Algorithm.THRESHOLDS ) {
            thresholds( amount, cassetteHealths );
        }
    }

    private void thresholds( int amount, List < CassetteHealth > cassetteHealths ) throws ATMException {
        int amountTemporary = amount;
        int amountAccumulated = 0;

        for ( int i = 1; i < cassetteHealths.size(); i++ ) {
            CassetteHealth cassetteHealth = cassetteHealths.get( i );
            Thresholds thresholds = cassetteHealth.getThresholds();
            while ( amountTemporary >= thresholds.getMinimumAmount() && cassetteHealth.getOrderDispensed() < thresholds.getMaxOfNotes() ) {
                amountTemporary = oneNoteForCassette( cassetteHealth, amountTemporary );
                amountAccumulated += cassetteHealth.getDenomination();
            }
        }
        if ( amountAccumulated < amount ) {
            CassetteHealth cassetteHealth = cassetteHealths.get( 0 );
            Thresholds thresholds = cassetteHealth.getThresholds();
            while ( amountTemporary >= thresholds.getMinimumAmount() && cassetteHealth.getOrderDispensed() < thresholds.getMaxOfNotes() ) {
                amountTemporary = oneNoteForCassette( cassetteHealth, amountTemporary );
                amountAccumulated += cassetteHealth.getDenomination();
            }
        }
        if ( amountAccumulated < amount ) {
            for ( CassetteHealth cassetteHealth : cassetteHealths ) {
                cassetteHealth.setOrderDispensed( 0 );
            }
            if ( LOGGER.isInfoEnabled() ) {
                LOGGER.info( String.format( "Se actualiza a dispensar %d con algoritmo \"Menor numero de notas\" ", amount ) );
            }
            dispensedByAlgorithm( amount, cassetteHealths, Algorithm.LESS_NUMBER_OF_NOTES );
        }
    }

    private void mixed( int amount, List < CassetteHealth > cassetteHealths ) {
        while ( amount > 0 ) {
            amount = oneNoteForCassette( cassetteHealths.get( 0 ), amount );
            amount = oneNoteForCassette( cassetteHealths.get( 1 ), amount );
            amount = oneNoteForCassette( cassetteHealths.get( 2 ), amount );
            amount = oneNoteForCassette( cassetteHealths.get( 3 ), amount );
        }
    }

    private void lessNotesOneMinor( int amount, List < CassetteHealth > cassetteHealths ) {
        while ( amount > 0 ) {
            boolean lessCassette;
            int initialAmount = amount;
            amount = oneNoteForCassette( cassetteHealths.get( 0 ), amount );
            lessCassette = compareAmount( initialAmount, amount );
            if ( !lessCassette ) {
                amount = oneNoteForCassette( cassetteHealths.get( 1 ), amount );
                lessCassette = compareAmount( initialAmount, amount );
            }
            if ( !lessCassette ) {
                amount = oneNoteForCassette( cassetteHealths.get( 2 ), amount );
            }
            amount = billsFromHigher( amount, cassetteHealths );
        }
    }

    private void lessNoteThreeMinor( int amount, List < CassetteHealth > cassetteHealths ) {
        while ( amount > 0 ) {
            amount = oneNoteForCassette( cassetteHealths.get( 0 ), amount );
            amount = oneNoteForCassette( cassetteHealths.get( 1 ), amount );
            amount = oneNoteForCassette( cassetteHealths.get( 2 ), amount );
            amount = billsFromHigher( amount, cassetteHealths );
        }
    }

    private void lessNote( int amount, List < CassetteHealth > cassetteHealths ) {
        billsFromHigher( amount, cassetteHealths );
    }

    private boolean compareAmount( int initialAmount, int amount ) {
        return initialAmount != amount;
    }

    private int oneNoteForCassette( CassetteHealth cassetteHealth, int amount ) {
        if ( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( String.format( "Validando casetera numeró %d con denominación de %s", cassetteHealth.getCassetteIndex(), cassetteHealth.getDenomination() ) );
        }
        if ( cassetteHealth.isStatus() && amount >= cassetteHealth.getDenomination() ) {
            cassetteHealth.incrementOrderDispensed();
            amount -= cassetteHealth.getDenomination();
            addNotes( 1, cassetteHealth, amount );
            return amount;
        }
        return amount;
    }


    private int billsFromHigher( int amount, List < CassetteHealth > cassetteHealths ) {
        for ( int i = cassetteHealths.size() - 1; i >= 0; i-- ) {
            CassetteHealth health = cassetteHealths.get( i );
            if ( health.isStatus() && amount >= health.getDenomination() ) {
                int cassetteNotes = amount / health.getDenomination();
                amount -= ( cassetteNotes * health.getDenomination() );
                for ( int j = 0; j < cassetteNotes; j++ ) {
                    health.incrementOrderDispensed();
                }
                addNotes( cassetteNotes, health, amount );
            }
        }
        return amount;
    }

    private void highDenominationsOk( int amount, List < CassetteHealth > cassetteHealths ) throws ATMException {
        CassetteHealth health2 = cassetteHealths.get( 1 );
        CassetteHealth health3 = cassetteHealths.get( 2 );
        CassetteHealth health4 = cassetteHealths.get( 3 );
        if ( amount >= health3.getDenomination() && amount % health2.getDenomination() == 0 ) {
            if ( amount >= health4.getDenomination() ) {
                highDenomination( amount, cassetteHealths );
            } else if ( amount % health3.getDenomination() == 0 ) {
                int cassetteNotes = amount / health3.getDenomination();
                for ( int j = 0; j < cassetteNotes; j++ ) {
                    health3.incrementOrderDispensed();
                }
                addNotes( cassetteNotes, health3, amount );
            } else {
                throwIncorrectAmount();
            }
        } else {
            throwIncorrectAmount();
        }
    }

    private void highDenomination( int amount, List < CassetteHealth > cassetteHealths ) {
        CassetteHealth health3 = cassetteHealths.get( 2 );
        CassetteHealth health4 = cassetteHealths.get( 3 );
        while ( amount > 0 ) {
            if ( amount % health4.getDenomination() == 0 ) {
                int cassetteNotes = amount / health4.getDenomination();
                amount -= ( cassetteNotes * health4.getDenomination() );
                for ( int j = 0; j < cassetteNotes; j++ ) {
                    health4.incrementOrderDispensed();
                }
                addNotes( cassetteNotes, health4, amount );
            }
            if ( amount % health3.getDenomination() == 0 ) {
                if ( amount != 0 ) {
                    amount -= health3.getDenomination();
                    health3.incrementOrderDispensed();
                    addNotes( 1, health3, amount );
                }
            } else {
                health4.incrementOrderDispensed();
                amount -= health4.getDenomination();
            }
        }
    }


    private void incrementNotes( int amount, CassetteHealth health ) throws ATMException {
        if ( validateResidue( health.getDenomination(), amount ) ) {
            int cassetteNotes = amount / health.getDenomination();
            for ( int j = 0; j < cassetteNotes; j++ ) {
                health.incrementOrderDispensed();
            }
            addNotes( cassetteNotes, health, amount );
        } else {
            throwIncorrectAmount();
        }
    }

    private boolean validateResidue( int denomination, int amount ) {
        return amount % denomination == 0;
    }

    private void addNotes( int cassetteNotes, CassetteHealth health, int amount ) {
        if ( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( String.format( "Se agregan %d nota(s) de %s, monto restante %d", cassetteNotes, health.getDenomination(), amount ) );
        }
    }

    public void validateThatCanBeDispensed( List < CassetteHealth > cassetteHealths, int amount ) throws ATMException {
        int residue = amount;
        for ( CassetteHealth health : cassetteHealths ) {
            if ( health.isStatus() ) {
                residue = residue % health.getDenomination();
                if ( residue == 0 ) {
                    if ( LOGGER.isDebugEnabled() ) {
                        LOGGER.debug( String.format( "La cantidad %d es múltiplo de la casetera %d con menor denominación %s", amount, health.getCassetteIndex(), health.getDenomination() ) );
                    }
                    return;
                }
            }
        }
        if ( cassetteHealths.get( cassetteHealths.size() - 1 ).isStatus() &&
                validateThatCanBeDispensed2( cassetteHealths, amount, cassetteHealths.size() - 2, cassetteHealths.get( cassetteHealths.size() - 1 ).getDenomination() ) ) {
            return;
        }
        throwIncorrectAmount();
    }

    private boolean validateThatCanBeDispensed2( List < CassetteHealth > cassetteHealths, int amount, int i, int denomination ) {
        int residue = amount - denomination;
        for ( ; i > 0; i-- ) {
            if ( cassetteHealths.get( i ).isStatus() ) {
                residue = residue % cassetteHealths.get( i ).getDenomination();
                if ( residue == 0 ) {
                    return true;
                }
            }
        }
        return false;
    }

    private void throwIncorrectAmount() throws ATMException {
        throw new ATMException( CatalogError.INVALID_AMOUNT_DISP );
    }

    public enum Algorithm {
        MIXED,
        LESS_NUMBER_OF_NOTES,
        LESS_NUMBER_OF_NOTES_AND_ONE_NOTE_MINOR,
        LESS_NUMBER_OF_NOTES_AND_THREE_NOTE_MINOR,
        THRESHOLDS,
    }
}
