package mx.com.prosa.nabhi.acv.config;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import mx.com.prosa.nabhi.misc.rest.SwaggerBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;

@Configuration
@EnableSwagger2
@Import( SwaggerBase.class )
public class SwaggerConfig implements WebMvcConfigurer {

    private final SwaggerBase base;
    public static final String TAG_1 = "Comandos de Cajeros Automáticos";
    public static final String TAG_2 = "Capturas de pantalla de Cajeros Automáticos";

    @Autowired
    public SwaggerConfig( SwaggerBase base ) {
        this.base = base;
    }

    @Bean
    public Docket swaggerPlugin() {
        //noinspection Guava
        return new Docket( DocumentationType.SWAGGER_2 )
                .select()
                .paths( PathSelectors.any() )
                .apis( Predicates.or( RequestHandlerSelectors
                                .basePackage( "mx.com.prosa.nabhi.acv" ),
                        RequestHandlerSelectors
                                .basePackage( "mx.com.prosa.nabhi.acv" ) ) )
                .build()
                .tags( new Tag( TAG_1, "Permite enviar comandos remotos al cajero automático" ) )
                .tags( new Tag( TAG_2, "Permite recibir capturas de pantalla desde el cajero automático y descargarlas" ) )
                .apiInfo( apiInfo() )
                .pathMapping( "/" )
                .forCodeGeneration( true )
                .genericModelSubstitutes( ResponseEntity.class )
                .ignoredParameterTypes( SpringDataWebProperties.Pageable.class )
                .ignoredParameterTypes( java.sql.Date.class )
                .directModelSubstitute( java.time.LocalDate.class, java.sql.Date.class )
                .directModelSubstitute( java.time.ZonedDateTime.class, Date.class )
                .directModelSubstitute( java.time.LocalDateTime.class, Date.class )
                .securityContexts( Lists.newArrayList( base.securityContext() ) )
                .securitySchemes( Lists.newArrayList( base.apiKey() ) )
                .useDefaultResponseMessages( false )
                .globalOperationParameters( base.signer() );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title( "ATM Control Viewfinder" )
                .description( "Micro servicio para el monitoreo y control remoto de los cajeros automáticos" )
                .termsOfServiceUrl( "" )
                .contact( new Contact( "Prosa", "https://www.prosamexico.mx/", "gustavo.mancilla@gonet.us" ) )
                .license( "Apache License Version 2.0" )
                .licenseUrl( "" ).version( "1.0" )
                .build();
    }

}
