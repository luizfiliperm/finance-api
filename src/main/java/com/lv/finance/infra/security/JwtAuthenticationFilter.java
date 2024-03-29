package com.lv.finance.infra.security;

import com.lv.finance.infra.security.services.JwtService;
import com.lv.finance.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserService userService;

    public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String jwtToken = recoverToken(request);

        if (jwtToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        authenticateUser(jwtToken, request);
        filterChain.doFilter(request, response);
    }

    private void authenticateUser(String jwtToken, HttpServletRequest request) {
        String userEmail = jwtService.extractUsername(jwtToken);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails user = userService.loadUserByEmail(userEmail);
            if (jwtService.isTokenValid(jwtToken, user)) {
                setAuthentication(user, request);
            }
        }
    }

    private void setAuthentication(UserDetails user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );
        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private String recoverToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer "))
            return null;
        return authHeader.replace("Bearer ", "");
    }
}
