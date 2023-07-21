package mx.com.prosa.nabhi.misc.config.annotation;

import mx.com.prosa.nabhi.misc.util.ConfigUtils;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
@Import( { ConfigUtils.class } )
public @interface EnableUtils {
}
