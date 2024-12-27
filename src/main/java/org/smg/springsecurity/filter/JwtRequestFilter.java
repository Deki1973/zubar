package org.smg.springsecurity.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.smg.springsecurity.service.CustomUserDetailsService;
import org.smg.springsecurity.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Handles JWT authentication, validating the token and extracting the user information from it.
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (isAuthorizationHeaderValid(authorizationHeader)) {
            String jwt = extractJwtFromHeader(authorizationHeader);
            String username = jwtUtil.extractUsername(jwt);

            if (username != null && isAuthenticationRequired()) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);//CustomUserDetailsService

                if (isTokenValid(jwt, userDetails)) {
                    setAuthenticationContext(request, userDetails);
                }
            }
        }

        chain.doFilter(request, response); // Continue the filter chain
    }

    private boolean isAuthorizationHeaderValid(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith("Bearer ");
    }

    private String extractJwtFromHeader(String authorizationHeader) {
        return authorizationHeader.substring(7); // Remove "Bearer " prefix
    }

    private boolean isAuthenticationRequired() {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private boolean isTokenValid(String jwt, UserDetails userDetails) {
        return jwtUtil.validateToken(jwt, userDetails.getUsername());
    }

    private void setAuthenticationContext(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,//principal (the user details)
                null,//credentials (such as password). This is where the password or any secret credentials would go.
                // Since you’re already authenticating with a JWT token and not needing the user's password again, you pass null here.
                // In a typical form-based authentication flow, you would pass the user’s password in this field.
                userDetails.getAuthorities());//authorities (permissions or roles)

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

}
