package com.ead.course.config.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Log4j2
@Component
public class JwtProvider {

    private Key key;

    @Value("${ead.auth.jwtSecret}")
    private String jwtSecret;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

 public String getClaimNameJwt(String token, String claimName) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(claimName)
                .toString();
    }

    public String getSubjectJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwt(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        return true;
        }catch(SignatureException e){
            log.error("Invalid JWT signature: {} ", e.getMessage());
        }catch(MalformedJwtException e){
            log.error("Invalid JWT token: {} ", e.getMessage());
        }catch(ExpiredJwtException e){
            log.error("JWT token is expired: {} ", e.getMessage());
        }catch(UnsupportedJwtException e){
            log.error("JWT token is unsupported: {} ", e.getMessage());
        }catch(IllegalArgumentException e){
            log.error("JWT claims string is empty: {} ", e.getMessage());
        }
        return false;
    }
}

