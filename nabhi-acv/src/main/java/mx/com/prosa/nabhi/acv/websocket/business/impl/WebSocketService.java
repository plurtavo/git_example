package mx.com.prosa.nabhi.acv.websocket.business.impl;

import mx.com.prosa.nabhi.acv.model.OnLoadRequest;
import mx.com.prosa.nabhi.acv.model.OnLoadResponse;
import mx.com.prosa.nabhi.acv.websocket.business.IWebSocketService;
import mx.com.prosa.nabhi.misc.domain.acv.entity.ATDForLogEntity;
import mx.com.prosa.nabhi.misc.domain.acv.entity.ATMLogConectionEntity;
import mx.com.prosa.nabhi.misc.domain.acv.repository.ATDForLogRepository;
import mx.com.prosa.nabhi.misc.domain.acv.repository.ATMLogConnectionRepository;
import mx.com.prosa.nabhi.misc.exception.ATMException;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class WebSocketService implements IWebSocketService {

    private static final Logger LOGGER = LoggerFactory.getLogger( WebSocketService.class );
    private final ATMLogConnectionRepository atmLogConnectionRepository;
    private final ATDForLogRepository atdRepository;
    private static final String ATM_NOT_EXIST_CONNECTION = "El cajero no existe o el log no existe, no se puede asociar la sesión al cajero";
    private static final String ATM_NOT_EXIST_DISCONNECTION = "El cajero no existe o el log no existe, no se puede asociar la sesión al cajero";

    @Autowired
    public WebSocketService( ATMLogConnectionRepository atmLogConnectionRepository, ATDForLogRepository atdRepository ) {
        this.atmLogConnectionRepository = atmLogConnectionRepository;
        this.atdRepository = atdRepository;
    }

    @Override
    public OnLoadResponse onload( OnLoadRequest request ) throws ATMException {
        Optional < ATDForLogEntity > optional = atdRepository.findById( request.getTerminalId() );
        Optional < ATMLogConectionEntity > optional1 = atmLogConnectionRepository.findAllBySessionId( request.getSessionId() );
        if ( optional.isPresent() && optional1.isPresent() ) {
            ATMLogConectionEntity entity = optional1.get();
            entity.setAtd( optional.get() );
            atmLogConnectionRepository.save( entity );
            ATDForLogEntity atdEntity = optional.get();
            atdEntity.setOnline( true );
            atdRepository.save( atdEntity );
            OnLoadResponse response = new OnLoadResponse();
            response.setTerminalId( request.getTerminalId() );
            return response;
        } else {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( ATM_NOT_EXIST_CONNECTION );
            }
            throw new ATMException( CatalogError.ATM_NOT_EXIST );
        }
    }


    @Override
    public void onDown( String sessionId ) throws ATMException {
        Optional < ATMLogConectionEntity > optional1 = atmLogConnectionRepository.findAllBySessionId( sessionId );
        if ( optional1.isPresent() ) {
            ATDForLogEntity atdEntity = optional1.get().getAtd();
            atdEntity.setOnline( false );
            atdRepository.save( atdEntity );
        } else {
            if ( LOGGER.isErrorEnabled() ) {
                LOGGER.error( ATM_NOT_EXIST_CONNECTION );
            }
            throw new ATMException( CatalogError.ATM_NOT_EXIST );
        }
    }


    @Override
    public OnLoadResponse disconnection( OnLoadRequest request ) throws ATMException {
        final DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        Optional < ATDForLogEntity > optional = atdRepository.findById( request.getTerminalId() );
        Optional < ATMLogConectionEntity > optional1 = atmLogConnectionRepository.findAllBySessionId( request.getSessionId() );
        if ( optional.isPresent() && optional1.isPresent() ) {
            ATMLogConectionEntity entity = optional1.get();
            entity.setAtd( optional.get() );
            try {
                Date date = dateFormat.parse( request.getTimestamp() );
                entity.setDown( new Timestamp( date.getTime() ) );
                atmLogConnectionRepository.save( entity );
            } catch ( ParseException e ) {
                LOGGER.error( ATM_NOT_EXIST_DISCONNECTION );
                throw new ATMException( CatalogError.ATM_NOT_EXIST );
            }
            ATDForLogEntity atdEntity = optional1.get().getAtd();
            atdEntity.setOnline( false );
            atdRepository.save( atdEntity );
            OnLoadResponse response = new OnLoadResponse();
            response.setTerminalId( request.getTerminalId() );
            return response;
        } else {
            throw new ATMException( CatalogError.ATM_NOT_EXIST );
        }
    }
}
