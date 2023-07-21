package mx.com.prosa.nabhi.acv.websocket.controller;

import mx.com.prosa.nabhi.acv.model.OnLoadRequest;
import mx.com.prosa.nabhi.acv.model.OnLoadResponse;
import mx.com.prosa.nabhi.acv.websocket.business.IWebSocketService;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class WebSocketController {

    private static final Logger LOG = LoggerFactory.getLogger( WebSocketController.class );
    private final IWebSocketService iWebSocketService;

    @Autowired
    public WebSocketController( IWebSocketService iWebSocketService ) {
        this.iWebSocketService = iWebSocketService;
    }

    @MessageMapping( "/onload" )
    @SendTo( "/topic/startup" )
    public OnLoadResponse onload( final OnLoadRequest request ) throws ATMException {
        return iWebSocketService.onload( request );
    }

    @MessageMapping( "/disconnection" )
    public OnLoadResponse disconnection( final OnLoadRequest request ) throws ATMException {
        return iWebSocketService.disconnection( request );
    }

    @MessageExceptionHandler()
    public void errorHandler( Exception e, @Headers Map < String, Object > headers ) {
        if ( e.getMessage().equals( "Access is denied" ) ) {
            LOG.error( "Web Socket with session tried to access to but does not have permission" );
        }
    }

}