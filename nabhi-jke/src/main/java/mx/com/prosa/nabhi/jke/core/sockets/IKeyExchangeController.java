package mx.com.prosa.nabhi.jke.core.sockets;

import com.dsapi.core.sockets.exception.TransactionException;
import com.dsapi.core.sockets.transactional.ITransactionalController;
import mx.com.prosa.nabhi.misc.model.jke.exchange.KeyRequest;
import mx.com.prosa.nabhi.misc.model.jke.exchange.KeyResponse;

public interface IKeyExchangeController extends ITransactionalController {

    KeyResponse send( KeyRequest keyRequest ) throws TransactionException;


}
