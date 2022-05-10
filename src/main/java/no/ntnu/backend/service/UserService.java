package no.ntnu.backend.service;

import no.ntnu.backend.entity.User;
import no.ntnu.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new user
     * @param user
     * @return The user
     */
    public User saveUser(User user){
        System.out.println("Saved user");
        return userRepository.save(user);
    }

    /**
     * Get a specific user by email
     * @param email
     * @return user found
     */
    public Optional<User> getUserByMail(String email){
        return userRepository.findById(email);
    }




}
