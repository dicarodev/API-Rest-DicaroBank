package com.dicaro.dicarobank.security;

import com.dicaro.dicarobank.model.AppUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Generate JWT token when a user logs in successfully. And validate the token when the user requests a resource.
 */
@Component
public class JwtTokeProvider {
    Logger log = LoggerFactory.getLogger(this.getClass());

    //Key used to sign the JWT
    @Value("${app.security.jwt.secret}")
    private String jwtSecret;

    //Duration of the JWT
    @Value("${app.security.jwt.expiration}")
    private Long jwtDurationSeconds;

    /**
     * Generate JWT token when a user logs in.
     * @param authentication the user's authentication object.
     * @return the JWT token
     */
    public String generateToken(Authentication authentication) {
        AppUser user = (AppUser) authentication.getPrincipal(); //Get the user from the authentication object.

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS512)
                .header().add("typ", "JWT").and()
                .subject(Long.toString(user.getId()))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + (jwtDurationSeconds * 1000)))
                .claim("dni", user.getDni())
                .claim("email", user.getEmail())
                .compact();

    }

    /**
     * Validate the token when the user requests a resource.
     * @param token the JWT token.
     * @return true if the token is valid, false otherwise.
     */
    public boolean isValidToken(String token) {
        if (!StringUtils.hasLength(token))
            return false;

        try {
            //Try to parse the token with the secret key and validate it. If no exception is thrown, the token is valid.
            JwtParser validator = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build();

            //If the token is valid, return true.
            validator.parseSignedClaims(token);
            return true;
        } catch (SignatureException e) {
            log.info("Error en la firma del token", e);
        } catch (MalformedJwtException | UnsupportedJwtException e) {
            log.info("Token incorrecto", e);
        } catch (ExpiredJwtException e) {
            log.info("Token expirado", e);
        }
        return false;

    }

    /**
     * Extracts the DNI from the token payload.
     * @param token the JWT token.
     * @return the DNI associated with the token.
     */
    public String getDniFromToken(String token) {
        //Try to parse the token with the secret key and validate it. If no exception is thrown, the token is valid.
        JwtParser parser = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build();

        //Extract the DNI from the token payload and return it.
        Claims claims = parser.parseSignedClaims(token).getPayload();
        return claims.get("dni").toString();
    }
}
