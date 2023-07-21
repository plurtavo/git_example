package mx.com.prosa.nabhi.jke.business;
//Cambio release/abc
import mx.com.prosa.nabhi.misc.exception.keys.KeyExchangeException;
import mx.com.prosa.nabhi.misc.exception.socket.SocketException;
import mx.com.prosa.nabhi.misc.model.jke.KeyRequest;

public interface IExchangeService {

    String exchange( KeyRequest keyRequest, String nodeName ) throws KeyExchangeException, SocketException;

    String translate( KeyRequest keyRequest, String nodeName ) throws KeyExchangeException, SocketException;
//Cambio release/abc
}

