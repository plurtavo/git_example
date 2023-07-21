package mx.com.prosa.nabhi.iso;

import mx.com.prosa.nabhi.iso.core.sockets.NodeISOBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ISOApplication extends SpringBootServletInitializer implements CommandLineRunner {

    private NodeISOBuilder nodeISOBuilder;

    @Autowired
    public void setNodeISOBuilder( NodeISOBuilder nodeISOBuilder ) {
        this.nodeISOBuilder = nodeISOBuilder;
    }

    @Override
    protected SpringApplicationBuilder configure( SpringApplicationBuilder application ) {
        return application.sources( ISOApplication.class );
    }

    @Override
    public void run( String... args ) {
        nodeISOBuilder.build();
    }

    public static void main( String...args ){
        SpringApplication.run( ISOApplication.class, args );
    }
}
