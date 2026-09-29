package br.com.vladison.qa.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Value("${qa.api-key}")
    private String expectedApiKey;

    public ApiKeyFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        boolean protectedMethod = switch (request.getMethod()) {
            case "POST", "PUT", "DELETE" -> request.getRequestURI().startsWith("/api/");
            default -> false;
        };

        if (protectedMethod && !expectedApiKey.equals(request.getHeader("X-API-Key"))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            objectMapper.writeValue(response.getWriter(), Map.of(
                    "timestamp", Instant.now().toString(),
                    "status", 401,
                    "error", "Unauthorized",
                    "message", "Missing or invalid X-API-Key",
                    "path", request.getRequestURI()
            ));
            return;
        }
        filterChain.doFilter(request, response);
    }
}
