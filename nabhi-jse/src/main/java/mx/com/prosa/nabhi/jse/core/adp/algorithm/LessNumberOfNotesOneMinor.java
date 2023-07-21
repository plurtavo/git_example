package mx.com.prosa.nabhi.jse.core.adp.algorithm;

import mx.com.prosa.nabhi.jse.core.adp.algorithm.container.CassetteUnitHealth;
import mx.com.prosa.nabhi.jse.core.adp.algorithm.util.MasterAlgorithm;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LessNumberOfNotesOneMinor implements Algorithm {


    private static final Logger LOGGER = LoggerFactory.getLogger( LessNumberOfNotesOneMinor.class );
    private final MasterAlgorithm masterAlgorithm;

    @Autowired
    public LessNumberOfNotesOneMinor( MasterAlgorithm masterAlgorithm ) {
        this.masterAlgorithm = masterAlgorithm;
    }

    @Override
    public String dispensed( int amount, CassetteUnitHealth cassetteUnitHealth ) throws ATMException {
        if ( LOGGER.isInfoEnabled() ) {
            LOGGER.info( String.format( "Se solicita dispensar %d con algoritmo \"Menor numero de notas y una nota de la menor denominación posible\" ", amount ) );
        }
        return masterAlgorithm.dispensed( amount, cassetteUnitHealth, MasterAlgorithm.Algorithm.LESS_NUMBER_OF_NOTES_AND_ONE_NOTE_MINOR );
    }

}
