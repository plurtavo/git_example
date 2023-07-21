package mx.com.prosa.nabhi.misc.domain.config;

import mx.com.prosa.nabhi.misc.domain.config.annotation.EnableLogDomain;
import mx.com.prosa.nabhi.misc.domain.config.annotation.EnableNodeDomain;
import mx.com.prosa.nabhi.misc.domain.config.annotation.EnableSecurityDomain;
import mx.com.prosa.nabhi.misc.domain.config.annotation.EnableSingleDomain;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories( { "mx.com.prosa.nabhi.misc.domain.complete.repository" } )
@EntityScan( { "mx.com.prosa.nabhi.misc.domain.complete.entity" } )
@ComponentScan( "mx.com.prosa.nabhi.misc.domain.complete" )
@EnableSingleDomain //For Single Search
@EnableNodeDomain //For Node TCP
@EnableSecurityDomain//For Access Control
@EnableLogDomain //For Audit
public class CompleteDomain {
}
