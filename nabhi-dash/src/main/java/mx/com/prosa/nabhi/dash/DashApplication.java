package mx.com.prosa.nabhi.dash;

//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
//Cambio release/redcat
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
public class DashApplication extends SpringBootServletInitializer implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger( DashApplication.class );
    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet end

    @Override
    protected SpringApplicationBuilder configure( SpringApplicationBuilder application ) {
        return application.sources( DashApplication.class );
    }

    public static void main( String[] args ) {
        SpringApplication.run( DashApplication.class, args );
    }


    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
    @Override
    public void run( String... args ){
        if ( LOG.isDebugEnabled() ){
            LOG.debug( "Actualizacion de LOGs" );
        }

    }
    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet end
}
