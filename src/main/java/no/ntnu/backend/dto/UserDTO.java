package no.ntnu.backend.dto;

import lombok.Data;
import no.ntnu.backend.entity.User;

@Data
public class UserDTO {

    private String email;

    private String password;

    public UserDTO(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
    }


    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }



}
