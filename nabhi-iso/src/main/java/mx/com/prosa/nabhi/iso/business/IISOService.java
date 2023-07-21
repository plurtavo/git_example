package mx.com.prosa.nabhi.iso.business;

import mx.com.prosa.nabhi.misc.exception.iso.ISO8583Exception;
import mx.com.prosa.nabhi.misc.exception.socket.SocketException;
import mx.com.prosa.nabhi.misc.model.iso.ATMRequestModel;
import mx.com.prosa.nabhi.misc.model.iso.ATMResponseModel;
import us.gonet.serializable.data.ISO;

public interface IISOService {

    ATMResponseModel sendTransaction( ATMRequestModel request, String nodeName ) throws ISO8583Exception, SocketException;
    ATMResponseModel sendReversal( ISO request, String nodeName ) throws ISO8583Exception, SocketException;
}
