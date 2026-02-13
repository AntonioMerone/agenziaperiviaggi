package com.example.demo.controllers;

import com.example.demo.entities.Dipendente;
import com.example.demo.entities.Viaggio;
import com.example.demo.exceptions.ValidationException;
import com.example.demo.payloads.StatoViaggioDTO;
import com.example.demo.payloads.ViaggioDTO;
import com.example.demo.services.ViaggiService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viaggi")
public class ViaggiController {

    private final ViaggiService viaggiService;

    public ViaggiController(ViaggiService viaggiService) {
        this.viaggiService = viaggiService;
    }

    @GetMapping
    public Page<Viaggio> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String orderBy
    ){
        return this.viaggiService.findAll(page, size, orderBy);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio create(@RequestBody @Valid ViaggioDTO payload) {
        return viaggiService.saveViaggio(payload);
    }

    @GetMapping("/{viaggioId}")
    public Viaggio findById(@PathVariable("viaggioId") long viaggioId) {
        return viaggiService.findById(viaggioId);
    }

    @PutMapping("/{viaggioId}")
    public Viaggio update(
            @PathVariable("viaggioId") long viaggioId,
            @RequestBody @Valid ViaggioDTO payload) {

        return viaggiService.findByIdAndUpdate(viaggioId, payload);
    }

    @DeleteMapping("/{viaggioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("viaggioId") long viaggioId) {
        viaggiService.findByIdAndDelete(viaggioId);
    }

    @PatchMapping("/{viaggioId}/statoViaggio")
    public Viaggio updateStatoViaggio(
            @PathVariable("viaggioId") long viaggioId,
            @RequestBody @Valid StatoViaggioDTO payload,
            BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            throw new ValidationException(
                    validationResult.getFieldErrors().stream()
                            .map(fieldError -> fieldError.getDefaultMessage())
                            .toList()
            );
        }

        return viaggiService.UpdateStatoViaggio(viaggioId, payload.statoViaggio());
    }
}
