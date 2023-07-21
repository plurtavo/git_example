package mx.com.prosa.nabhi.jke.config;

//Cambio release/abc

import mx.com.prosa.nabhi.misc.config.annotation.*;
import mx.com.prosa.nabhi.misc.domain.config.annotation.EnableLogDomain;
import mx.com.prosa.nabhi.misc.domain.config.annotation.EnableNodeDomain;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableSocketDomain
@EnableSecurity
@EnableUtils
@EnableNodeDomain
@EnableLogDomain
@EnableSpringContext
@EnableLog
@EnableAlert
//Cambio release/abc
public class Beans {

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

}
