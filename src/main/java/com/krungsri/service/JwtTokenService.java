package com.krungsri.service;

import com.krungsri.configuration.ApplicationConfiguration;
import com.krungsri.model.AuthenticationRequest;
import com.krungsri.model.JwtTokenResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Log4j2
@Service
@AllArgsConstructor
public class JwtTokenService {

    private final ApplicationConfiguration configuration;

    public String createToken(final UserDetails userDetails) {
        final Instant signAt = Instant.now();
        var refreshTokenClaims = Map.ofEntries(
                Map.entry("username", userDetails.getUsername()),
                Map.entry("password", userDetails.getPassword())
        );

        var refreshToken = Jwts.builder()
                .setClaims(refreshTokenClaims)
                .setIssuer("krungsri-registration")
                .setIssuedAt(Date.from(signAt))
                .setExpiration(Date.from(signAt.plusSeconds(configuration.getJwt().getTokenExpirationMs())))
                .signWith(SignatureAlgorithm.HS256, configuration.getJwt().getTokenSecretKey())
                .compact();

        var claims = Map.ofEntries(
                Map.entry("username", userDetails.getUsername()),
                Map.entry("password", userDetails.getPassword()),
                Map.entry("refresh-token", refreshToken)
        );

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("krungsri-registration")
                .setIssuedAt(Date.from(signAt))
                .setExpiration(Date.from(signAt.plusSeconds(configuration.getJwt().getTokenExpirationMs())))
                .signWith(SignatureAlgorithm.HS256, configuration.getJwt().getTokenSecretKey())
                .compact();
    }

    public boolean validateToken(final String token) {
        try {
            Jwts.parserBuilder()
                    .requireIssuer("krungsri-registration")
                    .setSigningKey(configuration.getJwt().getTokenSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | MissingClaimException | IncorrectClaimException ex) {
            log.warn("Token expired or invalid.");
        } catch (SignatureException | IllegalArgumentException e) {
            log.debug("Token could not be parsed.");
        }
        return false;
    }

    public JwtTokenResponse getJwtTokenDetail(final String token) {
        var claims = Jwts.parserBuilder()
                .requireIssuer("krungsri-registration")
                .setSigningKey(configuration.getJwt().getTokenSecretKey())
                .build()
                .parseClaimsJws(token);

        return JwtTokenResponse.builder()
                .username(claims.getBody().get("username").toString())
                .accessToken(token).build();
    }
}
