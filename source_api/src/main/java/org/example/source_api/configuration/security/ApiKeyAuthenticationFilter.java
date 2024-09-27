package org.example.source_api.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.source_api.entity.user.ApiUser;
import org.example.source_api.repository.user.ApiUserRepository;
import org.example.source_api.utils.AccessTokenHelper;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * A filter that implements API key authentication by validating access tokens passed in the HTTP header.
 * It checks if the provided access token is valid and not expired. If the token is valid, an authentication
 * object is created and set in the security context.
 */
//@Component
public class ApiKeyAuthenticationFilter implements Filter {
    private final Logger logger;
    private final ApiUserRepository apiUserRepository;
    private final String validityPeriod;
    private final AccessTokenHelper accessTokenHelper;

    ApiKeyAuthenticationFilter(final Logger logger,
                               final ApiUserRepository apiUserRepository,
                               final String validityPeriod,
                               final AccessTokenHelper accessTokenHelper) {
        this.logger = logger;
        this.apiUserRepository = apiUserRepository;
        this.validityPeriod = validityPeriod;
        this.accessTokenHelper = accessTokenHelper;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Filters incoming requests to authenticate using API key-based access tokens.
     *
     * @param request the ServletRequest object that contains the client's request
     * @param response the ServletResponse object that contains the filter's response
     * @param chain the FilterChain for invoking the next filter or the resource
     * @throws IOException if an input or output error is detected when the filter handles the request
     * @throws ServletException if the request could not be handled
     */
    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {
        logger.info("ApiKeyAuthenticationFilter doFilter for access token auth");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String accessToken = httpServletRequest.getHeader("Access-Token");
        logger.info("ApiKeyAuthenticationFilter doFilter: got access token from header: {}", accessToken);
        if (accessToken == null) {
            unauthorized(httpServletResponse);
            return;
        }

        accessToken = accessToken.replaceAll("[{}]", "");

        Optional<ApiUser> userOptional = apiUserRepository.findByAccessToken(accessToken);
        if(!userOptional.isPresent()) {
            logger.info("No user found with token: {}", accessToken);
            unauthorized(httpServletResponse);
            return;
        } else {
            logger.info("User found with token: {}", accessToken);

            ApiUser user = userOptional.get();
            OffsetDateTime createdAt = user.getCreatedAt();
            if (!accessTokenHelper.isAccessTokenValid(createdAt.toLocalDateTime())) {
                logger.info("Token expired for user: {}", user.getId().getUsername());
                unauthorized(httpServletResponse);
                return;
            }

            // Create an authentication object and set it in the security context
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    user, // principal
                    null, // credentials (you can pass null here for API key-based auth)
                    Collections.emptyList() // authorities (or roles if you have any)
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private void unauthorized(final HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Map<String, Object> response = Map.of("message", "SC_UNAUTHORIZED");
        String responseBody = new ObjectMapper().writeValueAsString(response);
        httpServletResponse.getWriter().write(responseBody);

    }
}
