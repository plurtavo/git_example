package mx.com.prosa.nabhi.iso.business.impl;

import com.dsapi.core.sockets.controller.ITCPController;
import com.dsapi.core.sockets.exception.TCPException;
import com.dsapi.core.sockets.factory.SocketFactory;
import com.dsapi.core.sockets.model.SocketWrap;
import mx.com.prosa.nabhi.iso.business.ISocketServices;
import mx.com.prosa.nabhi.iso.core.sockets.IISOController;
import mx.com.prosa.nabhi.iso.core.sockets.NodeISOBuilder;
import mx.com.prosa.nabhi.misc.domain.node.entity.NodeTCPEntity;
import mx.com.prosa.nabhi.misc.domain.node.repository.NodeTCPRepository;
import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.iso.ISO8583Exception;
import mx.com.prosa.nabhi.misc.exception.socket.SocketException;
import mx.com.prosa.nabhi.misc.model.sockets.TCPServiceInfo;
import mx.com.prosa.nabhi.misc.util.ISOUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SocketServices implements ISocketServices {

    private final SocketFactory socketFactory;
    private final NodeTCPRepository nodeTCPRepository;
    private final ModelMapper mapper;
    private final ISOUtils isoUtils;
    private final NodeISOBuilder nodeISOBuilder;
    private SocketWrap client;
    private ITCPController tcpController;
    private static final Logger LOG = LoggerFactory.getLogger( SocketServices.class );
    private static final String DISCONNECTED = "Disconnected";
    private static final String WAITING = "Waiting_Connection";
    private static final String CONNECTED = "Connected";

    @Autowired
    public SocketServices( SocketFactory socketFactory, NodeTCPRepository nodeTCPRepository, ModelMapper mapper, ISOUtils isoUtils, NodeISOBuilder nodeISOBuilder ) {
        this.socketFactory = socketFactory;
        this.nodeTCPRepository = nodeTCPRepository;
        this.mapper = mapper;
        this.isoUtils = isoUtils;
        this.nodeISOBuilder = nodeISOBuilder;
    }

    @Override
    public String onSocket( String nodeName ) throws SocketException {
        if ( getRequestedSocket( nodeName ) ) {
            try {
                if ( !validateConnection() ) {
                    startConnection();
                    tcpController.initClient( client.getSocket(), client.getIp(), client.getPort(), 1000, client.getName(), client.isSsl() );
                    client.setConnected( true );
                    okConnection();
                    //Cambio release/monitoreo
                    nodeISOBuilder.changeOn( nodeName );
                    //Cambio release/monitoreo
                    return String.format( "Socket TCP ISO con nombre %s iniciado", client.getName() );
                } else {
                    alreadyConnection();
                    throw new SocketException( CatalogError.SOCKET_ALREADY_ON );
                }
            } catch ( TCPException e ) {
                LOG.error( e.getMessage() );
                try {
                    reinstallSocket( client );
                    //Cambio release/monitoreo
                    nodeISOBuilder.changeOff( nodeName );
                    //Cambio release/monitoreo
                } catch ( TCPException e1 ) {
                    LOG.error( e1.getMessage() );
                }
            }
            throw new SocketException( CatalogError.SOCKET_ERROR_ON );
        } else {
            throw new SocketException( CatalogError.SOCKET_NO_EXIST );
        }
    }

    @Override
    public String offSocket( String nodeName ) throws SocketException {
        if ( getRequestedSocket( nodeName ) ) {
            try {
                if ( validateConnection() ) {
                    stopConnection( client );
                    stopConnection();
                    return String.format( "Socket TCP ISO con nombre %s apagado", client.getName() );
                } else {
                    if ( client.isConnected() ) {
                        tcpController.stopSocket();
                        client.setConnected( false );
                        stopConnection();
                        return String.format( "Socket TCP ISO con nombre %s apagado", client.getName() );
                    } else {
                        alreadyStopConnection();
                        throw new SocketException( CatalogError.SOCKET_ALREADY_OFF );
                    }
                }
            } catch ( TCPException e ) {
                LOG.error( e.getMessage() );
                throw new SocketException( CatalogError.SOCKET_ERROR_OFF );
            }
        } else {
            throw new SocketException( CatalogError.SOCKET_NO_EXIST );
        }
    }


    @Override
    public String logon( String nodeName ) throws ISO8583Exception, SocketException {
        if ( getRequestedSocket( nodeName ) ) {
            if ( validateConnectionAndNotLogon() ) {
                return sendLogon( nodeName );
            } else if ( validateConnectionAndLogon() ) {
                logExist( "Logon", nodeName );
                throw new SocketException( CatalogError.SOCKET_ALREADY_LOGON );
            } else {
                socketInvalidState( nodeName, "down" );
                throw new SocketException( CatalogError.SOCKET_NOT_ON );
            }
        } else {
            throw new SocketException( CatalogError.SOCKET_NO_EXIST );
        }
    }

    @Override
    public String logoff( String nodeName ) throws ISO8583Exception, SocketException {
        if ( getRequestedSocket( nodeName ) ) {
            if ( validateConnectionAndLogon() ) {
                return sendLogoff( nodeName );
            } else if ( validateConnectionAndNotLogon() ) {
                logExist( "Logoff", nodeName );
                throw new SocketException( CatalogError.SOCKET_NOT_LOGON );
            } else {
                socketInvalidState( nodeName, "up" );
                throw new SocketException( CatalogError.SOCKET_NOT_ON );
            }
        } else {
            throw new SocketException( CatalogError.SOCKET_NO_EXIST );
        }
    }

    @Override
    public String echo( String nodeName ) throws SocketException {
        if ( getRequestedSocket( nodeName ) ) {
            if ( validateConnectionAndLogon() ) {
                return sendEcho( nodeName );
            } else if ( validateConnectionAndNotLogon() ) {
                throw new SocketException( CatalogError.SOCKET_NOT_LOGON );
            } else {
                throw new SocketException( CatalogError.SOCKET_NOT_ON );
            }
        } else {
            throw new SocketException( CatalogError.SOCKET_NO_EXIST );
        }
    }

    @Override
    public String delete( String nodeName ) throws SocketException {
        try {
            if ( getRequestedSocket( nodeName ) ) {
                if ( validateConnection() ) {
                    stopConnection( client );
                }
                removeSocket( client );
                return String.format( "Socket TCP JKE con nombre %s eliminado", nodeName );
            } else {
                throw new SocketException( CatalogError.SOCKET_NO_EXIST );
            }
        } catch ( TCPException e ) {
            LOG.error( e.getMessage() );
            throw new SocketException( CatalogError.SOCKET_NO_EXIST );
        }

    }

    @Override
    public String create( String nodeName ) throws SocketException {
        if ( getRequestedSocket( nodeName ) ) {
            if ( LOG.isErrorEnabled() ) {
                LOG.error( String.format( "El socket TCP %s ya existe", nodeName ) );
            }
            throw new SocketException( CatalogError.SOCKET_ALREADY_EXIST );
        } else {
            nodeISOBuilder.buildSpecificSocket( nodeName );
            return String.format( "Socket TCP con nombre %s creado", nodeName );
        }
    }

    @Override
    public List < TCPServiceInfo >  getAllSocket() throws SocketException {
        TCPServiceInfo tcpServiceInfo = new TCPServiceInfo();
        List < TCPServiceInfo > list = new ArrayList <>();
        Map < String, SocketWrap > socket;
        try {
            socket = socketFactory.getSockets();
            if ( LOG.isDebugEnabled() ){
                LOG.debug( "Obteniendo lista de sockets tcp para ISO" );
            }
            for ( Map.Entry < String, SocketWrap > entry : socket.entrySet() ) {
                if ( !entry.getValue().getTcpController().isConnected() ) {
                    tcpServiceInfo.setStatus( DISCONNECTED );
                } else {
                    if ( entry.getValue().getTcpController().isConnected() ) {
                        tcpServiceInfo.setStatus( CONNECTED );
                    } else {
                        tcpServiceInfo.setStatus( WAITING );
                    }
                }
                updateTCPServiceInfo( tcpServiceInfo, entry );
                tcpServiceInfo.setController( entry.getValue().getTcpController().getTransactionController().toString() );
                list.add( tcpServiceInfo );
            }
            return list;
        } catch ( TCPException e ) {
            throw new SocketException( CatalogError.SOCKET_LIST_EMPTY );
        }
    }

    @Override
    public TCPServiceInfo getSingleSocket( String nodeName ) throws SocketException {
        TCPServiceInfo tcpServiceInfo;
        Optional < NodeTCPEntity > on = nodeTCPRepository.findById( nodeName );
        if ( on.isPresent() ) {
            SocketWrap socket;
            try {
                socket = socketFactory.getSocketByName( on.get().getNodeName(), on.get().getPort() );
                tcpServiceInfo = mapper.map( on.get(), TCPServiceInfo.class );
                if ( !socket.getTcpController().isConnected() ) {
                    tcpServiceInfo.setStatus( DISCONNECTED );
                } else {
                    if ( socket.getTcpController().isConnected() ) {
                        tcpServiceInfo.setStatus( CONNECTED );
                    } else {
                        tcpServiceInfo.setStatus( WAITING );
                    }
                }
                tcpServiceInfo.setController( socket.getTcpController().getTransactionController().toString() );
                return tcpServiceInfo;
            } catch ( TCPException e ) {
                throw new SocketException( CatalogError.SOCKET_NO_EXIST );
            }
        } else {
            throw new SocketException( CatalogError.SOCKET_NO_EXIST_DB );
        }
    }

    private boolean getRequestedSocket( String nodeName ) {
        try {
            Optional < NodeTCPEntity > on = nodeTCPRepository.findById( nodeName );
            if ( on.isPresent() ) {
                SocketWrap socketWrapper = socketFactory.getSocketByName( on.get().getNodeName(), on.get().getPort() );
                tcpController = socketWrapper.getTcpController();
                client = socketWrapper;
                if ( LOG.isDebugEnabled() ) {
                    LOG.debug( String.format( "Socket ISO %s encontrado", nodeName ) );
                }
                return true;
            } else {
                if ( LOG.isDebugEnabled() ) {
                    LOG.debug( String.format( "El Socket ISO %s no existe en la base de datos", nodeName ) );
                }
                return false;
            }
        } catch ( TCPException e ) {
            LOG.debug( String.format( "El Socket ISO%s no existe en la fabrica", nodeName ) );
            return false;
        }
    }

    private void updateTCPServiceInfo( TCPServiceInfo tcpServiceInfo, Map.Entry < String, SocketWrap > entry ) {
        tcpServiceInfo.setNodeName( entry.getKey().substring( entry.getKey().indexOf( ':' ) + 1 ) );
        tcpServiceInfo.setPort( Integer.parseInt( entry.getKey().substring( 0, entry.getKey().indexOf( ':' ) ) ) );
        tcpServiceInfo.setMode( "CLIENT" );
        tcpServiceInfo.setIp( entry.getValue().getIp() );
    }


    private boolean validateConnection() {
        return tcpController.isConnected() && client.isConnected();
    }

    private boolean validateConnectionAndLogon() {
        IISOController iisoController = ( IISOController ) tcpController.getTransactionController();
        return validateConnection() && iisoController.isLogon();
    }

    private boolean validateConnectionAndNotLogon() {
        IISOController iisoController = ( IISOController ) tcpController.getTransactionController();
        return validateConnection() && !iisoController.isLogon();
    }

    private String sendLogon( String nodeName ) throws ISO8583Exception {
        IISOController iisoController = ( IISOController ) tcpController.getTransactionController();
        iisoController.send( isoUtils.buildNetworkLogon() );
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "Logon enviado al node %s", nodeName ) );
        }
        return "Logon enviado";
    }

    private String sendLogoff( String nodeName ) throws ISO8583Exception {
        IISOController iisoController = ( IISOController ) tcpController.getTransactionController();
        iisoController.send( isoUtils.buildNetworkLogoff() );
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "Logoff enviado al node %s", nodeName ) );
        }
        return "Logoff enviado";
    }

    private String sendEcho( String nodeName ) throws SocketException {
        IISOController iisoController = ( IISOController ) tcpController.getTransactionController();
        if ( iisoController.isEcho() ) {
            throw new SocketException( CatalogError.ECHO_ALREADY_EXIST );
        } else {
            iisoController.startEcho();
            if ( LOG.isDebugEnabled() ) {
                LOG.debug( String.format( "Echo enviado al node %s", nodeName ) );
            }
            return "ECHO enviado";
        }

    }

    private void stopConnection( SocketWrap sock ) throws TCPException {
        sock.getTcpController().stopSocket();
        sock.setConnected( false );
        reinstallSocket( sock );
    }

    private void reinstallSocket( SocketWrap sock ) throws TCPException {
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "Reinstalando el socket iso %s", sock.getName() ) );
        }
        socketFactory.getSockets().remove( sock.getPort() + ":" + sock.getName() );
        //Cambio release/monitoreo
        nodeISOBuilder.unregister( sock.getName() );
        //Cambio release/monitoreo
        nodeISOBuilder.buildSpecificSocket( sock.getName() );
    }

    private void removeSocket( SocketWrap sock ) throws TCPException {
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "Removiendo el socket %s", sock.getName() ) );
        }
        //Cambio release/monitoreo
        nodeISOBuilder.unregister( sock.getName() );
        //Cambio release/monitoreo
        socketFactory.getSockets().remove( sock.getPort() + ":" + sock.getName() );
    }

    private void startConnection() {
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "Iniciando la comunicación con la ip %s puerto %s ssl %s", client.getIp(), client.getPort(), client.isSsl() ) );
        }
    }

    private void stopConnection() {
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "Deteniendo la comunicación con la ip %s puerto %s ssl %s", client.getIp(), client.getPort(), client.isSsl() ) );
        }
    }

    private void okConnection() {
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "Comunicación la ip %s puerto %s ssl %s", client.getIp(), client.getPort(), client.isSsl() ) );
        }
    }

    private void alreadyConnection() {
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "ISO-1: Ya existe la comunicación con la ip %s puerto %s ssl %s", client.getIp(), client.getPort(), client.isSsl() ) );
        }
    }

    private void alreadyStopConnection() {
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "ISO-2: La comunicación ya no existe con la ip %s puerto %s ssl %s", client.getIp(), client.getPort(), client.isSsl() ) );
        }
    }

    private void logExist( String op, String nodeName ) {
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "ISO-3: Ya existe un %s en el nodo %s", op, nodeName ) );
        }
    }

    private void socketInvalidState( String nodeName, String state ) {
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( String.format( "ISO-4: El nodo %s esta en un estado invalido %s para realizar esta operación", nodeName, state ) );
        }
    }

}
