package mx.com.prosa.nabhi.jke.core.handler;

import com.dsapi.core.sockets.controller.ITCPController;
import com.dsapi.core.sockets.exception.EError;
import com.dsapi.core.sockets.exception.ErrorSock;
import com.dsapi.core.sockets.exception.TransactionException;
import mx.com.prosa.nabhi.jke.core.sockets.IKeyExchangeController;
import mx.com.prosa.nabhi.jke.core.sockets.NodeJKEBuilder;
import mx.com.prosa.nabhi.misc.model.jke.exchange.KeyRequest;
import mx.com.prosa.nabhi.misc.model.jke.exchange.KeyResponse;
import mx.com.prosa.nabhi.misc.socket.TimerTable;
import mx.com.prosa.nabhi.misc.util.KeyResponseBuilder;
import mx.com.prosa.nabhi.misc.util.TranslatePinBlockBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.dsapi.core.sockets.exception.EError.ERROR_TXT_5;
import static com.dsapi.core.sockets.exception.EError.ERROR_TXT_6;


public class KeyExchangeManager implements IKeyExchangeController {

    //Cambio release/monitoreo
    private final ITCPController tcpController;
    private final KeyResponseBuilder keyResponseBuilder;
    private final TranslatePinBlockBuilder translatePinBlockBuilder;
    private final NodeJKEBuilder nodeJKEBuilder;
    private static final Logger LOG = LoggerFactory.getLogger( KeyExchangeManager.class );
    private static final Map < Integer, Object > LOCK_OBJECT = new LinkedHashMap <>();
    private static final Map < Integer, TimerTable > TIMER_TABLE_REQUEST = new LinkedHashMap <>();
    private static final Map < Integer, KeyResponse > TIMER_TABLE_KEY_RESPONSE = new LinkedHashMap <>();
    private final int waitTime;

    public KeyExchangeManager( ITCPController tcpController, KeyResponseBuilder keyResponseBuilder, TranslatePinBlockBuilder translatePinBlockBuilder, int waitTime, NodeJKEBuilder nodeJKEBuilder ) {
        this.tcpController = tcpController;
        this.keyResponseBuilder = keyResponseBuilder;
        this.translatePinBlockBuilder = translatePinBlockBuilder;
        this.waitTime = waitTime;
        this.nodeJKEBuilder = nodeJKEBuilder;
    }
    //Cambio release/monitoreo

    @Override
    public KeyResponse send( KeyRequest keyRequest ) throws TransactionException {
        Integer key = Integer.parseInt( keyRequest.getSequence() );
        TimerTable timerTable = new TimerTable( key, waitTime, this );
        if ( LOG.isDebugEnabled() ){
            LOG.debug( "Agregando llaves de tiempo" );
        }
        LOCK_OBJECT.put( key, new Object() );
        TIMER_TABLE_REQUEST.put( key, timerTable );
        if ( tcpController.isConnected() ) {
            return sendAndLockMessage( key, keyRequest.toString() );
        } else {
            if ( LOG.isErrorEnabled() ) {
                LOG.error( ERROR_TXT_5.getErrorMessage() );
            }
            List < ErrorSock > errors = new ArrayList <>();
            errors.add( EError.getError( ERROR_TXT_5 ) );
            throw new TransactionException( ERROR_TXT_5.getErrorMessage(), errors );
        }
    }

    @Override
    public void receive( String message ) {
        KeyResponse response;
        if ( message.startsWith( "1A" ) ) {
            response = keyResponseBuilder.build( message );
        } else {
            response = translatePinBlockBuilder.build( message );
        }
        Integer key = Integer.parseInt( response.getSequence() );
        TimerTable timerTable = TIMER_TABLE_REQUEST.get( key );
        if ( timerTable != null ) {
            timerTable.getTimer().stopTask();
            TIMER_TABLE_REQUEST.remove( key );
            TIMER_TABLE_KEY_RESPONSE.put( key, response );
            if ( LOCK_OBJECT.get( key ) != null ) {
                synchronized ( LOCK_OBJECT.get( key ) ) {
                    LOCK_OBJECT.get( key ).notifyAll();
                }
            }
        }
    }

    @Override
    public void notifyTimeOut( int sequence ) {
        if ( LOG.isDebugEnabled() ){
            LOG.debug( String.format( "Tiempo de espera excedido para la secuencia %d", sequence ) );
        }
        synchronized ( LOCK_OBJECT.get( sequence ) ) {
            LOCK_OBJECT.get( sequence ).notifyAll();
            TIMER_TABLE_REQUEST.remove( sequence );
        }
    }

    //Cambio release/monitoreo
    @Override
    public void disconnected( String nodeName ) {
        nodeJKEBuilder.changeOff( nodeName );
    }
    //Cambio release/monitoreo

    private KeyResponse sendAndLockMessage( Integer key, String message ) throws TransactionException {
        KeyResponse response;
        tcpController.sendMessage( message );
        try {
            synchronized ( LOCK_OBJECT.get( key ) ) {
                lockMessage( key );
                boolean flag = true;
                while ( flag ) {
                    if ( LOG.isTraceEnabled() ) {
                        LOG.trace( String.valueOf( key ) );
                    }
                    LOCK_OBJECT.get( key ).wait();
                    flag = false;
                }
            }
            unlockMessage( key );
            LOCK_OBJECT.remove( key );
            response = TIMER_TABLE_KEY_RESPONSE.get( key );
            TIMER_TABLE_KEY_RESPONSE.remove( key );
            if ( response == null ) {
                List < ErrorSock > errors = new ArrayList <>();
                errors.add( EError.getError( ERROR_TXT_6 ) );
                throw new TransactionException( ERROR_TXT_6.getErrorMessage(), errors );
            }
        } catch ( InterruptedException ex ) {
            LOG.error( EError.ERROR_TXT_7.getErrorMessage() );
            Thread.currentThread().interrupt();
            List < ErrorSock > errors = new ArrayList <>();
            errors.add( EError.getError( EError.ERROR_TXT_7 ) );
            throw new TransactionException( EError.ERROR_TXT_7.getErrorMessage(), errors );
        }
        return response;
    }

    private void lockMessage( Integer key ) {
        if ( LOG.isDebugEnabled() ) {
            String thReadName = Thread.currentThread().getName();
            LOG.debug( String.format( "Lock message with key: %d in thread %s", key, thReadName ) );
        }
    }

    private void unlockMessage( Integer key ) {
        if ( LOG.isDebugEnabled() ) {
            String thReadName = Thread.currentThread().getName();
            LOG.debug( String.format( "UnLock message with key: %d in thread %s", key, thReadName ) );
        }
    }

    @Override
    public String toString() {
        return "KeyExchangeManager";
    }
}
