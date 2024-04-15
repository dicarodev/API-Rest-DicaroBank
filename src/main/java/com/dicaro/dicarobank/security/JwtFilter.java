package com.dicaro.dicarobank.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Extract the JWT token from the header http request and validate it.
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userService;
    private final JwtTokeProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Extract the JWT token from the header http request.
        String token = extractToken(request);

        // If the token is valid, set the authentication in the security context holder.
        if (tokenProvider.isValidToken(token)){
            String dni = tokenProvider.getDniFromToken(token); //Get the user dni from the token.
            UserDetails user = userService.loadUserByUsername(dni); //Get the user details from the user service.

            //Create a new authentication object with the user details and add it to the security context.
            Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());

            // Set the authentication in the security context holder.
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // Continue the filter chain with the request and response objects.
        filterChain.doFilter(request, response);
    }

    /**
     * Extract the JWT token from the header http request.
     * @param request HttpServletRequest
     * @return the JWT token
     */
    private String extractToken(HttpServletRequest request){
        //Authorization: Bearer <token> gets the token from the header request.
        String bearerToken = request.getHeader("Authorization");

        // If the token is not null and starts with Bearer, return the token without the prefix.
        if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring("Bearer ".length());
        }

        return null;
    }
}
