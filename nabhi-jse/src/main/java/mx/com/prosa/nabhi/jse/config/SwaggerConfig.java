package mx.com.prosa.nabhi.jse.config;

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

    public static final String TAG_1 = "Notificaciones de cajeros";
    public static final String TAG_2 = "Intercambio de llaves";
    public static final String TAG_3 = "Capacidades del cajero";
    public static final String TAG_4 = "Acciones de tarjeta";
    public static final String TAG_5 = "Datos del ticket";
    public static final String TAG_6 = "Peticiones de autorización";
    public static final String TAG_7 = "Validación montos";
    private final SwaggerBase base;

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
                                .basePackage( "mx.com.prosa.nabhi.jse" ),
                        RequestHandlerSelectors
                                .basePackage( "mx.com.prosa.nabhi.jse" ) ) )
                .build()
                //Cambio release/abc
                .tags( new Tag( TAG_1, "Recibe notificaciones de los dispositivos de cajeros automáticos y modo supervisor" ) )
                .tags( new Tag( TAG_2, "Permite solicitar una nueva llave TPK para los cajeros automáticos" ) )
                .tags( new Tag( TAG_3, "Permite consultar la publicidad e imágenes del cajero así como sus capacidades transaccionales" ) )
                .tags( new Tag( TAG_4, "Identifica la tarjeta introducida y retirada así como el tipo de tarjeta" ) )
                .tags( new Tag( TAG_5, "Maneja los datos a imprimir del ticket" ) )
                .tags( new Tag( TAG_6, "Gestiona todas las peticiones para autorizar las transacciones disponibles" ) )
                .tags( new Tag( TAG_7, "Verifica los montos para retiros validos" ) )
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
                .globalOperationParameters( base.signer() )
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title( "API REST Middleware ATM" )
                .description( "Manage ATMS connected to the cloud by high-performance dynamic load" )
                .termsOfServiceUrl( "Terminos y condiciones" )
                .contact( new Contact( "Glints S.A. de C.V.", "www.glints.com.mx", "gustavo.mancilla@gonet.us" ) )
                .license( "Apache License Version 2.0" )
                .licenseUrl( "" ).version( "1.0" )
                .build();
    }

}
