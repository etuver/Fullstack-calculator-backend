package no.ntnu.backend.dto;

import lombok.Data;

@Data
public class AuthorizationDTO {

    private String email;
    private String password;

    public AuthorizationDTO(String email, String password){
        this.password = password;
        this.email = email;
    }


}
