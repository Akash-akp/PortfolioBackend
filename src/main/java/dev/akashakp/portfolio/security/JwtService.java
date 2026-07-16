package dev.akashakp.portfolio.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
            private String secret;

            @Value("${app.jwt.expiration-ms}")
                    private long expirationMs;

                    private SecretKey key() {
                    byte[] bytes;
                    try {
                    bytes = Decoders.BASE64.decode(secret);
                    } catch (Exception e) {
            bytes = secret.getBytes();
            }
            if (bytes.length < 32) {
            byte[] padded = new byte[32];
            System.arraycopy(bytes, 0, padded, 0, Math.min(bytes.length, 32));
            for (int i = bytes.length; i < 32; i++) padded[i] = (byte) i;
    bytes = padded;
}
        return Keys.hmacShaKeyFor(bytes);
    }

public String generate(UserDetails user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("roles", user.getAuthorities().stream().map(Object::toString).toList());
    return Jwts.builder()
            .claims(claims)
            .subject(user.getUsername())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expirationMs))
            .signWith(key())
            .compact();
}

public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
}

public <T> T extractClaim(String token, Function<Claims, T> resolver) {
    Claims claims = Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload();
    return resolver.apply(claims);
}

public boolean isValid(String token, UserDetails user) {
    try {
        String username = extractUsername(token);
        Date exp = extractClaim(token, Claims::getExpiration);
        return username.equals(user.getUsername()) && exp.after(new Date());
    } catch (Exception e) {
        return false;
    }
}
}
