package com.example.demo.payloads;

import com.example.demo.entities.StatoViaggio;
import jakarta.validation.constraints.NotNull;

public record StatoViaggioDTO(
        @NotNull(message = "lo stato del viaggio è obbligatorio")
        StatoViaggio statoViaggio) {}
