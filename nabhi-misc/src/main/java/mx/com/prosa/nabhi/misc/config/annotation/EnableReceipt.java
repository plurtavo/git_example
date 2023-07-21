package mx.com.prosa.nabhi.misc.config.annotation;

import mx.com.prosa.nabhi.misc.receipt.ContextRCPT;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
@Import( { ContextRCPT.class } )
public @interface EnableReceipt {
}
