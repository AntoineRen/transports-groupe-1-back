package dev.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.entites.VehiculeSociete;

@Repository
public interface VehiculeSocieteRepository extends JpaRepository<VehiculeSociete, Long> {

	Optional<VehiculeSociete> findOneByImmatriculation(String immatriculation);

}
