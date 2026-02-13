package com.example.demo.repositories;


import com.example.demo.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazione, Long> {
    boolean existsByDipendenteIdAndViaggio_DataViaggio(Long dipendenteId, LocalDate dataViaggio);
}
