package mx.com.prosa.nabhi.dash.config;

import mx.com.prosa.nabhi.dash.redcat.ftpconnection.FTPClientTemplate;
import mx.com.prosa.nabhi.dash.temp.EnableTempDomain;
import mx.com.prosa.nabhi.misc.config.annotation.*;
import mx.com.prosa.nabhi.misc.domain.config.annotation.EnableAlgorithmDomain;
import mx.com.prosa.nabhi.misc.domain.config.annotation.EnableGroupDomain;
import mx.com.prosa.nabhi.misc.domain.config.annotation.EnablePersonalizedDomain;
import mx.com.prosa.nabhi.misc.domain.config.annotation.EnableRedcatDomain;
import mx.com.prosa.nabhi.misc.domain.personalized.IDFSRepository;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
//Cambio release/monitoreoatm
//Cambio release/monitoreoatm
@Import( { IDFSRepository.class, } )
//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
//P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End
//Cambio release/grupos
@EnableGroupDomain
//Cambio release/grupos
@EnableRedcatDomain
//Cambio release/algoritmos
@EnableAlgorithmDomain
//Cambio release/algoritmos
@EnableUtils
@EnableSpringContext
@EnableJKERequest
@EnableISORequest
@EnablePersonalizedDomain
@EnableTempDomain
@EnableAlert
@EnableLog
@EnableReceipt
public class Beans {

    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
    @Value( "${ldap.url}" )
    private String ldapUrl;
    @Value( "${ldap.partitionSuffix}" )
    private String ldapSuffix;
    @Value( "${ldap.principal}" )
    private String ldapPrincipal;
    @Value( "${ldap.p}" )
    private String p;
    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End

    //Cambio release/redcat
    //Angel Serralde López 26/06/2021 - REDCAT
    @Value( "${redcat.user}" )
    private String userRedcat;
    @Value( "${redcat.p}" )
    private String pRedcat;
    @Value( "${redcat.hostname}" )
    private String hostRedcat;
    @Value( "${redcat.port}" )
    private int portRedcat;
    //Angel Serralde López 26/06/2021 - REDCAT


    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
    @Bean
    public BaseLdapPathContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl( ldapUrl );
        contextSource.setBase( ldapSuffix );
        contextSource.setUserDn( ldapPrincipal );
        contextSource.setPassword( p );
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate( contextSource() );
    }
    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End

    @Bean
    public FTPClient ftpClient() {
        return new FTPClient();
    }

    //Cambio release/redcat
    //Angel Serralde López 26/06/2021 - REDCAT
    @Bean
    public FTPSClient ftpsClient() {
        return new FTPSClient();
    }

    @Bean
    public FTPClientTemplate ftpClientTemplate() {
        return new FTPClientTemplate( userRedcat, pRedcat, hostRedcat, portRedcat );
    }
    //Angel Serralde López 26/06/2021 - REDCAT

}
