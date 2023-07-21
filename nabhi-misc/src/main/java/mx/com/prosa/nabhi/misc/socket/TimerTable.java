package mx.com.prosa.nabhi.misc.socket;

import com.dsapi.core.sockets.transactional.ITransactionalController;

public class TimerTable {

    private final int sequence;
    private final TimeOutTask timer;

    public TimerTable( int sequence, int seconds, ITransactionalController transactionController ) {
        this.sequence = sequence;
        timer = new TimeOutTask( sequence, seconds, transactionController );
        timer.start();
    }

    public int getSequence() {
        return sequence;
    }

    public TimeOutTask getTimer() {
        return timer;
    }
}
