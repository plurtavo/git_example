package mx.com.prosa.nabhi.iso.business.impl;

import com.dsapi.core.sockets.exception.TCPException;
import com.dsapi.core.sockets.factory.SocketFactory;
import com.dsapi.core.sockets.model.SocketWrap;
import mx.com.prosa.nabhi.iso.business.IISOService;
import mx.com.prosa.nabhi.iso.core.builder.ProcessingTransaction;
import mx.com.prosa.nabhi.iso.core.sockets.IISOController;
import mx.com.prosa.nabhi.misc.domain.node.entity.NodeTCPEntity;
import mx.com.prosa.nabhi.misc.domain.node.repository.NodeTCPRepository;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.iso.ISO8583Exception;
import mx.com.prosa.nabhi.misc.exception.socket.SocketException;
import mx.com.prosa.nabhi.misc.model.iso.ATMRequestModel;
import mx.com.prosa.nabhi.misc.model.iso.ATMResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.gonet.iso8583.constants.atm.TranCodes;
import us.gonet.serializable.data.ISO;

import java.util.Optional;

@Service
public class ISOService implements IISOService {

    private final SocketFactory socketsFactory;
    private final NodeTCPRepository nodeTCPRepository;
    private final ProcessingTransaction processingTransaction;
    private static final Logger LOG = LoggerFactory.getLogger( ISOService.class );

    @Autowired
    public ISOService( SocketFactory socketsFactory, NodeTCPRepository nodeTCPRepository, ProcessingTransaction processingTransaction ) {
        this.socketsFactory = socketsFactory;
        this.nodeTCPRepository = nodeTCPRepository;
        this.processingTransaction = processingTransaction;
    }

    @Override
    public ATMResponseModel sendTransaction( ATMRequestModel request, String nodeName ) throws ISO8583Exception, SocketException {
        TranCodes tranCode = TranCodes.getValue( request.getTranCode() );
        NodeTCPEntity entity = findSocket( nodeName );
        IISOController coreController = ( IISOController ) findSocket( entity ).getTcpController().getTransactionController();
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "Enviando una petición %s con secuencia %s al nodo %s", tranCode.getValue(), entity.getTracerNumber(), entity.getNodeName() ) );
        }
        ATMResponseModel response = processingTransaction.sendTransaction( tranCode, entity.getTracerNumber(), request.getNodeExchange(), request, coreController, entity.getZpk() );
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "Petición %s con secuencia %s con código de respuesta %s", tranCode.name(),
                    response.getMessage().getDataElements().get( 10 ).getContentField(),
                    response.getMessage().getDataElements().get( 38 ).getContentField() ) );
        }
        return response;
    }

    @Override
    public ATMResponseModel sendReversal( ISO request, String nodeName ) throws SocketException, ISO8583Exception {
        NodeTCPEntity entity = findSocket( nodeName );
        ATMResponseModel response = new ATMResponseModel();
        IISOController coreController = ( IISOController ) findSocket( entity ).getTcpController().getTransactionController();
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "Enviando una reverso con secuencia %s al nodo %s", entity.getTracerNumber(), entity.getNodeName() ) );
        }
        response.setMessage( coreController.send( request ) );
        return response;
    }


    private NodeTCPEntity findSocket( String nodeName ) throws SocketException {
        Optional < NodeTCPEntity > on = nodeTCPRepository.findById( nodeName );
        NodeTCPEntity entity;
        if ( on.isPresent() ) {
            entity = on.get();
            entity.setTracerNumber( entity.getTracerNumber() + 1 );
            nodeTCPRepository.save( entity );
            return entity;
        } else {
            if ( LOG.isDebugEnabled() ) {
                LOG.debug( String.format( "El socket TCP %s no existe en la base de datos", nodeName ) );
            }
            throw new SocketException( CatalogError.SOCKET_NO_EXIST_DB );
        }
    }

    private SocketWrap findSocket( NodeTCPEntity entity ) throws SocketException {
        try {
            SocketWrap socketWrapper = socketsFactory.getSocketByName( entity.getNodeName(), entity.getPort() );
            IISOController iisoController = ( IISOController ) socketWrapper.getTcpController().getTransactionController();
            if ( iisoController == null ) {
                if ( LOG.isDebugEnabled() ) {
                    LOG.debug( String.format( "No existe un controlador para este socket %s", entity.getNodeName() ) );
                }
                throw new SocketException( CatalogError.SOCKET_NOT_CONTROLLER );
            }
            if ( !socketWrapper.getTcpController().isConnected() ) {
                if ( LOG.isDebugEnabled() ) {
                    LOG.debug( String.format( "El nodo %s no esta conectando, por favor revise la configuración en la consola", entity.getNodeName() ) );
                }
                throw new SocketException( CatalogError.SOCKET_NOT_ON );
            }
            if ( !iisoController.isLogon() ) {
                if ( LOG.isDebugEnabled() ) {
                    LOG.debug( String.format( "No existe un logon con Base 24 y el socket %s", entity.getNodeName() ) );
                }
                throw new SocketException( CatalogError.SOCKET_NOT_LOGON );
            }
            return socketsFactory.getSocketByName( entity.getNodeName(), entity.getPort() );
        } catch ( TCPException e ) {
            if ( LOG.isDebugEnabled() ) {
                LOG.debug( String.format( "No existe el socket %s en la fabrica TCP", entity.getNodeName() ) );
            }
            throw new SocketException( CatalogError.SOCKET_NO_EXIST );
        }
    }
}
