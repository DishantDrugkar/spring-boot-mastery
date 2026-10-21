package com.example.springboot_series.config;

import com.example.springboot_series.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Step 1: Check Authorization header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            // Step 2: Extract username from JWT
            String username = jwtService.extractUsername(token);

            // Step 3: Check whether authentication already exists
            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                // Step 4: Load user details
                UserDetails user =
                        userDetailsService.loadUserByUsername(username);

                // Step 5: Validate token
                if (jwtService.isTokenValid(token, user.getUsername())) {

                    // Step 6: Create authentication object
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    user.getAuthorities()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    // Step 7: Store authentication in SecurityContext
                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }
            }

        } catch (io.jsonwebtoken.JwtException |
                 IllegalArgumentException e) {

            // Invalid or expired token
            SecurityContextHolder.clearContext();

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"error\":\"Invalid or expired JWT token\"}"
            );

            return;
        }

        // Step 8: Continue filter chain
        filterChain.doFilter(request, response);
    }
}