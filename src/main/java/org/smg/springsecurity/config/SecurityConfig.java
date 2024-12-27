package org.smg.springsecurity.config;

import org.smg.springsecurity.filter.JwtRequestFilter;
import org.smg.springsecurity.provider.CustomAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.smg.springsecurity.constants.RoleConstants.ROLE_ADMIN;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity//PreAuthorize enabled!
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    //sets up the security filter chain to handle authentication, authorization, CSRF protection, login, and logout.
    //@Bean: This annotation indicates that the method will return an object that should be registered as a bean in the Spring application context
    //HttpSecurity http: This parameter is used to configure web-based security for specific HTTP requests.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)//disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/api/users/authenticate")//endpoints that can be accessed by anyone
                        .permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole(ROLE_ADMIN) // User role required
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole(ROLE_ADMIN) // Admin role required
                        .anyRequest().authenticated())//any other request (not explicitly matched above) must be authenticated.
                // If a user is not authenticated (i.e., not logged in or doesn't have a valid token), they will be denied access.
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);// JWT filter is used to intercept incoming HTTP requests and check the JWT token in the request
//The UsernamePasswordAuthenticationFilter is the default filter in Spring Security that handles form-based login (using username and password).
// By adding the jwtRequestFilter before this filter, you're ensuring that your JWT-based authentication happens before any form login logic is applied.
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // register the CustomAuthenticationProvider!
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, CustomAuthenticationProvider customAuthenticationProvider) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthenticationProvider)
                .build();
    }
}