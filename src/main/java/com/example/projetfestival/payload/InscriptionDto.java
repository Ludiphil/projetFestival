package com.example.projetfestival.payload;

import lombok.Data;

@Data
public class InscriptionDto {
    private String name;
    private String username;
    private String email;
    private String password;
}
