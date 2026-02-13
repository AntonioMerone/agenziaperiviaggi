package com.example.demo.payloads;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PrenotazioneDTO(

        @Size(max = 1000, message = "Note troppo lunghe, non più di 1000")
        String note,

        @NotNull(message = "id  viaggio è obbligatorio")
        Long viaggioId,

        @NotNull(message = "id  dipendente è obbligatorio")
        Long dipendenteId

) {
}