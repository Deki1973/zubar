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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

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
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/api/users/authenticate","api/users/test") // Endpoints that can be accessed by anyone
                        .permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole(ROLE_ADMIN) // Admin role required
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole(ROLE_ADMIN) // Admin role required
                        .anyRequest().authenticated())  // Any other request (not explicitly matched above) must be authenticated.
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);  // JWT filter for authentication

        // Apply CORS configuration
        http.addFilterBefore(corsFilter(), CorsFilter.class);

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:5500","http://localhost:5173"));  // Allow specific frontend, ne moze 127.0.0.1:<port> - bacice CORS gresku
        //config.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5173"));  // Allow specific frontend
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);  // Allow credentials if needed
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }




        @Bean
        public PasswordEncoder passwordEncoder () {
            return new BCryptPasswordEncoder();
        }

        // register the CustomAuthenticationProvider!
        @Bean
        public AuthenticationManager authenticationManager (HttpSecurity http, CustomAuthenticationProvider
        customAuthenticationProvider) throws Exception {
            return http.getSharedObject(AuthenticationManagerBuilder.class)
                    .authenticationProvider(customAuthenticationProvider)
                    .build();
        }
    }