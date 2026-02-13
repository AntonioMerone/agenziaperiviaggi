package com.example.demo.services;


import com.example.demo.entities.Dipendente;
import com.example.demo.entities.Prenotazione;
import com.example.demo.entities.Viaggio;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.payloads.PrenotazioneDTO;
import com.example.demo.repositories.PrenotazioniRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class PrenotazioniService {

    private final PrenotazioniRepository prenotazioniRepository;
    private final DipendentiService dipendentiService;
    private final ViaggiService viaggiService;

    public Page<Prenotazione> findAll(int page, int size, String orderBy) {
        if (size > 200 || size <= 0) size = 10;
        if (page < 0) page = 0;

        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return prenotazioniRepository.findAll(pageable);
    }

@Autowired
    public PrenotazioniService(PrenotazioniRepository prenotazioniRepository, DipendentiService dipendentiService, ViaggiService viaggiService) {
        this.prenotazioniRepository = prenotazioniRepository;
        this.dipendentiService = dipendentiService;
        this.viaggiService = viaggiService;
    }
    public Prenotazione savePrenotazione(PrenotazioneDTO payload) throws BadRequestException {

        Dipendente dipendente = dipendentiService.findById(payload.dipendenteId());
        Viaggio viaggio = viaggiService.findById(payload.viaggioId());

        if (prenotazioniRepository
                .existsByDipendenteIdAndViaggio_DataViaggio(
                        dipendente.getId(),
                        viaggio.getDataViaggio())) {

            throw new BadRequestException(
                    "Il dipendente " + dipendente.getUsername() +
                            " ha già un viaggio prenotato per questa data"
            );
        }

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setDipendente(dipendente);
        prenotazione.setViaggio(viaggio);
        prenotazione.setNote(payload.note());
        prenotazione.setDataPrenotazione(LocalDate.now());

        return prenotazioniRepository.save(prenotazione);
    }
    public void findByIdAndDelete(long prenotazioneId) {
        Prenotazione found = prenotazioniRepository.findById(prenotazioneId)
                .orElseThrow(() -> new NotFoundException(
                        "Prenotazione con id " + prenotazioneId + " non trovata"
                ));

        prenotazioniRepository.delete(found);
    }

}


