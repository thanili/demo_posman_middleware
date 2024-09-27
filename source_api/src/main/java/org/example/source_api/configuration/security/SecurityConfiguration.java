package org.example.source_api.configuration.security;

import org.example.source_api.repository.user.ApiUserRepository;
import org.example.source_api.utils.AccessTokenHelper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class that sets up web security for different API endpoints.
 * It defines two security filter chains for different sets of endpoints.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final Logger logger;
    private final APIAuthenticationErrEntrypoint apiAuthenticationErrEntrypoint;
    private final ApiUserRepository apiUserRepository;
    private final AccessTokenHelper accessTokenHelper;

    @Value("${net.mspos.possible.service.apikey.valid.period}")
    private String validityPeriod;

    @Autowired
    public SecurityConfiguration(Logger logger,
                                 APIAuthenticationErrEntrypoint apiAuthenticationErrEntrypoint,
                                 ApiUserRepository apiUserRepository, AccessTokenHelper accessTokenHelper
    ) {
        this.logger = logger;
        this.apiAuthenticationErrEntrypoint = apiAuthenticationErrEntrypoint;
        this.apiUserRepository = apiUserRepository;
        this.accessTokenHelper = accessTokenHelper;
    }

    /**
     * Configures the security filter chain for API endpoints requiring API key authentication.
     * This filter chain applies to requests matching the pattern "/api/customer/**".
     *
     * @param http the HttpSecurity to modify.
     * @return a configured SecurityFilterChain object.
     * @throws Exception if an error occurs while configuring the security filter chain.
     */
    @Bean
    @Order(1)
    public SecurityFilterChain filterChainApiKey(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/customer/**")
                .authorizeHttpRequests((authz) -> authz.anyRequest().authenticated()) // Specify that all requests should be authenticated
                .addFilterBefore(
                        new ApiKeyAuthenticationFilter(logger, apiUserRepository, validityPeriod, accessTokenHelper),
                        UsernamePasswordAuthenticationFilter.class
                ) // Add the custom filter
                .exceptionHandling((auth) -> auth.authenticationEntryPoint(apiAuthenticationErrEntrypoint)) // Configure exception handling
                .cors((cors) -> cors.disable())
                .csrf((csrf) -> csrf.disable());
        return http.build();
    }

    /**
     * Configures the security filter chain for API endpoints that do not require authentication.
     * This filter chain applies to requests matching the pattern "/api/user/**",
     * allowing all such requests without authentication.
     *
     * @param http the HttpSecurity to modify
     * @return a configured SecurityFilterChain object
     * @throws Exception if an error occurs while configuring the security filter chain
     */
    @Bean
    @Order(2)
    public SecurityFilterChain filterChainNoAuth(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/api/user/**").permitAll()
                )
                .csrf((csrf) -> csrf.disable()); // Disable CSRF

        return http.build();
    }
}
