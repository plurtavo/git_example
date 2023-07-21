package mx.com.prosa.nabhi.jse.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.com.prosa.nabhi.jse.security.filter.SecurityConfig;
import mx.com.prosa.nabhi.jse.security.filter.auth.UserAuth;
import mx.com.prosa.nabhi.jse.security.filter.jwt.JwtAuthenticationFailureHandler;
import mx.com.prosa.nabhi.jse.security.filter.jwt.JwtFilter;
import mx.com.prosa.nabhi.jse.security.filter.jwt.JwtUtil;
import mx.com.prosa.nabhi.jse.security.filter.jwt.LoginFilter;
import mx.com.prosa.nabhi.misc.util.Sanitizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true )
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserAuth userAuth;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtFilter jwtFilter;
    private final SecurityConfig securityConfig;
    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private Sanitizer sanitizer;
    private static final String URL = "/auth";

    @Autowired
    public WebSecurityConfig( UserAuth userAuth, BCryptPasswordEncoder bCryptPasswordEncoder, JwtFilter jwtFilter, SecurityConfig securityConfig, JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler, JwtUtil jwtUtil, ObjectMapper objectMapper ) {
        this.userAuth = userAuth;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtFilter = jwtFilter;
        this.securityConfig = securityConfig;
        this.jwtAuthenticationFailureHandler = jwtAuthenticationFailureHandler;
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setSanitizer( Sanitizer sanitizer ) {
        this.sanitizer = sanitizer;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService( userAuth ).passwordEncoder( bCryptPasswordEncoder );
    }

    @Override
    public void configure( WebSecurity web ) {
        securityConfig.configureWebSecurity( web );
    }

    @Override
    protected void configure( final HttpSecurity http ) throws Exception {
        LoginFilter loginFilter = new LoginFilter( authenticationManager() );
        loginFilter.init( jwtAuthenticationFailureHandler, jwtUtil, userAuth, objectMapper, sanitizer );
        securityConfig.configureHttp( http, URL, loginFilter, jwtFilter );
    }
}
