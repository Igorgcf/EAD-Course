package com.ead.course.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Log4j2
@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwtStr = getTokenFromRequest(request);

            if (jwtStr != null && jwtProvider.validateJwt(jwtStr)) {

                String userId = jwtProvider.getSubjectJwt(jwtStr);
                String rolesStr = jwtProvider.getClaimNameJwt(jwtStr, "roles");
                UserDetails userDetails = UserDetailsImpl.buildFromEntity(UUID.fromString(userId), rolesStr);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                log.info("JWT AUTHENTICATED USER -> {}", userDetails.getUsername());
                userDetails.getAuthorities()
                        .forEach(a -> log.info("JWT AUTHORITY -> {}", a.getAuthority()));

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        }catch (Exception e){
            log.error("Cannot set User Authentication: {} ", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
    }
