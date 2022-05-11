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

    private final AuthenticationManager authManager;
    private final JwtProvider tokenProvider;

    @Autowired
    public AuthorizationService(AuthenticationManager authManager, JwtProvider tokenProvider) {
        this.authManager = authManager;
        this.tokenProvider = tokenProvider;
    }

    /**
     * Gets a token from given credentials.
     *
     * @param token
     *            Credentials.
     *
     * @return JWT.
     */
    public String getTokenFromCredentials(UsernamePasswordAuthenticationToken token) {
        Authentication auth = authManager.authenticate(token); //this one failing
        SecurityContextHolder.getContext().setAuthentication(auth);

        return tokenProvider.generateToken(auth);
    }
}
