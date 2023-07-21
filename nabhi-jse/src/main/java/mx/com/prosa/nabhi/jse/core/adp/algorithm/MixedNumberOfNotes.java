package mx.com.prosa.nabhi.jse.core.adp.algorithm;

import mx.com.prosa.nabhi.jse.core.adp.algorithm.container.CassetteUnitHealth;
import mx.com.prosa.nabhi.jse.core.adp.algorithm.util.MasterAlgorithm;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MixedNumberOfNotes implements Algorithm {


    private static final Logger LOGGER = LoggerFactory.getLogger( MixedNumberOfNotes.class );
    private final MasterAlgorithm masterAlgorithm;

    @Autowired
    public MixedNumberOfNotes( MasterAlgorithm masterAlgorithm ) {
        this.masterAlgorithm = masterAlgorithm;
    }

    @Override
    public String dispensed( int amount, CassetteUnitHealth cassetteUnitHealth ) throws ATMException {
        if ( LOGGER.isInfoEnabled() ) {
            LOGGER.info( String.format( "Se solicita dispensar %d con algoritmo \"Numero de notas Mixta\" ", amount ) );
        }
        return masterAlgorithm.dispensed( amount, cassetteUnitHealth, MasterAlgorithm.Algorithm.MIXED );
    }

}
