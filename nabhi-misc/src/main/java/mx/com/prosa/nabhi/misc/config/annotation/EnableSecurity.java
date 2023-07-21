package mx.com.prosa.nabhi.misc.config.annotation;

import mx.com.prosa.nabhi.misc.security.WebSecurityConfig;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
@EnableWebSecurity
@EnableAspectJAutoProxy
@EnableGlobalMethodSecurity( prePostEnabled = true )
@Import( WebSecurityConfig.class )
public @interface EnableSecurity {
}
