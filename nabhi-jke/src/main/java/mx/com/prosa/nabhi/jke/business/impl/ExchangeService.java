package mx.com.prosa.nabhi.jke.business.impl;
//Cambio release/abc

import com.dsapi.core.sockets.exception.TCPException;
import com.dsapi.core.sockets.exception.TransactionException;
import com.dsapi.core.sockets.factory.SocketFactory;
import com.dsapi.core.sockets.model.SocketWrap;
import mx.com.prosa.nabhi.jke.business.IExchangeService;
import mx.com.prosa.nabhi.jke.core.sockets.IKeyExchangeController;
import mx.com.prosa.nabhi.misc.domain.node.entity.NodeTCPEntity;
import mx.com.prosa.nabhi.misc.domain.node.repository.NodeTCPRepository;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.keys.KeyExchangeException;
import mx.com.prosa.nabhi.misc.exception.sanitize.SanitazeException;
import mx.com.prosa.nabhi.misc.exception.socket.SocketException;
import mx.com.prosa.nabhi.misc.model.jke.KeyRequest;
import mx.com.prosa.nabhi.misc.model.jke.exchange.*;
import mx.com.prosa.nabhi.misc.util.ISOUtils;
import mx.com.prosa.nabhi.misc.util.Sanitizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExchangeService implements IExchangeService {

    private final SocketFactory socketsFactory;
    private final NodeTCPRepository nodeTCPRepository;
    private final Sanitizer sanitizer;
    private final ISOUtils utilities;
    private static final Logger LOG = LoggerFactory.getLogger( ExchangeService.class );

    @Autowired
    public ExchangeService( SocketFactory socketsFactory, NodeTCPRepository nodeTCPRepository, Sanitizer sanitizer, ISOUtils utilities ) {
        this.socketsFactory = socketsFactory;
        this.nodeTCPRepository = nodeTCPRepository;
        this.sanitizer = sanitizer;
        this.utilities = utilities;
    }

    @Override
    public String exchange( KeyRequest keyRequest, String nodeName ) throws KeyExchangeException, SocketException {
        try {
            IKeyExchangeController controller = ( IKeyExchangeController ) findSocket( nodeName ).getTcpController().getTransactionController();
            if ( LOG.isDebugEnabled() ) {
                LOG.debug( String.format( "Realizando petición de intercambio de llaves al node %s, para el ATM %s", nodeName, keyRequest.getAtmRemote() ) );
            }
            KeyExchangeRequest keyExchangeRequest = new KeyExchangeRequest(
                    sanitizer.sanitize( keyRequest.getTermType() ),
                    sanitizer.sanitize( utilities.fillSpaces( keyRequest.getAtmRemote() ) ),
                    sanitizer.sanitize( utilities.fillSpaces( nodeName ) ),
                    utilities.randomSequence() );
            KeyResponse response = controller.send( keyExchangeRequest );
            if ( response == null ) {
                throw new KeyExchangeException( CatalogError.TIME_OUT );
            }
            KeyExchangeResponse ker = ( KeyExchangeResponse ) response;
            if ( ker.getError().equals( "0" ) ) {
                return ker.getMODE();
            } else {
                throw caseError( ker.getError() );
            }
        } catch ( SanitazeException e ) {
            throw new KeyExchangeException( CatalogError.SANITIZE_INPUT_ERROR );
        } catch ( TransactionException e ) {
            LOG.error( e.getMessage() );
            throw new KeyExchangeException( CatalogError.TIME_OUT );
        }
    }

    @Override
    public String translate( KeyRequest keyRequest, String nodeName ) throws KeyExchangeException, SocketException {
        try {
            IKeyExchangeController controller = ( IKeyExchangeController ) findSocket( nodeName ).getTcpController().getTransactionController();
            if ( LOG.isDebugEnabled() ) {
                LOG.debug( String.format( "Realizando petición de traslado de llaves al node %s, para el ATM %s", nodeName, keyRequest.getAtmRemote() ) );
            }
            TranslatePinBlockRequest pinBlockRequest = new TranslatePinBlockRequest(
                    sanitizer.sanitize( keyRequest.getTermType() ),
                    sanitizer.sanitize( utilities.fillSpaces( keyRequest.getAtmRemote() ) ),
                    sanitizer.sanitize( utilities.fillSpaces( keyRequest.getAtmLocal() ) ),
                    sanitizer.sanitize( keyRequest.getPinBlock() ),
                    sanitizer.sanitize( keyRequest.getTrack2() ),
                    sanitizer.sanitize( keyRequest.getIpk() ),
                    utilities.randomSequence() );
            KeyResponse response = controller.send( pinBlockRequest );
            if ( response == null ) {
                throw new KeyExchangeException( CatalogError.TIME_OUT );
            }
            TranslatePinBlockResponse tpb = ( TranslatePinBlockResponse ) response;
            if ( tpb.gerError().equals( "0" ) ) {
                return tpb.getBLOCK();
            } else {
                throw caseError( tpb.gerError() );
            }
        } catch ( SanitazeException e ) {
            throw new KeyExchangeException( CatalogError.SANITIZE_INPUT_ERROR );
        } catch ( TransactionException e ) {
            LOG.error( e.getMessage() );
            throw new KeyExchangeException( CatalogError.TIME_OUT );
        }
    }


    private SocketWrap findSocket( String nodeName ) throws SocketException {
        Optional < NodeTCPEntity > on = nodeTCPRepository.findById( nodeName );
        if ( on.isPresent() ) {
            try {
                SocketWrap socketWrapper = socketsFactory.getSocketByName( on.get().getNodeName(), on.get().getPort() );
                IKeyExchangeController exchangeController = ( IKeyExchangeController ) socketWrapper.getTcpController().getTransactionController();
                if ( exchangeController == null ) {
                    if ( LOG.isErrorEnabled() ) {
                        LOG.error( String.format( "No se encuentra un controlador para el socket %s", nodeName ));
                    }
                    throw new SocketException( CatalogError.SOCKET_NOT_CONTROLLER );
                }
                if ( !socketWrapper.getTcpController().isConnected() ) {
                    if ( LOG.isErrorEnabled() ) {
                        LOG.error( String.format( "El socket %s no esta conectado", nodeName ));
                    }
                    throw new SocketException( CatalogError.SOCKET_NOT_ON );
                }
                return socketsFactory.getSocketByName( on.get().getNodeName(), on.get().getPort() );
            } catch ( TCPException e ) {
                LOG.error( e.getMessage() );
                throw new SocketException( CatalogError.SOCKET_NO_EXIST );
            }
        } else {
            logSocketDataBase( nodeName );
            throw new SocketException( CatalogError.SOCKET_NO_EXIST_DB );
        }
    }

    private void logSocketDataBase( String nodeName ) {
        if ( LOG.isDebugEnabled() ) {
            LOG.error( String.format( "El socket %s no se encuentra en la base de datos", nodeName ));
        }
    }

    private KeyExchangeException caseError( String code ) {
        if ( LOG.isDebugEnabled() ) {
            switch ( code ) {
                case "1":
                    return new KeyExchangeException( CatalogError.DEVICE_ERROR );
                case "2":
                    return new KeyExchangeException( CatalogError.IPK_ERROR );
                case "3":
                    return new KeyExchangeException( CatalogError.DATA_ERROR );
                case "4":
                    return new KeyExchangeException( CatalogError.DATA_ERROR_2 );
                case "5":
                    return new KeyExchangeException( CatalogError.SANITIZE_ERROR );
                case "6":
                    return new KeyExchangeException( CatalogError.DATABASE_ERROR );
                default:
                    return new KeyExchangeException( CatalogError.UNKNOWN_ERROR );
            }
        }
        return new KeyExchangeException( CatalogError.UNKNOWN_ERROR );
    }
//Cambio release/abc
}
