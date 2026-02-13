package com.example.demo.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DipendenteDTO(

        @NotBlank(message =  "username è obbligatorio")
        @Size(min = 3, max = 20, message = "almeno 3 e 20 caratteri")
        String username,

        @NotBlank(message = "Il nome è obbligatorio")
        @Size(min = 2, max = 30, message = "almeno 2 e 30 caratteri")
        String nome,

        @NotBlank(message = "Il cognome è obbligatorio")
        @Size(min = 2, max = 30, message = "almeno 2 e 30 caratteri")
        String cognome,

        @NotBlank(message = "L'email è obbligatoria")
        @Email(message = "Formato email non valido, controlla @ o altro")
        String email


) {
}
