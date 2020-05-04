package dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entites.Annonce;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {

}
