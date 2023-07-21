package mx.com.prosa.nabhi.acv.websocket.business;

import mx.com.prosa.nabhi.acv.model.OnLoadRequest;
import mx.com.prosa.nabhi.acv.model.OnLoadResponse;
import mx.com.prosa.nabhi.misc.exception.ATMException;

public interface IWebSocketService {

    OnLoadResponse onload( OnLoadRequest request ) throws ATMException;

    void onDown( String sessionId ) throws ATMException;

    OnLoadResponse disconnection( OnLoadRequest request ) throws ATMException;
}
