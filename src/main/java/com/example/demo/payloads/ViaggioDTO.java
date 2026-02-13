package com.example.demo.payloads;

import com.example.demo.entities.StatoViaggio;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ViaggioDTO(

        @NotBlank(message = "La destinazione è obbligatoria")
        @Size(min = 2, max = 30, message = "La destinazione deve essere tra 2 e 30 caratteri")
        String destinazione,

        @NotNull(message = "La data del viaggio è obbligatoria")
        @FutureOrPresent(message = "La data del viaggio non può essere all'indietro")
        LocalDate dataViaggio,

        @NotNull(message = "Lo stato del viaggio è obbligatorio")
        StatoViaggio statoViaggio
) {
}