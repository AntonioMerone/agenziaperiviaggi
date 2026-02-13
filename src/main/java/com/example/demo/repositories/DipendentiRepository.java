package com.example.demo.repositories;

import com.example.demo.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DipendentiRepository extends JpaRepository<Dipendente, Long> {
    Optional<Dipendente> findByUsername(String username);
}
