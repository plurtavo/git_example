package mx.com.prosa.nabhi.misc.domain.complete.entity;

import mx.com.prosa.nabhi.misc.domain.SpringContext;
import mx.com.prosa.nabhi.misc.domain.log.entity.LogEventEntity;
import mx.com.prosa.nabhi.misc.domain.log.repository.LogEventRepository;
import mx.com.prosa.nabhi.misc.domain.node.entity.NodeTCPEntity;
import mx.com.prosa.nabhi.misc.domain.personalized.entity.*;
import mx.com.prosa.nabhi.misc.domain.security.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Transactional( propagation = Propagation.REQUIRES_NEW )
public class AuditTable {

    private static final Logger LOG = LoggerFactory.getLogger( AuditTable.class );

    @PostUpdate
    public void afterUpdateOperation( Object o ) {
        //Cambio release/eventos
        instance( o, "actualizo" );
        //Cambio release/eventos
    }

    @PostPersist
    public void afterCreatedOperation( Object o ) {
        //Cambio release/eventos
        instance( o, "creo" );
        //Cambio release/eventos
    }

    @PostRemove
    public void afterDeletedOperation( Object o ) {
        //Cambio release/eventos
        instance( o, "elimino" );
        //Cambio release/eventos
    }

    private void instance( Object o, String op ) {
        if ( o instanceof APCEntity ) {
            log( ( ( APCEntity ) o ).getId(), "apc", op );
        } else if ( o instanceof ATDEntity ) {
            log( ( ( ATDEntity ) o ).getTerminalId(), "atd", op );
        } else if ( o instanceof BINEntity ) {
            log( ( ( BINEntity ) o ).getId(), "bin", op );
        } else if ( o instanceof IDFEntity ) {
            log( ( ( IDFEntity ) o ).getFiid(), "idf", op );
        } else if ( o instanceof ImageEntity ) {
            log( ( ( ImageEntity ) o ).getName(), "image", op );
        } else if ( o instanceof JournalEntity ) {
            log( ( ( JournalEntity ) o ).getId(), "journal", op );
        } else if ( o instanceof RCPTEntity ) {
            log( ( ( RCPTEntity ) o ).getId(), "rcpt", op );
        } else if ( o instanceof ScreenGroupEntity ) {
            log( ( ( ScreenGroupEntity ) o ).getGroupId(), "screenGroup", op );
        } else {
            instance2( o, op );
        }

    }

    private void instance2( Object o, String op ) {
        if ( o instanceof SurchargeEntity ) {
            log( ( ( SurchargeEntity ) o ).getSurchargeId().toString(), "surcharge", op );
        } else if ( o instanceof NodeTCPEntity ) {
            log( ( ( NodeTCPEntity ) o ).getNodeName(), "nodeTcp", op );
        } else if ( o instanceof IDFSurchargeEntity ) {
            log( ( ( IDFSurchargeEntity ) o ).getFiid(), "idf_surcharge", op );
        } else if ( o instanceof UserEntity ) {
            log( ( ( UserEntity ) o ).getId(), "user", op );
        }
    }

    private void log( Object o, String table, String op ) {
        SecurityContext context = SecurityContextHolder.getContext();
        HttpServletRequest request = ( ( ServletRequestAttributes ) Objects.requireNonNull( RequestContextHolder.getRequestAttributes() ) ).getRequest();
        if ( LOG.isInfoEnabled() && context.getAuthentication() != null ) {
            StringBuilder builder = new StringBuilder();
            String host = "OPEN-SHIFT";
            String ip = "";
            try {
                host = InetAddress.getLocalHost().getHostName();
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch ( UnknownHostException e ) {
                LOG.error( "Unknown Host" );
            }
            String ip2 = request.getRemoteAddr();
            String name = context.getAuthentication().getName();
            // TODO: 21/07/2021 Ver autoridades
            if ( true ) {
                LogEventRepository logEventRepository = ( LogEventRepository ) SpringContext.getBean( "logEventRepository" );
                LogEventEntity event = new LogEventEntity();
                event.setTime( new java.sql.Date( new Date().getTime() ) );
                event.setHostName( host );
                event.setAppName( "NABHI" );
                event.setIpHost( ip );
                event.setRemoteHostName( request.getRemoteHost() );
                event.setRemoteHostIp( request.getRemoteAddr() );
                event.setRemoteHostPort( String.valueOf( request.getRemotePort() ) );
                event.setEndPoint( request.getRequestURI() );
                event.setUserName( name );
                //Cambio release/eventos
                event.setEvent( String.format( "El usuario %s %s el registro con id %s de la tabla %s desde la IP %s", name, op, o, table, ip2 ) );
                //Cambio release/eventos
                logEventRepository.save( event );
            } else {
                builder
                        .append( "[" )
                        .append( new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" ).format( new Date() ) )
                        .append( "]" )
                        .append( ";" )
                        .append( host )
                        .append( ";" )
                        .append( "NABHI" )
                        .append( ";" )
                        .append( ip )
                        .append( ";" )
                        .append( request.getRemoteHost() )
                        .append( ";" )
                        .append( request.getRemoteAddr() )
                        .append( ";" )
                        .append( request.getRemotePort() )
                        .append( ";" )
                        .append( request.getRequestURI() )
                        .append( ";" )
                        .append( name )
                        .append( ";" )
                        .append( name )
                        .append( ";" )
                        .append( String.format( "User %s %s the %s record by id %s from the ip %s", name, op, table, o, ip2 ) );
                LOG.info( builder.toString() );
            }

        }
    }
}
