package com.example.demo.repositories;

import com.example.demo.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViaggiRepository extends JpaRepository<Viaggio, Long> {
}
