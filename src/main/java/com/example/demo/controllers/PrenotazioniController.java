package com.example.demo.controllers;

import com.example.demo.entities.Dipendente;
import com.example.demo.entities.Prenotazione;
import com.example.demo.exceptions.ValidationException;
import com.example.demo.payloads.PrenotazioneDTO;
import com.example.demo.payloads.ViaggioDTO;
import com.example.demo.services.PrenotazioniService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {
    private final PrenotazioniService prenotazioniService;

    public PrenotazioniController(PrenotazioniService prenotazioniService){
        this.prenotazioniService = prenotazioniService;
    }



    @GetMapping
    public Page<Prenotazione> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String orderBy
    ){
        return this.prenotazioniService.findAll(page, size, orderBy);
    }

// post
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public Prenotazione create(@RequestBody @Valid PrenotazioneDTO payload, BindingResult validationResult) throws BadRequestException {
    if (validationResult.hasErrors()){
        throw new ValidationException(
                validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList()
        );

    }
    return prenotazioniService.savePrenotazione(payload);
}
//DELETE
@DeleteMapping("/{prenotazioneId}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void delete(@PathVariable("prenotazioneId") long prenotazioneId) {
    prenotazioniService.findByIdAndDelete(prenotazioneId);
}





}

