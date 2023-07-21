package mx.com.prosa.nabhi.jse.dispenser;

import mx.com.prosa.nabhi.jse.core.adp.ContextADP;
import mx.com.prosa.nabhi.jse.core.adp.algorithm.Algorithm;
import mx.com.prosa.nabhi.jse.core.adp.algorithm.container.CassetteUnitHealth;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith( SpringRunner.class )
@SpringBootTest( classes = { ContextADP.class } )
@ActiveProfiles( "dev" )
public class LowerNumberOfNoteThreeMinorTest {

    public static final Logger LOGGER = LoggerFactory.getLogger( LowerNumberOfNoteThreeMinorTest.class );
    @Autowired
    private YAMLDefinitionLoader yamlDefinitionLoader;
    @Autowired
    private Algorithm lessNumberOfNotesThreeMinor;
    @Autowired
    private ValidationCassettes validationCassettes2;

    @Test
    public void testADispenserUnit() {
        Dispenser dispenser = yamlDefinitionLoader.getDispenser( "/dispenser.yml" );
        assertNotNull( dispenser );
        if ( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( dispenser.toString() );
        }
    }

    @Test
    public void testDispense1300() {
        int amount = 1300;
        //1111
        testDispenser( 15, amount,  "0002010301" );
        //1110
        testDispenser( 14, amount,  "0002020500" );
        //1101
        testDispenser( 13, amount,  "0002020002" );
        //1100
        testDispenser( 12, amount,  "0002120000" );
        //1011
        testDispenser( 11, amount,  "0002000102" );
        //1010
        testDispenser( 10, amount,  "0002000600" );
        //1001
        testDispenser( 9, amount, "0006000002" );
        //1000
        testDispenser( 8, amount, "0026000000" );
        //0111
        testDispenser( 7, amount, "0000010102" );
        //0110
        testDispenser( 6, amount, "0000010600" );
        //0101
        testDispenser( 5, amount, "0000030002" );
        //0100
        testDispenser( 4, amount, "0000130000" );
        //0011
        testDispenser( 3, amount, "0000000401" );
        //0010
        testDispenser( 2, amount, "El monto seleccionado no se puede dispensar" );
        //0001
        testDispenser( 1, amount, "El monto seleccionado no se puede dispensar" );
    }


    @Test
    public void testDispense1700() {
        int amount = 1700;
        //1111
        testDispenser( 15, amount,  "0002020202" );
        //1110
        testDispenser( 14, amount,  "0002020700" );
        //1101
        testDispenser( 13, amount,  "0002010003" );
        //1100
        testDispenser( 12, amount,  "0002160000" );
        //1011
        testDispenser( 11, amount,  "0002000302" );
        //1010
        testDispenser( 10, amount,  "0002000800" );
        //1001
        testDispenser( 9, amount, "0004000003" );
        //1000
        testDispenser( 8, amount, "0034000000" );
        //0111
        testDispenser( 7, amount, "0000010302" );
        //0110
        testDispenser( 6, amount, "0000010800" );
        //0101
        testDispenser( 5, amount, "0000020003" );
        //0100
        testDispenser( 4, amount, "0000170000" );
        //0011
        testDispenser( 3, amount, "0000000103" );
        //0010
        testDispenser( 2, amount, "El monto seleccionado no se puede dispensar" );
        //0001
        testDispenser( 1, amount, "El monto seleccionado no se puede dispensar" );
    }


    @Test
    public void testDispense850() {
        int amount = 850;
        //1111
        testDispenser( 15, amount,  "0001010101" );
        //1110
        testDispenser( 14, amount,  "0001020300" );
        //1101
        testDispenser( 13, amount,  "0001030001" );
        //1100
        testDispenser( 12, amount,  "0001080000" );
        //1011
        testDispenser( 11, amount,  "0003000101" );
        //1010
        testDispenser( 10, amount,  "0001000400" );
        //1001
        testDispenser( 9, amount, "0007000001" );
        //1000
        testDispenser( 8, amount, "0017000000" );
        //0111
        testDispenser( 7, amount, "El monto seleccionado no se puede dispensar" );
        //0110
        testDispenser( 6, amount, "El monto seleccionado no se puede dispensar" );
        //0101
        testDispenser( 5, amount, "El monto seleccionado no se puede dispensar" );
        //0100
        testDispenser( 4, amount, "El monto seleccionado no se puede dispensar" );
        //0011
        testDispenser( 3, amount, "El monto seleccionado no se puede dispensar" );
        //0010
        testDispenser( 2, amount, "El monto seleccionado no se puede dispensar" );
        //0001
        testDispenser( 1, amount, "El monto seleccionado no se puede dispensar" );
    }

    @Test
    public void testDispense1000() {
        int amount = 1000;
        //1111
        testDispenser( 15, amount,  "0002020101" );
        //1110
        testDispenser( 14, amount,  "0002010400" );
        //1101
        testDispenser( 13, amount,  "0002040001" );
        //1100
        testDispenser( 12, amount,  "0002090000" );
        //1011
        testDispenser( 11, amount,  "0002000201" );
        //1010
        testDispenser( 10, amount,  "0004000400" );
        //1001
        testDispenser( 9, amount, "0010000001" );
        //1000
        testDispenser( 8, amount, "0020000000" );
        //0111
        testDispenser( 7, amount, "0000010201" );
        //0110
        testDispenser( 6, amount, "0000020400" );
        //0101
        testDispenser( 5, amount, "0000050001" );
        //0100
        testDispenser( 4, amount, "0000100000" );
        //0011
        testDispenser( 3, amount, "0000000002" );
        //0010
        testDispenser( 2, amount, "0000000500" );
        //0001
        testDispenser( 1, amount, "0000000002" );
    }


    @Test
    public void testDispense5000() {
        int amount = 5000;
        //1111
        testDispenser( 15, amount,  "0002020109" );
        //1110
        testDispenser( 14, amount,  "0002012400" );
        //1101
        testDispenser( 13, amount,  "0002040009" );
        //1100
        testDispenser( 12, amount,  "El monto seleccionado no se puede dispensar" );
        //1011
        testDispenser( 11, amount,  "0002000209" );
        //1010
        testDispenser( 10, amount,  "0004002400" );
        //1001
        testDispenser( 9, amount, "0010000009" );
        //1000
        testDispenser( 8, amount, "El numero de notas excede el limite permitido de 50" );
        //0111
        testDispenser( 7, amount, "0000010209" );
        //0110
        testDispenser( 6, amount, "0000022400" );
        //0101
        testDispenser( 5, amount, "0000050009" );
        //0100
        testDispenser( 4, amount, "0000500000" );
        //0011
        testDispenser( 3, amount, "0000000010" );
        //0010
        testDispenser( 2, amount, "0000002500" );
        //0001
        testDispenser( 1, amount, "0000000010" );
    }

    @Test
    public void testDispense300() {
        int amount = 300;
        //1111
        testDispenser( 15, amount,  "0002020000" );
        //1110
        testDispenser( 14, amount,  "0002020000" );
        //1101
        testDispenser( 13, amount,  "0002020000" );
        //1100
        testDispenser( 12, amount,  "0002020000" );
        //1011
        testDispenser( 11, amount,  "0002000100" );
        //1010
        testDispenser( 10, amount,  "0002000100" );
        //1001
        testDispenser( 9, amount, "0006000000" );
        //1000
        testDispenser( 8, amount, "0006000000" );
        //0111
        testDispenser( 7, amount, "0000010100" );
        //0110
        testDispenser( 6, amount, "0000010100" );
        //0101
        testDispenser( 5, amount, "0000030000" );
        //0100
        testDispenser( 4, amount, "0000030000" );
        //0011
        testDispenser( 3, amount, "El monto seleccionado no se puede dispensar" );
        //0010
        testDispenser( 2, amount, "El monto seleccionado no se puede dispensar" );
        //0001
        testDispenser( 1, amount, "El monto seleccionado no se puede dispensar" );
    }

    @Test
    public void testDispense8200() {
        int amount = 8200;
        //1111
        testDispenser( 15, amount,  "0002020215" );
        //1110
        testDispenser( 14, amount,  "0002014000" );
        //1101
        testDispenser( 13, amount,  "0002010016" );
        //1100
        testDispenser( 12, amount,  "El numero de notas excede el limite permitido de 50" );
        //1011
        testDispenser( 11, amount,  "0002000315" );
        //1010
        testDispenser( 10, amount,  "0004004000" );
        //1001
        testDispenser( 9, amount, "0004000016" );
        //1000
        testDispenser( 8, amount, "El numero de notas excede el limite permitido de 50" );
        //0111
        testDispenser( 7, amount, "0000010315" );
        //0110
        testDispenser( 6, amount, "0000024000" );
        //0101
        testDispenser( 5, amount, "0000020016" );
        //0100
        testDispenser( 4, amount, "El numero de notas excede el limite permitido de 50" );
        //0011
        testDispenser( 3, amount, "0000000116" );
        //0010
        testDispenser( 2, amount, "0000004100" );
        //0001
        testDispenser( 1, amount, "El monto seleccionado no se puede dispensar" );
    }


    private void testDispenser( int flags, int amount, String expected ){
        try {
            Dispenser dispenser = getDispenser( flags );
            CassetteUnitHealth cassetteUnitHealth = validationCassettes2.validateCashUnits( dispenser.getCassettes() );
            String buffer = lessNumberOfNotesThreeMinor.dispensed( amount, cassetteUnitHealth );
            assertEquals( expected, buffer );
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( String.format( "Esperado %s vs real %s", expected, buffer ) );
            }
        } catch ( ATMException e ) {
            assertEquals( expected, e.getMessage() );
            LOGGER.error( e.getMessage() );
        }
    }

    private Dispenser getDispenser( int value ) {
        String str = Integer.toBinaryString( value );
        if ( str.length() == 1 ){
            str = "0" + str;
        }
        if ( str.length() == 2 ){
            str = "0" + str;
        }
        if ( str.length() == 3 ){
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
        if ( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( String.format( "Cassette1: %s Cassette2: %s Cassette3: %s Cassette4: %s", actives[ 0 ], actives[ 1 ], actives[ 2 ], actives[ 3 ] ) );
        }
        Dispenser dispenser = yamlDefinitionLoader.getDispenser( "/dispenser.yml" );
        for ( int i = 0; i < actives.length; i++ ) {
            if ( !actives[ i ] ) {
                dispenser.getCassettes().get( i + 1 ).getPhysical().setStatus( "NA" );
            }
        }
        return dispenser;
    }


}
