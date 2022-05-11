package no.ntnu.backend.service;

import no.ntnu.backend.entity.User;
import no.ntnu.backend.repository.UserRepository;
import no.ntnu.backend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


/**
 * guide here
 * https://www.baeldung.com/spring-security-authentication-with-a-database
 */

@Service
@Transactional
public class UserDetailsServiceimpl implements UserDetailsService {

    private final UserRepository userRepository;


    public UserDetailsServiceimpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(email);

        if (user.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return UserPrincipal.of(user.get());
    }
}
