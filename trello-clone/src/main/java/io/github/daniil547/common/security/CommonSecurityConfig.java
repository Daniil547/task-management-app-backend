package io.github.daniil547.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

@Configuration
public class CommonSecurityConfig {

    @Bean(name = "whiteListedUrisMatcher")
    public RequestMatcher whiteListedRequestUrisMatcher() {
        return req -> List.of("/v2/api-docs",
                              "/swagger-resources",
                              "/swagger-resources/**",
                              "/configuration/ui",
                              "/configuration/security",
                              "/webjars/**",
                              "/login",
                              "/swagger-ui/**",
                              "favicon.ico")
                          .contains(req.getRequestURI());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager noopAuthManager() {
        return new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                authentication.setAuthenticated(true);

                SecurityContextHolder.getContext().setAuthentication(authentication);
                return authentication;
            }
        };
    }
}