package mx.com.prosa.nabhi.misc.controller;

//Cambio release/abc

import mx.com.prosa.nabhi.misc.exception.socket.SocketException;
import mx.com.prosa.nabhi.misc.model.sockets.TCPServiceInfo;

import java.util.List;

public interface ISockClientServices {

    //Cambio release/abc
    String onSocket( String nodeName ) throws SocketException;

    String offSocket( String nodeName ) throws SocketException;

    List < TCPServiceInfo > getAllSocket() throws SocketException;

    TCPServiceInfo getSingleSocket( String nodeName ) throws SocketException;
    //Cambio release/abc
}
