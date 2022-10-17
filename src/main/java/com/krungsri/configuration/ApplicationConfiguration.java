package com.krungsri.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("application")
public class ApplicationConfiguration {
    private JwtConfiguration jwt;

    @Getter
    @Setter
    public static class JwtConfiguration {
        private String tokenSecretKey;
        private Long tokenExpirationMs;
    }
}
