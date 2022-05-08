package no.ntnu.backend.dto;

import lombok.Data;

@Data
public class AuthorizationDTO {

    private String username;
    private String password;

    public AuthorizationDTO(String username, String password){
        this.password = password;
        this.username = username;
    }


}
