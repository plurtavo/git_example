package mx.com.prosa.nabhi.iso.config;

import mx.com.prosa.nabhi.misc.config.annotation.*;
import mx.com.prosa.nabhi.misc.domain.config.annotation.EnableLogDomain;
import mx.com.prosa.nabhi.misc.domain.config.annotation.EnableNodeDomain;
import org.codehaus.jackson.map.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
@EnableSocketDomain
@EnableNodeDomain
@EnableSecurity
@EnableUtils
@EnableLogDomain
@EnableJKERequest
@EnableSpringContext
@EnableLog
@EnableAlert
public class Beans {

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
