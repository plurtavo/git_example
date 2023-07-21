package mx.com.prosa.nabhi.jse.security.filter;

import com.google.common.collect.ImmutableList;
import mx.com.prosa.nabhi.jse.security.AllowedUrl;
import mx.com.prosa.nabhi.jse.security.filter.jwt.JwtFilter;
import mx.com.prosa.nabhi.jse.security.filter.jwt.LoginFilter;
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
        configuration.setAllowedMethods( ImmutableList.of( "HEAD", "GET", "POST", "PUT", "DELETE", "PATCH" ) );
        configuration.addExposedHeader( HttpHeaders.AUTHORIZATION );
        configuration.setAllowCredentials( true );
        configuration.setAllowedHeaders( ImmutableList.of( "*" ) );
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

    /*********
     *
     * @param http Http Context
     * @param login Url for Login
     * @param loginFilter Filter
     * @param jwtFilter Filter
     * @param allowedUrl Only two index
     */
    public void configureHttp( final HttpSecurity http, String login, LoginFilter loginFilter, JwtFilter jwtFilter, AllowedUrl... allowedUrl ) throws Exception {
        if ( allowedUrl.length == 0 ) {
            allowedUrl = new AllowedUrl[ 1 ];
            allowedUrl[ 0 ] = new AllowedUrl( HttpMethod.POST, login );
        }
        http.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ).and()
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers( HttpMethod.POST, login ).permitAll()
                //Cambio release/monitoreo
                .antMatchers( "/actuator/**" ).permitAll()
                //Cambio release/monitoreo
                .antMatchers( allowedUrl[ 0 ].getMethod(), allowedUrl[ 0 ].getUrl() ).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore( loginFilter, UsernamePasswordAuthenticationFilter.class )
                .addFilterBefore( jwtFilter, UsernamePasswordAuthenticationFilter.class )
                .cors().configurationSource( corsConfigurationSource() );

    }
}
