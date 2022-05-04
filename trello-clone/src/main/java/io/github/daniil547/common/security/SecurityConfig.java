package io.github.daniil547.common.security;

import io.github.daniil547.common.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthorizationFilter authorizationFilter;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          AuthorizationFilter authorizationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authorizationFilter = authorizationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors()
            .and()
// making swagger work requires both the whitelist filter and this config below
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/v2/api-docs").permitAll()
            .antMatchers("/swagger-resources").permitAll()
            .antMatchers("/swagger-resources/**").permitAll()
            .antMatchers("/configuration/ui").permitAll()
            .antMatchers("/configuration/security").permitAll()
            .antMatchers("/webjars/**").permitAll()
            .antMatchers("/login").permitAll()
            .antMatchers("/swagger-ui/**").permitAll()
            .antMatchers("favicon.ico").permitAll()
            .anyRequest().authenticated()
            .and()

            .addFilter(jwtAuthenticationFilter)
            .addFilterAfter(authorizationFilter, JwtAuthenticationFilter.class)
            // this disables session creation on Spring Security
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

            .headers().xssProtection()
            .and()
            .contentSecurityPolicy("script-src 'self'");
    }
}