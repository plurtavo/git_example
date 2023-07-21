package mx.com.prosa.nabhi.misc.domain.config.annotation;

import mx.com.prosa.nabhi.misc.domain.config.SingleDomain;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
@Import( { SingleDomain.class } )
public @interface EnableSingleDomain {
}