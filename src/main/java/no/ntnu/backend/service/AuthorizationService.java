package no.ntnu.backend.service;

import no.ntnu.backend.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthorizationService(AuthenticationManager authenticationManager, JwtProvider jwtProvider ) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }


    public String getTokenFromCredentials(UsernamePasswordAuthenticationToken token) {
        Authentication auth = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);

        return jwtProvider.generateToken(auth);
    }
}
