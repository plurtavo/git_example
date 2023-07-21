package mx.com.prosa.nabhi.dash.business;

import mx.com.prosa.nabhi.misc.exception.socket.SocketException;

public interface ISockService {


    String sock( String app, String op, String node ) throws SocketException;
}
