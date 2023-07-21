package mx.com.prosa.nabhi.misc.config.annotation;

import mx.com.prosa.nabhi.misc.config.LogContext;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
@Import( { LogContext.class } )
public @interface EnableLog {
}
