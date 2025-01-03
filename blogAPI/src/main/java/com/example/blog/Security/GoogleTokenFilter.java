package com.example.blog.Security;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.accept.ServletPathExtensionContentNegotiationStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GoogleTokenFilter extends OncePerRequestFilter {
    
    private static final String TOKEN_INFO_URL = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
        throws ServletException , IOException{

            String path = request.getRequestURI();

            if (!path.startsWith("/api/auth")) {
                filterChain.doFilter(request, response);
                return;
            }

            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Authorization header is missing");
                return;
            } 

            String token = authHeader.substring(7);
            try {
                String userId = validateGoogleToken(token);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, null , null);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            } catch (RuntimeException e) {
                logger.error("Token validation failed", e);
                sendErrorResponse(response, HttpStatus.UNAUTHORIZED, e.getMessage());
                return;    
            }

            filterChain.doFilter(request, response);
    }

    private String validateGoogleToken(String token) {
        String url = TOKEN_INFO_URL + token;
        Map<String, Object> tokenInfo = restTemplate.getForObject(url, Map.class);
        
        if (tokenInfo == null || tokenInfo.containsKey("error")) {
            throw new RuntimeException("Invalid token for getting user info");
        }

        String userId = (String) tokenInfo.get("sub");
        return userId;
    }

     private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        String jsonError = String.format("{\"error\": \"%s\", \"status\": %d}", message, status.value());
        response.getWriter().write(jsonError);
    }

}
