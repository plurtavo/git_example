package mx.com.prosa.nabhi.iso.core.sockets;

import com.dsapi.core.sockets.controller.impl.ClientController;
import com.dsapi.core.sockets.exception.SSLContextException;
import com.dsapi.core.sockets.exception.TCPException;
import com.dsapi.core.sockets.factory.SocketFactory;
import com.dsapi.core.sockets.factory.ssl.StoreData;
import com.dsapi.core.sockets.model.SocketWrap;
import mx.com.prosa.nabhi.iso.core.handler.ISOManager;
import mx.com.prosa.nabhi.misc.domain.node.entity.NodeTCPEntity;
import mx.com.prosa.nabhi.misc.domain.node.repository.NodeTCPRepository;
import mx.com.prosa.nabhi.misc.util.ISOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.HealthIndicatorRegistry;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class NodeISOBuilder {

    @Value( "${app.ssl.jke.name}" )
    private String jksName;
    @Value( "${app.ssl.jke.pass}" )
    private String jksPass;

    private static final Logger LOG = LoggerFactory.getLogger( NodeISOBuilder.class );
    //Cambio release/monitoreo
    private final NodeTCPRepository nodeTCPRepository;
    private final SocketFactory socketsFactory;
    private final ISOUtils isoUtils;
    private final HealthIndicatorRegistry healthIndicatorRegistry;
    //Cambio release/monitoreo
    private boolean contextSSL = false;

    //Cambio release/monitoreo
    @Autowired
    public NodeISOBuilder( NodeTCPRepository nodeTCPRepository, SocketFactory socketsFactory, ISOUtils isoUtils, HealthIndicatorRegistry healthIndicatorRegistry ) {
        this.nodeTCPRepository = nodeTCPRepository;
        this.socketsFactory = socketsFactory;
        this.isoUtils = isoUtils;
        this.healthIndicatorRegistry = healthIndicatorRegistry;
    }
    //Cambio release/monitoreo

    public void build() {
        try {
            socketsFactory.init();
            contextSSL = true;
            if ( LOG.isDebugEnabled() ){
                LOG.debug( "Inicio correctamete el contexto fabrica de nodos" );
            }
        } catch ( SSLContextException e ) {
            LOG.error( "Unable to load ssl context ISO" );
        }
        List < NodeTCPEntity > entities = nodeTCPRepository.findByNodeType( "HISO" );
        for ( NodeTCPEntity s : entities ) {
            try {
                buildNode( s );
            } catch ( TCPException e ) {
                LOG.error( String.format( "Unable to load socket %s configuration ISO", s.getNodeName() ) );
            }
        }
    }

    public void buildSpecificSocket( String nodeName ) {
        try {
            Optional < NodeTCPEntity > entity = nodeTCPRepository.findById( nodeName );
            if ( entity.isPresent() ) {
                NodeTCPEntity s = entity.get();
                buildNode( s );
            }
        } catch ( TCPException e ) {
            LOG.error( String.format( "Unable to load socket %s configuration ISO", nodeName ) );
        }
    }

    private void buildNode( NodeTCPEntity s ) throws TCPException {
        if ( s.isSslEnable() ) {
            if ( contextSSL ) {
                StoreData storeData = new StoreData();
                storeData.setJksLocation( jksName );
                storeData.setJksPass( jksPass );
                socketsFactory.createSocket( s.getNodeName(), s.getIp(), s.getPort(), s.isSslEnable(), storeData );
            } else {
                if ( LOG.isErrorEnabled() ) {
                    LOG.error( String.format( "Unable to build ssl socket %s ISO", s.getNodeName() ) );
                }
                //Cambio release/monitoreo
                return;
                //Cambio release/monitoreo
            }
        } else {
            socketsFactory.createSocket( s.getNodeName(), s.getIp(), s.getPort(), false, null );
        }
        if ( s.getController().equals( "ISOController" ) ) {
            ClientController controller = new ClientController();
            //Cambio release/monitoreo
            controller.setTransactionController( new ISOManager( controller, isoUtils, s.getEchoDelay(), s.getTimeout(), this ) );
            //Cambio release/monitoreo
            SocketWrap wrap = socketsFactory.getSocketByName( s.getNodeName(), s.getPort() );
            wrap.setTcpController( controller );
            if ( LOG.isInfoEnabled() ) {
                LOG.info( String.format( "Socket ISO configured: Name: %s Port: %s Mode: Client", s.getNodeName(), s.getPort() ) );
            }
            //Cambio release/monitoreo
            register( s );
            //Cambio release/monitoreo
        }
    }

    //Cambio release/monitoreo
    public void register( NodeTCPEntity s ) {
        healthIndicatorRegistry.register( s.getNodeName(), () -> {
            Health.Builder status = Health.status( "CREATE" );
            status.withDetail( "IP", s.getIp() );
            status.withDetail( "PORT", s.getPort() );
            status.withDetail( "TLS", s.isSslEnable() );
            return status.build();
        } );
    }

    public void unregister( String nodeName ) {
        healthIndicatorRegistry.unregister( nodeName );
    }

    public void changeOn( String nodeName ) {
        HealthIndicator indicator = healthIndicatorRegistry.get( nodeName );
        Map < String, Object > o = indicator.health().getDetails();
        unregister( nodeName );
        healthIndicatorRegistry.register( nodeName, () -> {
            Health.Builder status = Health.up();
            status.withDetails( o );
            return status.build();
        } );
    }

    public void changeOff( String nodeName ) {
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
