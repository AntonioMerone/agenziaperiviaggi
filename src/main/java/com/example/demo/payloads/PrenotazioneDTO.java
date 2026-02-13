package com.example.demo.payloads;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PrenotazioneDTO(

        @NotNull(message = "La data della prenotazione è obbligatoria")
        @FutureOrPresent(message = "La data del viaggio non può essere all'indietro")
        LocalDate dataPrenotazione,

        @Size(max = 1000, message = "Note troppo lunghe, non più di 1000")
        String note,

        @NotNull(message = "id  viaggio è obbligatorio")
        Long viaggioId,

        @NotNull(message = "id  dipendente è obbligatorio")
        Long dipendenteId

) {
}