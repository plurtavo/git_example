package mx.com.prosa.nabhi.jse.dispenser;

import mx.com.prosa.nabhi.jse.core.adp.ContextADP;
import mx.com.prosa.nabhi.jse.core.adp.algorithm.Algorithm;
import mx.com.prosa.nabhi.jse.core.adp.algorithm.container.CassetteUnitHealth;
import mx.com.prosa.nabhi.jse.core.adp.algorithm.container.Thresholds;
import mx.com.prosa.nabhi.jse.core.adp.algorithm.util.MasterAlgorithm;
import mx.com.prosa.nabhi.jse.core.adp.algorithm.util.ValidationCassettes;
import mx.com.prosa.nabhi.jse.core.adp.algorithm.util.YAMLDefinitionLoader;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.model.devices.cdm.Dispenser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith( SpringRunner.class )
@SpringBootTest( classes = { ContextADP.class } )
@ActiveProfiles( "dev" )
public class AllAlgorithmTest {

    public static final Logger LOGGER = LoggerFactory.getLogger( AllAlgorithmTest.class );
    @Autowired
    private YAMLDefinitionLoader yamlDefinitionLoader;
    @Autowired
    private Algorithm lessNumberOfNotesOneMinor;
    @Autowired
    private Algorithm lessNumberOfNotes;
    @Autowired
    private Algorithm lessNumberOfNotesThreeMinor;
    @Autowired
    private Algorithm mixedNumberOfNotes;
    @Autowired
    private ValidationCassettes validationCassettes2;
    @Autowired
    private Algorithm numberOfNotesByThresholds;


    @Test
    public void testCassLessNotes() {
        LOGGER.info( MasterAlgorithm.Algorithm.LESS_NUMBER_OF_NOTES.name() );
        for ( int amount = 50; amount <= 8200; amount += 50 ) {
            testDispenser( amount, lessNumberOfNotes, MasterAlgorithm.Algorithm.LESS_NUMBER_OF_NOTES );
        }
    }

    @Test
    public void testCassLessNumberOfNotesOneMinor() {
        LOGGER.info( MasterAlgorithm.Algorithm.LESS_NUMBER_OF_NOTES_AND_ONE_NOTE_MINOR.name() );
        for ( int amount = 50; amount <= 8200; amount += 50 ) {
            testDispenser( amount, lessNumberOfNotesOneMinor, MasterAlgorithm.Algorithm.LESS_NUMBER_OF_NOTES_AND_ONE_NOTE_MINOR );
        }
    }

    @Test
    public void testCassLessNumberOfNotesThreeMinor() {
        LOGGER.info( MasterAlgorithm.Algorithm.LESS_NUMBER_OF_NOTES_AND_THREE_NOTE_MINOR.name() );
        for ( int amount = 50; amount <= 8200; amount += 50 ) {
            testDispenser( amount, lessNumberOfNotesThreeMinor, MasterAlgorithm.Algorithm.LESS_NUMBER_OF_NOTES_AND_THREE_NOTE_MINOR );
        }
    }

    @Test
    public void testCassMixedNumberOfNotes() {
        LOGGER.info( MasterAlgorithm.Algorithm.MIXED.name() );
        for ( int amount = 50; amount <= 8200; amount += 50 ) {
            testDispenser( amount, mixedNumberOfNotes, MasterAlgorithm.Algorithm.MIXED );
        }
    }


    private void testDispenser( int amount, Algorithm algorithm, MasterAlgorithm.Algorithm algorithmName ) {
        try {
            Dispenser dispenser = getDispenser( 15 );
            CassetteUnitHealth cassetteUnitHealth = validationCassettes2.validateCashUnits( dispenser.getCassettes() );
            Thresholds thresholds1 = new Thresholds( 7, 1 );
            Thresholds thresholds2 = new Thresholds( 4, 100 );
            Thresholds thresholds3 = new Thresholds( 4, 350 );
            Thresholds thresholds4 = new Thresholds( 50, 500 );
            cassetteUnitHealth.getCassetteHealths().get( 0 ).setThresholds( thresholds1 );
            cassetteUnitHealth.getCassetteHealths().get( 1 ).setThresholds( thresholds2 );
            cassetteUnitHealth.getCassetteHealths().get( 2 ).setThresholds( thresholds3 );
            cassetteUnitHealth.getCassetteHealths().get( 3 ).setThresholds( thresholds4 );
            String buffer = algorithm.dispensed( amount, cassetteUnitHealth );
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( String.format( "\t %d \t %d \t %d \t %d",
                        cassetteUnitHealth.getCassetteHealths().get( 0 ).getOrderDispensed(),
                        cassetteUnitHealth.getCassetteHealths().get( 1 ).getOrderDispensed(),
                        cassetteUnitHealth.getCassetteHealths().get( 2 ).getOrderDispensed(),
                        cassetteUnitHealth.getCassetteHealths().get( 3 ).getOrderDispensed() ) );
                LOGGER.debug( String.format( "Buffer: %s", buffer ) );
            }
        } catch ( ATMException e ) {
            LOGGER.error( String.format( "\t %d \t %d \t %d \t %d", 0, 0, 0, 0 ) );

        }
    }

    private Dispenser getDispenser( int value ) {
        String str = Integer.toBinaryString( value );
        if ( str.length() == 1 ) {
            str = "0" + str;
        }
        if ( str.length() == 2 ) {
            str = "0" + str;
        }
        if ( str.length() == 3 ) {
            str = "0" + str;
        }
        char[] array = str.toCharArray();
        boolean[] flags = new boolean[ array.length ];
        for ( int i = 0; i < array.length; i++ ) {
            flags[ i ] = array[ i ] != '0';
        }
        return getDispenser( flags );
    }

    private Dispenser getDispenser( boolean... actives ) {
        Dispenser dispenser = yamlDefinitionLoader.getDispenser( "/dispenser1.yml" );
        for ( int i = 0; i < actives.length; i++ ) {
            if ( !actives[ i ] ) {
                dispenser.getCassettes().get( i + 1 ).getPhysical().setStatus( "NA" );
            }
        }
        return dispenser;
    }


}
