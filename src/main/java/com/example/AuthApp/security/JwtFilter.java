package com.example.AuthApp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter { // one request one time, no repeat
    private final Jwtutil jwtutil;

    public JwtFilter(Jwtutil jwtutil) {
        this.jwtutil = jwtutil;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // get authorization header
        String authHeader = request.getHeader("Authorization");
        // if header exists and has bearer token
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            // extract token
            String token = authHeader.substring(7);
            // validate the token
            if(jwtutil.validateToken(token)) {
                // extracting the token
                String email = jwtutil.extractEmail(token);
                // tells the user is authenticated
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        email,  // who is the user
                        null,
                        new ArrayList<>()
                );
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response); // pass request to next controller
    }
}
