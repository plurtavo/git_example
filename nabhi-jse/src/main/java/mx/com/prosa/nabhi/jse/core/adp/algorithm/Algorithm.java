package mx.com.prosa.nabhi.jse.core.adp.algorithm;

import mx.com.prosa.nabhi.jse.core.adp.algorithm.container.CassetteUnitHealth;
import mx.com.prosa.nabhi.misc.exception.ATMException;

public interface Algorithm {


    String dispensed( int amount, CassetteUnitHealth cassetteUnitHealth ) throws ATMException;
}
