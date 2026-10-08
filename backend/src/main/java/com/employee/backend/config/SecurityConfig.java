package com.employee.backend.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.employee.backend.security.JwtAuthenticationFilter;
import com.employee.backend.service.JwtService;

@Configuration
public class SecurityConfig {

        private final JwtService jwtService;

        public SecurityConfig(
                        JwtService jwtService) {
                this.jwtService = jwtService;
        }

        @Bean
        public JwtAuthenticationFilter jwtAuthenticationFilter() {

                return new JwtAuthenticationFilter(
                                jwtService);
        }

        @Bean
        public SecurityFilterChain securityFilterChain(
                        HttpSecurity http) throws Exception {

                http

                                // Tắt CSRF vì frontend Vue gọi REST API
                                .csrf(csrf -> csrf.disable())

                                // Cho phép Vue gọi Spring Boot
                                .cors(cors -> cors.configurationSource(
                                                corsConfigurationSource()))

                                // Không sử dụng session
                                .sessionManagement(session -> session.sessionCreationPolicy(
                                                SessionCreationPolicy.STATELESS))

                                // Phân quyền API
                                .authorizeHttpRequests(auth -> auth

                                                // =====================================
                                                // LOGIN
                                                // =====================================

                                                // Login không cần JWT
                                                .requestMatchers(
                                                                "/api/login")
                                                .permitAll()

                                                // OPTIONS dùng cho CORS
                                                .requestMatchers(
                                                                HttpMethod.OPTIONS,
                                                                "/**")
                                                .permitAll()

                                                // =====================================
                                                // EMPLOYEE
                                                // =====================================

                                                // User + Admin đều được xem nhân viên
                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/employees/**")
                                                .authenticated()

                                                // Chỉ Admin được thêm nhân viên
                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/employees/**")
                                                .hasRole("ADMIN")

                                                // Chỉ Admin được sửa nhân viên
                                                .requestMatchers(
                                                                HttpMethod.PUT,
                                                                "/api/employees/**")
                                                .hasRole("ADMIN")

                                                // Chỉ Admin được xóa nhân viên
                                                .requestMatchers(
                                                                HttpMethod.DELETE,
                                                                "/api/employees/**")
                                                .hasRole("ADMIN")

                                                // =====================================
                                                // UNIT
                                                // =====================================

                                                // User + Admin đều được xem đơn vị
                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/units/**")
                                                .authenticated()

                                                // Chỉ Admin được thêm đơn vị
                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/units/**")
                                                .hasRole("ADMIN")

                                                // Chỉ Admin được sửa đơn vị
                                                .requestMatchers(
                                                                HttpMethod.PUT,
                                                                "/api/units/**")
                                                .hasRole("ADMIN")

                                                // Chỉ Admin được xóa đơn vị
                                                .requestMatchers(
                                                                HttpMethod.DELETE,
                                                                "/api/units/**")
                                                .hasRole("ADMIN")

                                                // =====================================
                                                // CÁC API KHÁC
                                                // =====================================

                                                // Các API khác phải đăng nhập
                                                .anyRequest().authenticated())

                                // Gắn JWT Filter vào Spring Security
                                .addFilterBefore(
                                                jwtAuthenticationFilter(),
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {

                CorsConfiguration configuration = new CorsConfiguration();

                configuration.setAllowedOrigins(
                                List.of(
                                                "http://localhost:5173"));

                configuration.setAllowedMethods(
                                List.of(
                                                "GET",
                                                "POST",
                                                "PUT",
                                                "DELETE",
                                                "OPTIONS"));

                configuration.setAllowedHeaders(
                                List.of(
                                                "Authorization",
                                                "Content-Type"));

                configuration.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

                source.registerCorsConfiguration(
                                "/**",
                                configuration);

                return source;
        }
}