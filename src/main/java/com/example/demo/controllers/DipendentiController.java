package com.example.demo.controllers;

import com.example.demo.entities.Dipendente;
import com.example.demo.payloads.DipendenteDTO;
import com.example.demo.services.DipendentiService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/dipendenti")

public class DipendentiController {

    private final DipendentiService dipendentiService;

    public DipendentiController(DipendentiService dipendentiService) {
        this.dipendentiService = dipendentiService;
    }

    @GetMapping
    public Page<Dipendente> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String orderBy
    ){
        return this.dipendentiService.findAll(page, size, orderBy);
    }





    //post
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente create(@RequestBody @Valid DipendenteDTO payload) {
        return dipendentiService.saveDipendente(payload);
    }
    //get
    @GetMapping("/{dipendenteId}")
    public Dipendente findById(@PathVariable long dipendenteId) {
        return dipendentiService.findById(dipendenteId);
    }

    //put
    @PutMapping("/{dipendenteId}")
    public Dipendente update(
            @PathVariable long dipendenteId,
            @RequestBody @Valid DipendenteDTO payload) {

        return dipendentiService.updateDipendente(dipendenteId, payload);
    }

    //delete
    @DeleteMapping("/{dipendenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long dipendenteId){
        dipendentiService.findByIdAndDelete(dipendenteId);



    }

    //upload cover (l'avatar)
    @PostMapping("/{dipendenteId}/avatar")
    public Dipendente uploadAvatar(
            @PathVariable long dipendenteId,
            @RequestParam("avatar") MultipartFile file) {

        return dipendentiService.uploadCover(dipendenteId, file);
    }
}
