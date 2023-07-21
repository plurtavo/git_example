package mx.com.prosa.nabhi.jke;

import mx.com.prosa.nabhi.jke.core.sockets.NodeJKEBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class JKEApplication extends SpringBootServletInitializer implements CommandLineRunner {

    private NodeJKEBuilder nodeJKEBuilder;

    @Autowired
    public void setNodeJKEBuilder( NodeJKEBuilder nodeJKEBuilder ) {
        this.nodeJKEBuilder = nodeJKEBuilder;
    }

    @Override
    protected SpringApplicationBuilder configure( SpringApplicationBuilder application ) {
        return application.sources( JKEApplication.class );
    }

    public static void main( String...args ){
        SpringApplication.run( JKEApplication.class, args );
    }

    @Override
    public void run( String... args ) {
        nodeJKEBuilder.build();
    }
}
