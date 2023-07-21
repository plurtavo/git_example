package mx.com.prosa.nabhi.jke.config;

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

    //Cambio release/abc
    private final SwaggerBase base;
    public static final String TAG_1 = "Criptografía";
    public static final String TAG_2 = "Operación de Sockets TCP";
    //Cambio release/abc


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
                                .basePackage( "mx.com.prosa.nabhi.jke" ),
                        RequestHandlerSelectors
                                .basePackage( "mx.com.prosa.nabhi.misc.controller" ) ) )
                .build()
                //Cambio release/abc
                .tags( new Tag( TAG_1, "Permite generar llaves 3DES y Trasladar PIN BLOC" ) )
                .tags( new Tag( TAG_2, "Permite operar sockets TCP contra un Host" ) )
                //Cambio release/abc
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
        return new ApiInfoBuilder().title( "Java Key Exchange" )
                .description( "API REST Interface with HSM Thales B24" )
                .termsOfServiceUrl( "" )
                .contact( new Contact( "Glints S.A. de C.V.", "www.glints.com.mx", "gustavo.mancilla@gonet.us" ) )
                .license( "Apache License Version 2.0" )
                .licenseUrl( "" ).version( "1.0" )
                .build();
    }



}
