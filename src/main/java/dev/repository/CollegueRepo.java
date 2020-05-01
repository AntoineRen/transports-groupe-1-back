package dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entites.Collegue;

import java.util.Optional;

public interface CollegueRepo extends JpaRepository<Collegue, Long> {

    Optional<Collegue> findByEmail(String email);
}
