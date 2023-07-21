package mx.com.prosa.nabhi.iso.core.handler;

import com.dsapi.core.sockets.controller.ITCPController;
import mx.com.prosa.nabhi.iso.core.sockets.IISOController;
import mx.com.prosa.nabhi.iso.core.sockets.NodeISOBuilder;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.iso.ISO8583Exception;
import mx.com.prosa.nabhi.misc.socket.TimerTable;
import mx.com.prosa.nabhi.misc.util.ISOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.gonet.iso8583.constants.ReversalCodes;
import us.gonet.iso8583.message.Request0800;
import us.gonet.iso8583.message.Request0810;
import us.gonet.iso8583.message.Reversal0420;
import us.gonet.serializable.data.ISO;
import us.gonet.utils.DecodeISO8583;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class ISOManager implements IISOController {

    private final ITCPController tcpController;
    //Cambio release/monitoreo
    private final NodeISOBuilder nodeISOBuilder;
    //Cambio release/monitoreo
    private static final Logger LOG = LoggerFactory.getLogger( ISOManager.class );
    private static final Map < Integer, Object > LOCK_OBJECT = new LinkedHashMap <>();
    private static final Map < Integer, TimerTable > TIMER_TABLE_REQUEST = new LinkedHashMap <>();
    private static final Map < Integer, ISO > TIMER_TABLE_RESPONSE = new LinkedHashMap <>();
    private boolean logon = false;
    private final Echo echoTask;
    private final ISOUtils isoUtils;
    private final int waitTime;

    //Cambio release/monitoreo
    public ISOManager( ITCPController tcpController, ISOUtils isoUtils, int echoDelay, int waitTime, NodeISOBuilder nodeISOBuilder ) {
        this.isoUtils = isoUtils;
        this.tcpController = tcpController;
        this.echoTask = new Echo( this, tcpController, echoDelay );
        this.waitTime = waitTime;
        this.nodeISOBuilder = nodeISOBuilder;
    }
    //Cambio release/monitoreo

    @Override
    public ISO send( ISO wrapper ) throws ISO8583Exception {
        Integer key = getSequence( wrapper );
        TimerTable timerTable = new TimerTable( key, waitTime, this );
        LOCK_OBJECT.put( key, new Object() );
        TIMER_TABLE_REQUEST.put( key, timerTable );
        if ( tcpController.isConnected() ) {
            return sendAndLockMessage( key, wrapper.getMessage() );
        } else {
            if ( LOG.isDebugEnabled() ) {
                LOG.debug( "La conexión TCP no esta arriba para enviar peticiones" );
            }
            //Verify message type, only advice message must be send to saf
            throw new ISO8583Exception( CatalogError.TCP_NO_READY );
        }
    }

    @Override
    public void receive( String message ) {
        ISO iso = new DecodeISO8583( message ).getIso();
        if ( !verifyNetworkRequest( iso ) ) {
            Integer key = getSequence( iso );
            TimerTable timerTable = TIMER_TABLE_REQUEST.get( key );
            if ( timerTable != null ) {
                //OK
                timerTable.getTimer().stopTask();
                TIMER_TABLE_REQUEST.remove( key );
                TIMER_TABLE_RESPONSE.put( key, iso );
                if ( LOCK_OBJECT.get( key ) != null ) {
                    synchronized ( LOCK_OBJECT.get( key ) ) {
                        LOCK_OBJECT.get( key ).notifyAll();
                    }
                }
            } else {
                if ( isoUtils.isFinancialResponse( iso ) ) {
                    //Recursive call
                    ISO reversal = new Reversal0420( iso, ReversalCodes.RESPONSE_RECEIVED_TOO_LATE ).getIso();
                    try {
                        send( reversal );
                    } catch ( ISO8583Exception e ) {
                        LOG.error( e.getMessage() );
                        //SAF
                    }
                }
            }
        }
    }

    @Override
    public void notifyTimeOut( int sequence ) {
        synchronized ( LOCK_OBJECT.get( sequence ) ) {
            LOCK_OBJECT.get( sequence ).notifyAll();
            TIMER_TABLE_REQUEST.remove( sequence );
            if ( LOG.isInfoEnabled() ) {
                LOG.info( String.format( "Timeout trace %s", sequence ) );
            }
        }
    }

    //Cambio release/monitoreo
    @Override
    public void disconnected( String nodeName ) {
        nodeISOBuilder.changeOff( nodeName );
    }
    //Cambio release/monitoreo

    @Override
    public boolean isEcho() {
        return echoTask.isEchoStatus();
    }

    @Override
    public boolean isLogon() {
        return logon;
    }

    @Override
    public void startEcho() {
        echoTask.start();
    }

    @Override
    public void stopEcho() {
        echoTask.stop();
    }

    private int getSequence( ISO iso ) {
        return Integer.parseInt( iso.getDataElements().get( 10 ).getContentField() );
    }

    private ISO sendAndLockMessage( Integer key, String message ) throws ISO8583Exception {
        ISO response;
        tcpController.sendMessage( message );
        try {
            synchronized ( LOCK_OBJECT.get( key ) ) {
                lockMessage( key );
                boolean flag = true;
                while ( flag ) {
                    LOCK_OBJECT.get( key ).wait();
                    flag = false;
                }
            }
            unlockMessage( key );
            LOCK_OBJECT.remove( key );
            response = TIMER_TABLE_RESPONSE.get( key );
            TIMER_TABLE_RESPONSE.remove( key );
            if ( response == null ) {
                throw new ISO8583Exception( CatalogError.ISSUER_INOPERATIVE_TIMEOUT );
            }
        } catch ( InterruptedException ex ) {
            Thread.currentThread().interrupt();
            LOG.error( "Error grabe en los hilos del servicio" );
            throw new ISO8583Exception( CatalogError.THREAD_ERROR );
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

    private boolean verifyNetworkRequest( ISO iso ) {
        if ( isoUtils.isRed( iso ) && !echoTask.isEchoStatus() && isoUtils.isNetworkRequest( iso ) ) {
            setLogonFlag( iso );
            ISO networkResponse = new Request0810( iso ).getIso();
            tcpController.sendMessage( networkResponse.getMessage() );
            return true;
        } else {
            setLogonFlag( iso );
            return false;
        }
    }

    private void setLogonFlag( ISO iso ) {
        if ( isoUtils.isLogon( iso ) ) {
            logon = true;
        } else if ( isoUtils.isLogoff( iso ) ) {
            logon = false;
        }
    }

    @Override
    public String toString() {
        return "ISOController";
    }

    private static class Echo {

        private Timer timer = new Timer();
        private int i = 200000;
        private boolean echoStatus;
        private final int echoDelay;
        private final IISOController isoController;
        private final ITCPController tcpController;

        Echo( IISOController iisoController, ITCPController tcpController, int echoDelay ) {
            this.isoController = iisoController;
            this.tcpController = tcpController;
            this.echoDelay = echoDelay;
        }

        void start() {
            echoStatus = true;
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    if ( tcpController.isConnected() ) {
                        Map < String, String > dataElements = new LinkedHashMap <>();
                        dataElements.put( "p11", "" + i++ );
                        dataElements.put( "s70", "Echo-test" );
                        Request0800 rq0800 = new Request0800( dataElements );
                        ISO wrapper = rq0800.getIso();
                        i++;
                        try {
                            isoController.send( wrapper );
                        } catch ( ISO8583Exception e ) {
                            LOG.error( e.getMessage() );
                            stop();
                        }
                    } else {
                        LOG.error( "La conexión TCP no esta lista para los mensajes 0800" );
                        stop();
                    }
                }
            };
            timer.scheduleAtFixedRate( timerTask, 0, echoDelay );
        }

        void stop() {
            echoStatus = false;
            timer.cancel();
            timer.purge();
            timer = new Timer();
        }

        boolean isEchoStatus() {
            return echoStatus;
        }

    }
}
