package dev.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entites.Annonce;

public interface AnnonceRepository extends JpaRepository<Annonce, UUID> {

}


