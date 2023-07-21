package mx.com.prosa.nabhi.esq;

import com.dsapi.core.sockets.factory.SocketFactory;
import com.dsapi.core.sockets.factory.ssl.SSLBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import( { SSLBean.class, SocketFactory.class } )
public class ESQApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure( SpringApplicationBuilder application ) {
        return application.sources( ESQApplication.class );
    }

    public static void main( String ... args ){
        SpringApplication.run( ESQApplication.class, args );
    }

}
