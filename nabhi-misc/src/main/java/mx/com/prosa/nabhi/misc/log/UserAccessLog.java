package mx.com.prosa.nabhi.misc.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class UserAccessLog {

    private static final Logger LOGGER = LoggerFactory.getLogger( UserAccessLog.class );

    @Before( "@annotation( mx.com.prosa.nabhi.misc.log.UserAccess )" )
    public void before( JoinPoint joinPoint ) {
        HttpServletRequest request = (( ServletRequestAttributes ) RequestContextHolder.currentRequestAttributes()).getRequest();
        if ( LOGGER.isInfoEnabled() ){
            String user = SecurityContextHolder.getContext().getAuthentication().getName();
            if ( user != null ){
                LOGGER.info( String.format( "El usuario %s esta accediendo al recurso %s %s ", user, request.getMethod(), request.getRequestURI() ) );
            }
        }
        if ( LOGGER.isTraceEnabled() ){
            LOGGER.info( String.format( "Metodo en API %s", joinPoint.toString() ) );
        }
    }

    @After( "@annotation( mx.com.prosa.nabhi.misc.log.UserAccess )" )
    public void after( JoinPoint joinPoint ) {
        HttpServletRequest request = (( ServletRequestAttributes ) RequestContextHolder.currentRequestAttributes()).getRequest();
        if ( LOGGER.isInfoEnabled() ){
            String user = SecurityContextHolder.getContext().getAuthentication().getName();
            if ( user != null ){
                LOGGER.info( String.format( "El usuario %s esta saliendo del recurso %s %s ", user, request.getMethod(), request.getRequestURI() ) );
            }
        }
        if ( LOGGER.isTraceEnabled() ){
            LOGGER.info( String.format( "Metodo en API %s", joinPoint.toString() ) );
        }
    }

}
