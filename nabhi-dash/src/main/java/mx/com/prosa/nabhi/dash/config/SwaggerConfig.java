package mx.com.prosa.nabhi.dash.config;

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

    //Cambio release/monitoreoatm
    private final SwaggerBase base;
    public static final String TAG_1 = "Reportes de Cajeros Automáticos";
    public static final String TAG_2 = "Monitoreo de Cajeros Automáticos";
    //Cambio release/monitoreoatm
    //Cambio release/grupos
    public static final String TAG_3 = "Grupos de Cajeros Automáticos";
    //Cambio release/grupos
    //Cambio release/redcat
    public static final String TAG_4 = "Reporte REDCAT";
    //Cambio release/redcat
    //Cambio release/algoritmos
    public static final String TAG_5 = "Algoritmos de Dispensado";
    //Cambio release/algoritmos
    public static final String TAG_6 = "Control de Accesos";
    public static final String TAG_7 = "Sockets TCP ISO-JKE";
    public static final String TAG_8 = "Control de Perfiles Adquirientes";
    public static final String TAG_9 = "Cajeros Automáticos";
    public static final String TAG_10 = "Prefijos";
    public static final String TAG_11 = "Instituciones";
    public static final String TAG_12 = "Imágenes";
    public static final String TAG_13 = "Nodos";
    public static final String TAG_14 = "Journals";
    public static final String TAG_15 = "Recibos";
    public static final String TAG_16 = "Grupos de Pantalla";
    public static final String TAG_17 = "Comisiones";
    public static final String TAG_18 = "Carga Masiva";
    public static final String TAG_80 = "Varios - Solo Lectura (Country, County, State, Paquetes)";

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
                                .basePackage( "mx.com.prosa.nabhi.dash" ),
                        RequestHandlerSelectors
                                .basePackage( "mx.com.prosa.nabhi.dash" ) ) )
                .build()
                //Cambio release/monitoreoatm
                .tags( new Tag( TAG_1, "Permite generar reportes de disponibilidad de cajeros automáticos" ) )
                .tags( new Tag( TAG_2, "Permite monitorear cajeros automáticos" ) )
                //Cambio release/monitoreoatm
                //Cambio release/grupos
                .tags( new Tag( TAG_3, "Permite realizar CRUD de grupos de cajeros" ) )
                //Cambio release/grupos
                //Cambio release/grupos
                .tags( new Tag( TAG_4, "Permite generar reportes de corte de cajeros automáticos" ) )
                //Cambio release/grupos
                //Cambio release/algoritmos
                .tags( new Tag( TAG_5, "Permite realizar CRUD de Algoritmos de dispensado" ) )
                //Cambio release/algoritmos
                .tags( new Tag( TAG_6, "Permite las gestión de control de accesos" ) )
                .tags( new Tag( TAG_7, "Permite operar los Sockets TCP de los servicios ISO y JKE" ) )
                .tags( new Tag( TAG_8, "Permite realizar CRUD de Control de Perfiles de Adquirientes" ) )
                .tags( new Tag( TAG_9, "Permite realizar CRUD de Cajeros Automáticos" ) )
                .tags( new Tag( TAG_10, "Permite realizar CRUD de Prefijos de Tarjetas" ) )
                .tags( new Tag( TAG_11, "Permite realizar CRUD de Instituciones" ) )
                .tags( new Tag( TAG_12, "Permite realizar CRUD de Imágenes" ) )
                .tags( new Tag( TAG_13, "Permite realizar CRUD de Nodos TCP" ) )
                .tags( new Tag( TAG_14, "Permite realizar Búsquedas de Journals" ) )
                .tags( new Tag( TAG_15, "Permite realizar CRUD de Recibos transaccionales" ) )
                .tags( new Tag( TAG_16, "Permite realizar CRUD de Grupos de Pantallas" ) )
                .tags( new Tag( TAG_17, "Permite realizar CRUD de Comisiones" ) )
                .tags( new Tag( TAG_18, "Permite realizar la carga masiva" ) )
                .tags( new Tag( TAG_80, "Permite realizar Búsquedas de varias Tablas de Control" ) )
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
        return new ApiInfoBuilder().title( "Dash Board Back End Services" )
                .description( "Back End for Dashboard clients" )
                .termsOfServiceUrl( "" )
                .contact( new Contact( "Glints S.A. de C.V.", "www.glints.com.mx", "gustavo.mancilla@gonet.us" ) )
                .license( "Apache License Version 2.0" )
                .licenseUrl( "" ).version( "1.0" )
                .build();
    }

}
