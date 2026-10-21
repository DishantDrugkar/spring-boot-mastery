package com.example.springboot_series.config;

import com.example.springboot_series.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SpringSecurity {

    @Autowired
    @Lazy
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session -> session
                        .sessionCreationPolicy(
                                org.springframework.security.config.http.SessionCreationPolicy.STATELESS
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers(
                                "/api/employee/get",
                                          "/api/employee/get/**"
                        ).permitAll()
                        .requestMatchers("/api/employee/create").hasRole("ADMIN")
                        .requestMatchers("/api/employee/update/**").hasRole("ADMIN")
                        .requestMatchers("/api/employee/delete/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(

                        jwtAuthenticationFilter,

                        UsernamePasswordAuthenticationFilter.class

                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner createInitialUsers(UserService userService) {
        return args -> {

            if (!userService.userServiceExists("admin")) {
                userService.createUser(
                        "admin", "admin123", "ROLE_ADMIN"
                );
            } else {
                userService.resetPassword("admin", "admin123");
            }

            if (!userService.userServiceExists("user")) {
                userService.createUser(
                        "user", "user123", "ROLE_USER"
                );
            }
            System.out.println(
                    "Admin password valid: " +
                            userService.verifyPassword("admin", "admin123")
            );
        };
    }
}