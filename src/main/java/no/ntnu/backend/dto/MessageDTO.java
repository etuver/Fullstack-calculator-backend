package no.ntnu.backend.dto;

import lombok.Data;

@Data
public class MessageDTO {
    private String message;

    public MessageDTO(){

    }

    public MessageDTO(String message){
        this.message = message;
    }



}
