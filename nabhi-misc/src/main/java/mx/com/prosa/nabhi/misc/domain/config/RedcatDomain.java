package mx.com.prosa.nabhi.misc.domain.config;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories( { "mx.com.prosa.nabhi.misc.domain.redcat.repository" } )
@EntityScan( { "mx.com.prosa.nabhi.misc.domain.redcat.entity" } )
@ComponentScan( "mx.com.prosa.nabhi.misc.domain.redcat" )
public class RedcatDomain {
}
