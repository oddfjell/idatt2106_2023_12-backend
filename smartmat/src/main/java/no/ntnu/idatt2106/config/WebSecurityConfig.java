package no.ntnu.idatt2106.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * Instance of JWTRequestFilter.class
     */
    private JWTRequestFilter jwtRequestFilter;

    /**
     * Constructor
     * @param jwtRequestFilter JWTRequestFilter
     */
    public WebSecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Checks if the HttpRequest calls a permitted endpoint or an endpoint that requires a token
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.cors().and().csrf().disable();
        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests()
                .requestMatchers("/auth/account/registerAccount").permitAll()
                .requestMatchers("/auth/account/loginAccount").permitAll()
                .requestMatchers("/grocery/*").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }
}
