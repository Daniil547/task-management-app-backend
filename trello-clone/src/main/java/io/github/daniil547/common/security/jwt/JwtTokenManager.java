package io.github.daniil547.common.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
public class JwtTokenManager {
    private final byte[] secretKey;
    private final long tokenExpireMilliseconds;


    public JwtTokenManager(@Value("${security.jwt.token.secret-key}") String secretKey,
                           @Value("${security.jwt.token.expire-milliseconds}") long tokenExpireMilliseconds) {
        this.secretKey = Base64.getEncoder()
                               .encodeToString(secretKey.getBytes())
                               .getBytes(StandardCharsets.UTF_8);
        this.tokenExpireMilliseconds = tokenExpireMilliseconds;
    }

    String generateToken(UUID userId) {
        Date expirationDate = new Date(System.currentTimeMillis() + tokenExpireMilliseconds);

        return Jwts.builder()
                   .setSubject(userId.toString())
                   .setExpiration(expirationDate)
                   .signWith(Keys.hmacShaKeyFor(secretKey))
                   .compact();
    }

    Optional<UUID> validateAndExtractPrincipal(String token) {
        UUID userId = null;
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                                        .setSigningKey(Keys.hmacShaKeyFor(secretKey))
                                        .build()
                                        .parseClaimsJws(token);

            userId = UUID.fromString(claimsJws.getBody().getSubject());
        } catch (JwtException ignored) {
            // in case of exception userId stays null
        }

        return Optional.ofNullable(userId);
    }


}