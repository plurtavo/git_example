package mx.com.prosa.nabhi.misc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan( "mx.com.prosa.nabhi.misc.alert" )
public class AlertContext {
}
