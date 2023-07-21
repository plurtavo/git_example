package mx.com.prosa.nabhi.acv.security.config;

import mx.com.prosa.nabhi.acv.security.filter.SecurityConfig;
import mx.com.prosa.nabhi.acv.security.filter.jwt.JwtAuthenticationFailureHandler;
import mx.com.prosa.nabhi.acv.security.filter.jwt.JwtFilter;
import mx.com.prosa.nabhi.acv.security.filter.jwt.JwtUtil;
import mx.com.prosa.nabhi.acv.security.filter.jwt.LoginFilter;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true )
@Primary
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String URL = "/auth";
    private final JwtFilter jwtFilter;
    private final SecurityConfig securityConfig;
    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Autowired
    public WebSecurityConfig( JwtFilter jwtFilter, SecurityConfig securityConfig, JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler, JwtUtil jwtUtil, ObjectMapper objectMapper ) {
        this.jwtFilter = jwtFilter;
        this.securityConfig = securityConfig;
        this.jwtAuthenticationFailureHandler = jwtAuthenticationFailureHandler;
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure( WebSecurity web ) {
        securityConfig.configureWebSecurity( web );
    }

    @Override
    protected void configure( final HttpSecurity http ) throws Exception {
        LoginFilter loginFilter = new LoginFilter( authenticationManager() );
        loginFilter.init( jwtAuthenticationFailureHandler, jwtUtil, objectMapper );
        securityConfig.configureHttp( http, URL, loginFilter, jwtFilter );
    }
}
