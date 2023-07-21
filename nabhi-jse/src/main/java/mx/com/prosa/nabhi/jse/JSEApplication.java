package mx.com.prosa.nabhi.jse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class JSEApplication extends SpringBootServletInitializer {


    @Override
    protected SpringApplicationBuilder configure( SpringApplicationBuilder application ) {
        return application.sources( JSEApplication.class );
    }

    public static void main( String[] args ) {
        SpringApplication.run( JSEApplication.class, args );
    }

}

