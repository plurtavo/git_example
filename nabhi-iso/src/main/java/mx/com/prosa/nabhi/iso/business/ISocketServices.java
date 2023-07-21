package mx.com.prosa.nabhi.iso.business;

import mx.com.prosa.nabhi.misc.controller.ISockClientServices;
import mx.com.prosa.nabhi.misc.exception.iso.ISO8583Exception;
import mx.com.prosa.nabhi.misc.exception.socket.SocketException;

public interface ISocketServices extends ISockClientServices {

    String logon( String nodeName ) throws SocketException, ISO8583Exception;

    String logoff( String nodeName ) throws SocketException, ISO8583Exception;

    String echo( String nodeName ) throws SocketException;

    String delete( String nodeName ) throws SocketException;

    String create( String nodeName ) throws SocketException;
}
