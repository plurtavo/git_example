package mx.com.prosa.nabhi.jke.business;
//Cambio release/abc

import mx.com.prosa.nabhi.misc.controller.ISockClientServices;
import mx.com.prosa.nabhi.misc.exception.socket.SocketException;

public interface ISocketServices extends ISockClientServices {

    String delete( String nodeName ) throws SocketException;

    String create( String nodeName ) throws SocketException;
    //Cambio release/abc
}
