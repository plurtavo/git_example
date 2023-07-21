package mx.com.prosa.nabhi.dash.security;
//Cambio release/redcat
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import mx.com.prosa.nabhi.dash.security.jwt.JwtAuthenticationFailureHandler;
import mx.com.prosa.nabhi.dash.security.jwt.JwtFilter;
import mx.com.prosa.nabhi.dash.security.jwt.JwtUtil;
import mx.com.prosa.nabhi.dash.security.jwt.LoginFilter;
import mx.com.prosa.nabhi.dash.security.ldap.AuthenticationLdap;
import mx.com.prosa.nabhi.misc.util.Sanitizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true )
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
    private final UserAuthLdap userAuth;
    private final JwtFilter jwtFilter;
    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End
    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private Sanitizer sanitizer;
    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
    private final AuthenticationLdap authenticationLdap;
    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End
    private static final String URL = "/auth";

    @Autowired
    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
    public WebSecurityConfig( UserAuthLdap userAuth, AuthenticationLdap authenticationLdap, JwtFilter jwtFilter, JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler, JwtUtil jwtUtil, ObjectMapper objectMapper ) {
        this.userAuth = userAuth;
        this.jwtFilter = jwtFilter;
        //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet End
        this.jwtAuthenticationFailureHandler = jwtAuthenticationFailureHandler;
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        this.authenticationLdap = authenticationLdap;
    }

    @Autowired
    public void setSanitizer( Sanitizer sanitizer ) {
        this.sanitizer = sanitizer;
    }


    @Override
    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet Begin
    public void configure( WebSecurity web ) {
        configureWebSecurity( web );
    }

    @Override
    protected void configure( final HttpSecurity http ) throws Exception {
        LoginFilter loginFilter = new LoginFilter( authenticationLdap );
        loginFilter.init( jwtAuthenticationFailureHandler, jwtUtil, userAuth, objectMapper, sanitizer );
        configureHttp( http, URL, loginFilter, jwtFilter );
    }

    public void configureHttp( final HttpSecurity http, String login, LoginFilter loginFilter, JwtFilter jwtFilter ) throws Exception {
        http.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ).and()
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers( HttpMethod.POST, login ).permitAll()
                //Cambio release/monitoreo
                .antMatchers( "/actuator/**" ).permitAll()
                //Cambio release/monitoreo
                .anyRequest().authenticated()
                .and()
                .addFilterBefore( loginFilter, UsernamePasswordAuthenticationFilter.class )
                .addFilterBefore( jwtFilter, UsernamePasswordAuthenticationFilter.class )
                .cors().configurationSource( corsConfigurationSource() );

    }

    private CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins( ImmutableList.of( "*" ) );
        configuration.setAllowedMethods( ImmutableList.of( "HEAD", "GET", "POST", "PUT", "DELETE" ) );
        configuration.setAllowedHeaders( ImmutableList.of( "*" ) );
        configuration.addExposedHeader( HttpHeaders.AUTHORIZATION );
        configuration.setAllowCredentials( true );
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration( "/**", configuration );
        return source;
    }

    public void configureWebSecurity( WebSecurity web ) {
        web.ignoring().antMatchers( "/v2/api-docs",
                "/configuration/ide",
                "/swagger-ui.html",
                "/favicon.ico",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ide.html",
                "/swagger-ui.html",
                "/webjars/**" );
    }
    //P-81-7019-20 D2 Gustavo Mancilla Flores Gonet end
}
