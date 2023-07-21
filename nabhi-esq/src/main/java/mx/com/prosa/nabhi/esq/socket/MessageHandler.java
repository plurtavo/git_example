package mx.com.prosa.nabhi.esq.socket;

import com.dsapi.core.sockets.controller.impl.ClientController;
import com.dsapi.core.sockets.exception.SSLContextException;
import com.dsapi.core.sockets.exception.TCPException;
import com.dsapi.core.sockets.factory.SocketFactory;
import com.dsapi.core.sockets.factory.ssl.StoreData;
import com.dsapi.core.sockets.model.SocketWrap;
import com.dsapi.core.sockets.transactional.ITransactionalController;
import mx.com.prosa.nabhi.esq.exception.SocketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.HealthIndicatorRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;

@Component
public class MessageHandler {

    @Value( "${socket.ip}" )
    private String ip;
    @Value( "${socket.puerto}" )
    private int port;
    @Value( "${socket.ssl}" )
    private boolean ssl;
    @Value( "${socket.ssl.location}" )
    private String location;
    @Value( "${socket.ssl.s}" )
    private String secret;

    private static final String ESQ = "ESQ";
    private static final Logger LOG = LoggerFactory.getLogger( MessageHandler.class );
    private boolean contextSSL = false;
    //Cambio release/monitoreo
    private final SocketFactory socketFactory;
    private final HealthIndicatorRegistry healthIndicatorRegistry;

    @Autowired
    public MessageHandler( SocketFactory socketFactory, HealthIndicatorRegistry healthIndicatorRegistry ) {
        this.socketFactory = socketFactory;
        this.healthIndicatorRegistry = healthIndicatorRegistry;
    }
    //Cambio release/monitoreo

    @PostConstruct
    private void init() {
        try {
            //Cambio release/monitoreo
            register();
            //Cambio release/monitoreo
            socketFactory.init();
            contextSSL = true;
            buildNode();
            SocketWrap socketWrap = getSocket();
            socketWrap.getTcpController().initClient( socketWrap.getSocket(), socketWrap.getIp(), socketWrap.getPort(), 1000, "ESQ", socketWrap.isSsl() );
            socketWrap.setConnected( true );
            //Cambio release/monitoreo
            changeOn( ESQ );
            //Cambio release/monitoreo

        } catch ( SSLContextException e ) {
            LOG.error( "Unable to load ssl context ESQ" );
        } catch ( TCPException e ) {
            LOG.error( String.format( "Unable to load socket %s configuration ESQ", ESQ ) );
            //Cambio release/monitoreo
            changeOff( ESQ );
            //Cambio release/monitoreo
        }
    }

    private void buildNode() throws TCPException {
        if ( ssl ) {
            if ( contextSSL ) {
                StoreData storeData = new StoreData();
                storeData.setJksLocation( location );
                storeData.setJksPass( secret );
                socketFactory.createSocket( "ESQ", ip, port, ssl, storeData );
            } else {
                if ( LOG.isErrorEnabled() ) {
                    LOG.error( String.format( "Unable to build ssl socket %s ESQ", ESQ ) );
                }
                //Cambio release/monitoreo
                return;
                //Cambio release/monitoreo
            }
        } else {
            socketFactory.createSocket( "ESQ", ip, port, false, null );
        }
        ClientController controller = new ClientController();
        //Cambio release/monitoreo
        controller.setTransactionController( new ITransactionalController() {
            @Override
            public void receive( String message ) {
                if ( LOG.isDebugEnabled() ){
                    LOG.debug( "receive" );
                }
            }

            @Override
            public void notifyTimeOut( int sequence ) {
                if ( LOG.isDebugEnabled() ){
                    LOG.debug( "timeout" );
                }
            }

            @Override
            public void disconnected( String nodeName ) {
                changeOff( ESQ );
            }
        } );
        //Cambio release/monitoreo
        getSocket().setTcpController( controller );
        if ( LOG.isInfoEnabled() ) {
            LOG.info( String.format( "Socket ESQ configured: Name: %s Port: %s Mode: Client", ip, port ) );
        }
    }

    private SocketWrap getSocket() throws TCPException {
        return socketFactory.getSocketByName( ESQ, port );
    }

    private void remove() throws TCPException {
        socketFactory.getSockets().remove( port + ":" + ESQ );
    }

    public void send( String message ) throws SocketException {
        try {
            SocketWrap wrap = getSocket();
            if ( !wrap.getTcpController().isConnected() ){
                wrap.setConnected( false );
                remove();
                init();
            }
            if ( wrap.isConnected() ) {
                wrap.getTcpController().sendMessage( message );
            } else {
                throw new SocketException( "Socket not open" );
            }
        } catch ( TCPException e ) {
            LOG.error( "Socket not exists" );
        }
    }

    @PreDestroy
    private void stop(){
        try {
            getSocket().getTcpController().stopSocket();
        } catch ( TCPException e ) {
            LOG.error( "Socket stop error" );
        }
    }

    //Cambio release/monitoreo
    public void register() {
        healthIndicatorRegistry.register( ESQ, () -> {
            Health.Builder status = Health.status( "CREATE" );
            status.withDetail( "IP", ip );
            status.withDetail( "PORT", port );
            status.withDetail( "TLS", ssl );
            return status.build();
        } );
    }

    public void unregister( String nodeName ){
        healthIndicatorRegistry.unregister( nodeName );
    }

    public void changeOn( String nodeName ){
        HealthIndicator indicator = healthIndicatorRegistry.get( nodeName );
        Map < String, Object > o = indicator.health().getDetails();
        unregister( nodeName );
        healthIndicatorRegistry.register( nodeName, () -> {
            Health.Builder status = Health.up();
            status.withDetails( o );
            return status.build();
        } );
    }

    public void changeOff( String nodeName ){
        HealthIndicator indicator = healthIndicatorRegistry.get( nodeName );
        Map < String, Object > o = indicator.health().getDetails();
        unregister( nodeName );
        healthIndicatorRegistry.register( nodeName, () -> {
            Health.Builder status = Health.down();
            status.withDetails( o );
            return status.build();
        } );
    }
//Cambio release/monitoreo

}

