package no.ntnu.backend.controller;
import no.ntnu.backend.dto.MessageDTO;
import no.ntnu.backend.dto.UserDTO;
import no.ntnu.backend.entity.User;
import no.ntnu.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Create a new user
     * @param data userdata
     * @return new user
     */
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserDTO data) {
        return ResponseEntity.ok(new UserDTO(userService.saveUser(new User(data))));
    }

    /**
     * Get the current user
     * @param principal The user
     * @return The user
     */
    @GetMapping
    public ResponseEntity<Object> getCurrentUser(Principal principal){
        Optional<User> optionalUser = userService.getUserByMail(principal.getName());
        if (optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO("User not found"));
        }
        return ResponseEntity.ok(new UserDTO(optionalUser.get()));
    }












}
