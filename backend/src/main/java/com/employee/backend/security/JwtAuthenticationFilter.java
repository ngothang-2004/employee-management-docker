package com.employee.backend.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.employee.backend.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(
            JwtService jwtService
    ) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Lấy Authorization Header
        String authHeader =
                request.getHeader("Authorization");

        // Nếu không có Authorization
        // thì cho request đi tiếp.
        // SecurityConfig sẽ quyết định
        // request đó có được phép hay không.
        if (
                authHeader == null ||
                !authHeader.startsWith("Bearer ")
        ) {

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }

        // Lấy JWT sau chữ "Bearer "
        String token =
                authHeader.substring(7);

        try {

            // Đọc thông tin bên trong JWT
            Claims claims =
                    jwtService.extractAllClaims(token);

            String username =
                    claims.getSubject();

            String role =
                    claims.get(
                            "role",
                            String.class
                    );

            // Nếu JWT có username
            // và Spring Security chưa xác thực
            if (
                    username != null &&
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication() == null
            ) {

                String authority =
                        "ROLE_" +
                        role.toUpperCase();

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                List.of(
                                        new SimpleGrantedAuthority(
                                                authority
                                        )
                                )
                        );

                // Gắn thông tin đăng nhập
                // vào Security Context
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(
                                authentication
                        );
            }

        } catch (JwtException |
                 IllegalArgumentException e) {

            // JWT không hợp lệ hoặc đã hết hạn
            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );

            response.setContentType(
                    "application/json;charset=UTF-8"
            );

            response.getWriter().write(
                    """
                    {
                        "message": "Token không hợp lệ hoặc đã hết hạn"
                    }
                    """
            );

            return;
        }

        // Cho request đi tiếp
        filterChain.doFilter(
                request,
                response
        );
    }
}