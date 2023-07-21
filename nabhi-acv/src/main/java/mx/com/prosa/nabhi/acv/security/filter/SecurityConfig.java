package mx.com.prosa.nabhi.acv.security.filter;

import com.google.common.collect.ImmutableList;
import mx.com.prosa.nabhi.acv.security.filter.jwt.JwtFilter;
import mx.com.prosa.nabhi.acv.security.filter.jwt.LoginFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Component
public class SecurityConfig {

    private CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins( ImmutableList.of( "*" ) );
        configuration.setAllowedMethods( ImmutableList.of( "HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "ACV" ) );
        configuration.addExposedHeader( HttpHeaders.AUTHORIZATION );
        configuration.setAllowCredentials( true );
        configuration.setAllowedHeaders( ImmutableList.of( "*" ) );
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration( "/**", configuration );
        return source;
    }

    public void configureWebSecurity( WebSecurity web ) {
        web.ignoring().antMatchers( "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**" );
    }

    public void configureHttp( final HttpSecurity http, String login, LoginFilter loginFilter, JwtFilter jwtFilter ) throws Exception {
        http.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ).and()
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers( HttpMethod.POST, login ).permitAll()
                .antMatchers( HttpMethod.GET, "/file/download/**/**" ).permitAll()
                //Cambio release/monitoreo
                .antMatchers( "/actuator/**" ).permitAll()
                //Cambio release/monitoreo
                .anyRequest().authenticated()
                .and()
                .addFilterBefore( loginFilter, UsernamePasswordAuthenticationFilter.class )
                .addFilterBefore( jwtFilter, UsernamePasswordAuthenticationFilter.class )
                .cors().configurationSource( corsConfigurationSource() );

    }
}
