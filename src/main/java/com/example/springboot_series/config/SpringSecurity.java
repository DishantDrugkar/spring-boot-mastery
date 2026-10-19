package com.example.springboot_series.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurity {

    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder passwordEncoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // Admin user
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        // Normal user
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // Anyone can access these GET endpoints
                        .requestMatchers(
                                "/api/employee/get",
                                "/api/employee/get/**"
                        ).permitAll()

                        // Only ADMIN can create employees
                        .requestMatchers(
                                "/api/employee/create"
                        ).hasRole("ADMIN")

                        // Only ADMIN can update employees
                        .requestMatchers(
                                "/api/employee/update/**"
                        ).hasRole("ADMIN")

                        // Only ADMIN can delete employees
                        .requestMatchers(
                                "/api/employee/delete/**"
                        ).hasRole("ADMIN")

                        // All other requests require authentication
                        .anyRequest().authenticated()
                )

                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}