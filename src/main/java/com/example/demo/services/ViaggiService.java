package com.example.demo.services;


import com.example.demo.entities.StatoViaggio;
import com.example.demo.entities.Viaggio;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.payloads.ViaggioDTO;
import com.example.demo.repositories.ViaggiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j

public class ViaggiService {

    private final ViaggiRepository viaggiRepository;

    public Page<Viaggio> findAll(int page, int size, String orderBy) {
        if (size > 200 || size <= 0) size = 10;
        if (page < 0) page = 0;

        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return viaggiRepository.findAll(pageable);
    }


    public ViaggiService(ViaggiRepository viaggiRepository) {
        this.viaggiRepository = viaggiRepository;
    }

    public Viaggio saveViaggio(ViaggioDTO bodypeyloadviaggio) {
        Viaggio viaggio = new Viaggio(
                bodypeyloadviaggio.destinazione(),
                bodypeyloadviaggio.dataViaggio(),
                bodypeyloadviaggio.statoViaggio()
        );
        return viaggiRepository.save(viaggio);

    }

    public Viaggio findById(long viaggioId) {
        return viaggiRepository.findById(viaggioId).orElseThrow(() -> new NotFoundException("la risorsa con id " + viaggioId + " non è stata trovata!"));
    }
    public Viaggio findByIdAndUpdate(long viaggioId, ViaggioDTO bodypeyloadviaggio){
        Viaggio found = findById(viaggioId);

        found.setDestinazione(bodypeyloadviaggio.destinazione());
        found.setDataViaggio(bodypeyloadviaggio.dataViaggio());
        found.setStatoViaggio(bodypeyloadviaggio.statoViaggio());

        Viaggio updated = viaggiRepository.save(found);
        log.info("viaggio aggiornato. id={}", viaggioId);
        return updated;

    }

    public Viaggio UpdateStatoViaggio(long viaggioId, StatoViaggio newViaggioAggiornato){
        Viaggio found = this.findById(viaggioId);
        found.setStatoViaggio(newViaggioAggiornato);
        return viaggiRepository.save(found);
    }


    public void findByIdAndDelete(long viaggioId){
        Viaggio found = findById(viaggioId);
        viaggiRepository.delete(found);
        log.info("Viaggio cancellato/eliminato. id={}", viaggioId);
    }
}










