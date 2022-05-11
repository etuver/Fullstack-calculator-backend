package no.ntnu.backend.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtProvider tokenProvider, UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }


    /**
     * Method to validate token in http header
     * @param request asks for token
     * @param response sends token
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
        String token = getTokenFromRequest(request);

        if (token != null){
            if (!tokenProvider.validateToken(token)){
                System.out.println("invalid token");
                throw new IllegalAccessException("Invalid token");
            }
            String username = tokenProvider.getUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        }catch (Exception e){
            logger.error("Authentication failed: "+e.getMessage());
        }
        filterChain.doFilter(request, response);

    }

    /**
     * Method to get token from http- request
     * @param request the request
     * @return the token or null
     */
    private String getTokenFromRequest(HttpServletRequest request){
        String bearer = request.getHeader("Authorization");
        String PREFIX = "Bearer ";
        if (bearer == null || bearer.isBlank() || !bearer.startsWith(PREFIX)){
            return null;
        }
        return bearer.substring(7); //Remove PREFIX

    }



}
