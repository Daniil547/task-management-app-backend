package io.github.daniil547.common.security.jwt;

import io.github.daniil547.user.UserCredentials;
import io.github.daniil547.user.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.UUID;

import static java.util.Objects.requireNonNullElse;

@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

    private final JwtTokenManager jwtTokenManager;
    private final RequestMatcher requestMatcher; // TODO not needed?
    private final UserCredentialsRepository userCredsRepo;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public JwtAuthenticationFilter(RequestMatcher requestMatcher,
                                   JwtTokenManager jwtTokenManager,
                                   UserCredentialsRepository userCredsRepo,
                                   PasswordEncoder passwordEncoder,
                                   AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.jwtTokenManager = jwtTokenManager;
        this.requestMatcher = requestMatcher;
        this.userCredsRepo = userCredsRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        if (requestMatcher.matches(request)) {
            return null;
        }

        String username = requireNonNullElse(obtainUsername(request), "").strip();
        String password = requireNonNullElse(obtainPassword(request), "").strip();
        String token = obtainToken(request);

        if (token == null) {
            if (!username.equals("") && !password.equals("")) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
                                                                                                             password,
                                                                                                             Collections.emptyList());


                UserCredentials userCreds = userCredsRepo.findByIdentifier(username)
                                                         .orElseThrow(() -> {
                                                             throw new BadCredentialsException("Invalid username or password");
                                                         });

                if (passwordEncoder.matches(password, userCreds.getPassword())) {
                    return this.getAuthenticationManager().authenticate(authentication);
                } else {
                    // technically that should be an "invalid password"
                    // but I guess it's better not to give out this info
                    throw new BadCredentialsException("Invalid username or password");
                }
            } else if (!password.equals("")) {
                throw new BadCredentialsException("Username or email must not be empty");
            } else if (!username.equals("")) {
                throw new BadCredentialsException("Password must not be empty");
            } else {
                SecurityContextHolder.clearContext();
                return null; // user is anonymous
            }
        } else {
            UUID userId = jwtTokenManager.validateAndExtractPrincipal(token)
                                         .orElseThrow(() -> {
                                             throw new BadCredentialsException("Expired or invalid JWT token");
                                         });

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId,
                                                                                                         "",
                                                                                                         Collections.emptyList());

            return this.getAuthenticationManager().authenticate(authentication);
        }
    }

    public String obtainToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(BEARER_TOKEN_PREFIX)) {
            return bearerToken.substring(BEARER_TOKEN_PREFIX.length());
        }
        return null;
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        String token = jwtTokenManager.generateToken(UUID.fromString(authResult.getName()));
        response.addHeader(HttpHeaders.AUTHORIZATION, BEARER_TOKEN_PREFIX + token);
    }

}