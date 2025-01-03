package com.example.blog.Security;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleTokenAuthenticationProvider implements AuthenticationProvider{

    private static final String TOKEN_INFO_URL = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = (String) authentication.getCredentials();
        Map<String, Object> response;

        try {
            response = restTemplate.getForObject(TOKEN_INFO_URL + token, Map.class);
            
            if (response == null || response.containsKey("error")) {
                throw new RuntimeException("Invalid token for getting user info");
            }

            String userId = (String) response.get("sub");
            return new UsernamePasswordAuthenticationToken(userId, token, null);
        } catch (Exception e) {
            throw new BadCredentialsException("Failed to validate token:" + token , e);
        }
    }
    
    @Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
