package mx.com.prosa.nabhi.misc.config;

import mx.com.prosa.nabhi.misc.domain.SpringContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import( SpringContext.class )
public class SpringConfig {
}
