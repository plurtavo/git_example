package mx.com.prosa.nabhi.acv.config;

import com.dsapi.core.sockets.crypto.Crypto;
import mx.com.prosa.nabhi.misc.config.annotation.EnableAlert;
import mx.com.prosa.nabhi.misc.config.annotation.EnableLog;
import mx.com.prosa.nabhi.misc.domain.config.annotation.EnableACVDomain;
import org.codehaus.jackson.map.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableScheduling
@EnableACVDomain
@EnableLog
@EnableAlert
@Import( Crypto.class )
public class ACVConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public PasswordEncoder sshaPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
