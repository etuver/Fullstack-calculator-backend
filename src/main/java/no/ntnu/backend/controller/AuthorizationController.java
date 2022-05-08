package no.ntnu.backend.controller;


import no.ntnu.backend.dto.AuthorizationDTO;
import no.ntnu.backend.dto.TokenDTO;
import no.ntnu.backend.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller for atuhorizing user
 */
@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private final AuthorizationService authorizationService;


    @Autowired
    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }


    /**
     * Method to Authenticate a user and returs a JWT
     * @param data credentials
     * @return JWT token
     */
    @PostMapping
    public ResponseEntity<Object> getTokenFromCredentials(@Valid @RequestBody AuthorizationDTO data){
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
        String token = authorizationService.getTokenFromCredentials(credentials);

        return ResponseEntity.ok(new TokenDTO(token));

    }

}
