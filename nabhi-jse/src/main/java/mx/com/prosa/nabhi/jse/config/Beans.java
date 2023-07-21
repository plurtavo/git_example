package mx.com.prosa.nabhi.jse.config;

import mx.com.prosa.nabhi.misc.config.annotation.*;
import mx.com.prosa.nabhi.misc.domain.config.annotation.EnableAlgorithmDomain;
import mx.com.prosa.nabhi.misc.domain.config.annotation.EnablePersonalizedDomain;
import mx.com.prosa.nabhi.misc.rest.esq.ESQRequester;
import mx.com.prosa.nabhi.misc.rest.iso.ISORequester;
import org.codehaus.jackson.map.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableAsync
@Import( {
        ISORequester.class,
        ESQRequester.class } )
//Cambio release/algoritmos
@EnableAlgorithmDomain
//Cambio release/algoritmos
@EnablePersonalizedDomain
@EnableSpringContext
@EnableJKERequest
@EnableISORequest
@EnableUtils
@EnableLog
@EnableAlert
@EnableReceipt
public class Beans {

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
