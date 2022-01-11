package com.example.projetfestival.payload;

import lombok.Data;

@Data
public class ConnexionDto {
    private String usernameOrEmail;
    private String password;
}
