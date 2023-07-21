package mx.com.prosa.nabhi.acv.business;

import com.dsapi.core.sockets.crypto.CryptoException;
import mx.com.prosa.nabhi.misc.exception.ATMException;

public interface IOrderService {

    String captureImage( String atm, String filename ) throws ATMException;

    String getImage( String atm, String filename ) throws ATMException;

    String sendReset( String atm, String device ) throws ATMException;

    String sendCommand( String atm, int command ) throws ATMException;

    String commandUpdateScreen( String payload );

    String sendKey( String atm, String key, int part ) throws ATMException, CryptoException;

    String sendKeyFinal( String atm, String key, int part ) throws ATMException, CryptoException;


}
