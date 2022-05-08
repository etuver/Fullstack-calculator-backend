package no.ntnu.backend.dto;

import lombok.Data;
import no.ntnu.backend.entity.User;

@Data
public class UserDTO {

    private String email;

    private String name;

    private String password;

    public UserDTO(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
    }


    public UserDTO(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }



}
