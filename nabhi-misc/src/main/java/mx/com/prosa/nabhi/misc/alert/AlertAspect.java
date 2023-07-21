package mx.com.prosa.nabhi.misc.alert;

import mx.com.prosa.nabhi.misc.exception.CatalogError;
import mx.com.prosa.nabhi.misc.exception.ErrorCode;
import mx.com.prosa.nabhi.misc.model.ResponseError;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AlertAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger( AlertAspect.class );
    private final ModelMapper mapper = new ModelMapper();
    private static final ErrorCode ENUM = CatalogError.NO_ERROR;

    @AfterReturning( pointcut = "@annotation( mx.com.prosa.nabhi.misc.alert.Alert )", returning = "result" )
    public void logAfter( JoinPoint joinPoint, Object result ) {
        if ( result != null ) {
            ResponseEntity < ? > entity = ( ResponseEntity < ? > ) result;
            if ( entity.getStatusCode().value() == 500 ) {
                ResponseError payload = mapper.map( entity.getBody(), ResponseError.class );
                if ( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( String.format( "Se envía una respuesta con error %d y mensaje %s", payload.getErrorCode(), payload.getError() ) );
                }
                ErrorCode critical = ENUM.findByCodeAndCritical( payload.getErrorCode() );
                if ( critical != ENUM && LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( String.format( "Evento critico con código %d, se notifica por correo", payload.getErrorCode() ) );
                }
            }
        }
    }


}
