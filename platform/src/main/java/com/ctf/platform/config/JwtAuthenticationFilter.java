package com.ctf.platform.config;

import com.ctf.platform.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if(header == null || ! header.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = header.substring(7);
        String userEmail = jwtService.extractEmail(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
             UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
             boolean validationToken = jwtService.isTokenValid(jwt, userDetails);

            if (validationToken){
                UsernamePasswordAuthenticationToken usrAuthToken = UsernamePasswordAuthenticationToken.
                        authenticated(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usrAuthToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
