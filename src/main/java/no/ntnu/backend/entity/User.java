package no.ntnu.backend.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class User {

    @Id
    @Getter
    private String email;

    @NotNull
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String password;

    public User() {
    }

    public User(String email, String name,  String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

}
