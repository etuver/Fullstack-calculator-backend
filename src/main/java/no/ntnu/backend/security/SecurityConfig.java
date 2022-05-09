package no.ntnu.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final JwtAuthFilter filter;

    @Autowired
    public SecurityConfig(JwtAuthFilter filter) {
        this.filter = filter;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // token endpoint is not protected
        http.headers().frameOptions().sameOrigin() // Needed for H2 console to work.
                .and().cors().configurationSource(request -> corsConfiguration()).and().csrf().disable()
                .authorizeRequests().antMatchers("/h2/**", // Allow access to H2 database admin panel.
                        "/auth/**", // Allow access to authentication endpoint.'
                        "/auth/**" // Allow access to authentication endpoint.
                ).permitAll().antMatchers(HttpMethod.POST, "/user/**").permitAll()
                .antMatchers(HttpMethod.GET, "/image/**", "/listing/**").permitAll().anyRequest().authenticated().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);
        List.of("GET", "POST", "PUT", "DELETE", "OPTIONS").forEach(config::addAllowedMethod);

        return config;
    }

}
