package com.example.api_gateway.Security;
import com.example.api_gateway.config.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    private final JwtConfig jwtConfig;
    private Key key;

    // 🔥 Constructor Injection (NO @Autowired)
    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }


    // 🔥 Initialize key AFTER config is loaded
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
        System.out.println("GATEWAY JWT SECRET LOADED: " + jwtConfig.getSecret());
    }
    public String getRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }
    public String getEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // 👈 email stored as subject
    }
    // 🔐 Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}