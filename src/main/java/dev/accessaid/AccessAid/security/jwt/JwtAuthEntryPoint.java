package dev.accessaid.AccessAid.security.jwt;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.accessaid.AccessAid.config.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        log.error("Unauthorized error: {}", authException.getMessage());

        ErrorResponse errorResponse = new ErrorResponse("bad credentials");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), errorResponse);

    }
}
