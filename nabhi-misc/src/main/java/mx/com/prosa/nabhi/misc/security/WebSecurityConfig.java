package mx.com.prosa.nabhi.misc.security;

import com.google.common.collect.ImmutableList;
import mx.com.prosa.nabhi.misc.security.jwt.JwtFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@ComponentScan( "mx.com.prosa.nabhi.misc.security" )
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;
    private static final String URL = "/auth";

    public WebSecurityConfig( JwtFilter jwtFilter ) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    public void configure( WebSecurity web ) {
        configureWebSecurity( web );
    }

    @Override
    protected void configure( final HttpSecurity http ) throws Exception {
        configureHttp( http, URL, jwtFilter );
    }

    public void configureHttp( final HttpSecurity http, String login, JwtFilter jwtFilter ) throws Exception {
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
                .addFilterBefore( jwtFilter, UsernamePasswordAuthenticationFilter.class )
                .cors().configurationSource( corsConfigurationSource() );

    }

    private CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins( ImmutableList.of( "*" ) );
        configuration.setAllowedMethods( ImmutableList.of( "HEAD", "GET", "POST", "PUT", "DELETE", "PATCH" ) );
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
}
