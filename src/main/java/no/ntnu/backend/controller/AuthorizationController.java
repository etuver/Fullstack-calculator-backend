package no.ntnu.backend.controller;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import no.ntnu.backend.dto.AuthorizationDTO;
import no.ntnu.backend.dto.TokenDTO;
import no.ntnu.backend.entity.User;
import no.ntnu.backend.repository.UserRepository;
import no.ntnu.backend.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller for atuhorizing user
 */
@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private final AuthorizationService authorizationService;


    private final String TOKEN_KEY = "jpw8nt-k7a76t-bw4bzk-ies8vg-yeyeye-ye";
    private final long duration = Duration.ofHours(24).toMillis(); // Duration of the token

    @Autowired
    UserRepository userRepository;


    @Autowired
    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }


    @PostMapping("/token")
    public ResponseEntity<Object> getTokenFromCredentials(@RequestBody AuthorizationDTO data) throws Exception{
        String username = data.getEmail();
        String password = data.getPassword();
        System.out.println("Here" + username + password);

        Optional<User> optionalUser = userRepository.findById(username);

        if (optionalUser.isPresent()){
            String token = generateToken(username);
            System.out.println( "token in controller: "+token);
                return ResponseEntity.ok(new TokenDTO(token));
            }

        return ResponseEntity.badRequest().body("Wrong credentials");
    }


    public String generateToken(String userId) throws Exception {
        Key key = Keys.hmacShaKeyFor(TOKEN_KEY.getBytes("UTF-8"));
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("userId", userId);
        claims.put("authorities", grantedAuthorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        Date current = new Date();
        Date expires = new Date(current.getTime() + duration);

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(current)
                .setExpiration(expires)
                .signWith(key)
                .compact();
    }


}


    /**
     * Method to Authenticate a user and returs a JWT
     * @param data credentials
     * @return JWT token

    @PostMapping
    public ResponseEntity<Object> getTokenFromCredentials(@Valid @RequestBody AuthorizationDTO data){
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
        System.out.println("data.email: " + data.getEmail() + ", passord: "+ data.getPassword());
        System.out.println("Credentials: " +credentials);
        String token = authorizationService.getTokenFromCredentials(credentials);
        System.out.println( "token in controller: "+token);

        return ResponseEntity.ok(new TokenDTO(token));

    }
     */