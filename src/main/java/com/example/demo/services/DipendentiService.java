package com.example.demo.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.entities.Dipendente;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.payloads.DipendenteDTO;
import com.example.demo.repositories.DipendentiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class DipendentiService {

    private final DipendentiRepository dipendentiRepository;
    private final Cloudinary cloudinary;

    public DipendentiService(DipendentiRepository dipendentiRepository, Cloudinary cloudinary) {
        this.dipendentiRepository = dipendentiRepository;
        this.cloudinary = cloudinary;
    }

    public Page<Dipendente> findAll(int page, int size, String orderBy) {
        if (size > 200 || size <= 0) size = 10;
        if (page < 0) page = 0;

        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return dipendentiRepository.findAll(pageable);
    }



    public Dipendente saveDipendente(DipendenteDTO payload) {

        Dipendente dipendente = new Dipendente(
                payload.username(),
                payload.nome(),
                payload.cognome(),
                payload.email()
        );
        return dipendentiRepository.save(dipendente);

    }

    public Dipendente findById(long dipendenteId) {
        return dipendentiRepository.findById(dipendenteId)
                .orElseThrow(() -> new NotFoundException("uil dipendente con l'id" + dipendenteId +" non è trovato"));
    }
public Dipendente updateDipendente(long dipendenteId, DipendenteDTO payload){
        Dipendente found = findById(dipendenteId);

    found.setUsername(payload.username());
    found.setNome(payload.nome());
    found.setCognome(payload.cognome());
    found.setEmail(payload.email());

    Dipendente updated = dipendentiRepository.save(found);
    log.info("Dipendente aggiornato. id={}", dipendenteId);

    return updated;
    }

    public void findByIdAndDelete(long dipendenteId) {
        Dipendente found = findById(dipendenteId);
        dipendentiRepository.delete(found);
        log.info("Dipendente eliminato. id={}", dipendenteId);
    }

    // Upload cover (l'avatar)
    public Dipendente uploadCover(long blogId, MultipartFile file) {
        Dipendente found = findById(blogId);

        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File mancante o vuoto");
        }

        try {
            Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) result.get("secure_url");

            found.setAvatarUrl(imageUrl);
            Dipendente updated = dipendentiRepository.save(found);

            log.info("Cover blog aggiornata. id={}, url={}", blogId, imageUrl);
            return updated;

        } catch (IOException e) {
            throw new BadRequestException("Errore durante upload immagine");
        }
}

}
