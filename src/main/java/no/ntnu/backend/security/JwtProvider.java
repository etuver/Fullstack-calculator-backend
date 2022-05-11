package no.ntnu.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Component
public class JwtProvider {

    private final String TOKEN_KEY = "jpw8nt-k7a76t-bw4bzk-ies8vg";
    private final long duration = Duration.ofHours(24).toMillis(); // Duration of the token

    /**
     * Method to generate a token with a set duration
     * As thought by example given in class
     * <a href="https://gitlab.com/sysdev-tutorials/springboot/auth_demo/-/blob/04-protect-api-tokenbased-final/src/main/java/com/example/demo/controller/TokenController.java">https://gitlab.com/sysdev-tutorials/springboot/auth_demo/-/blob/04-protect-api-tokenbased-final/src/main/java/com/example/demo/controller/TokenController.java</a>
     * @param authentication the user
     * @return The token
     */
    public String generateToken(Authentication authentication){
        Key key = Keys.hmacShaKeyFor(TOKEN_KEY.getBytes());
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        Date current = new Date();
        Date expires = new Date(current.getTime() + duration);

        return Jwts.builder().setSubject(principal.getUsername()).setIssuedAt(current).setExpiration(expires).signWith(key).compact();
}


    /**
     * Method to validate a given token
     * @param token the token
     * @return true if successful
     */
    public boolean validateToken(String token){
        Key key = Keys.hmacShaKeyFor(TOKEN_KEY.getBytes(StandardCharsets.UTF_8));
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        try {
            jwtParser.parseClaimsJwt(token);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Method to get username from a given token
     * @param token the token
     * @return the username as a string
     */
    public String getUsername(String token){
        Key key = Keys.hmacShaKeyFor(TOKEN_KEY.getBytes(StandardCharsets.UTF_8));
        JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();
        Claims claims = parser.parseClaimsJws(token).getBody();
        return claims.getSubject();
    }



}
