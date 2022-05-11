package no.ntnu.backend.entity;


import lombok.Getter;
import lombok.Setter;
import no.ntnu.backend.dto.UserDTO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class User {

    @Id
    @Getter
    private String email;
    @Getter
    @Setter
    private String password;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(UserDTO data) {
        this.email = data.getEmail();
        this.password = data.getPassword();
    }

}
