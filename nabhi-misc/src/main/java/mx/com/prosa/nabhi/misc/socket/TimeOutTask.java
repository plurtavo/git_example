package mx.com.prosa.nabhi.misc.socket;

import com.dsapi.core.sockets.transactional.ITransactionalController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class TimeOutTask {

    private ITransactionalController transactionController;

    private int second;
    private final Timer timer;
    private int sequence;
    private static final Logger LOG = LoggerFactory.getLogger( TimeOutTask.class );
    private final TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if ( second == 0 ) {
                if ( LOG.isDebugEnabled() ) {
                    LOG.debug( String.format( "Timeout sequence: %d", sequence ) );
                }
                stopTask();
                transactionController.notifyTimeOut( sequence );
            } else {
                second--;
            }
        }
    };

    TimeOutTask( int sequence, int seconds, ITransactionalController transactionController ) {
        this.sequence = sequence;
        this.second = seconds;
        this.transactionController = transactionController;
        timer = new Timer();
    }

    void start() {
        if ( LOG.isDebugEnabled() ){
            LOG.debug( "Iniciando timer" );
        }
        timer.schedule( task, 0, 1000 );
    }

    public void stopTask() {
        timer.cancel();
        timer.purge();
    }

}
