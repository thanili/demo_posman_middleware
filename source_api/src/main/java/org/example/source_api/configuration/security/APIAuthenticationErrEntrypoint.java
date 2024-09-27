package org.example.source_api.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Component responsible for handling authentication errors in API requests.
 * This class implements AuthenticationEntryPoint and customizes the response
 * to unauthorized access attempts.
 */
@Component
public class APIAuthenticationErrEntrypoint implements AuthenticationEntryPoint {
    /**
     * Handles authentication errors by customizing the response to unauthorized access attempts.
     *
     * @param httpServletRequest  the HttpServletRequest object that triggered the exception
     * @param httpServletResponse the HttpServletResponse object used to return the response
     * @param e                   the AuthenticationException that triggered this handler
     * @throws IOException if an input or output exception occurs while handling the response
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        httpServletResponse.setStatus(401);
        Map<String, Object> response = Map.of("message", "SC_UNAUTHORIZED");
        String responseBody = new ObjectMapper().writeValueAsString(response);
        httpServletResponse.getWriter().write(responseBody);
    }
}
