package mx.com.prosa.nabhi.misc.config;

import com.dsapi.core.sockets.factory.SocketFactory;
import com.dsapi.core.sockets.factory.ssl.SSLBean;
import mx.com.prosa.nabhi.misc.domain.SpringContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan( "mx.com.prosa.nabhi.misc.controller" )
@Import( {  SocketFactory.class, SSLBean.class, SpringContext.class } )
public class SockConfig {
}
