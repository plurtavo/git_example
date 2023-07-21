package mx.com.prosa.nabhi.iso.core.sockets;

import com.dsapi.core.sockets.transactional.ITransactionalController;
import mx.com.prosa.nabhi.misc.exception.iso.ISO8583Exception;
import us.gonet.serializable.data.ISO;

public interface IISOController extends ITransactionalController {

    boolean isEcho();

    boolean isLogon();

    void startEcho();

    void stopEcho();

    ISO send( ISO message ) throws ISO8583Exception;


}
