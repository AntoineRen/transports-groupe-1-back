package dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.entites.Reservation;
import dev.entites.VehiculeSociete;

@Repository
public interface VehiculeSocieteRepository extends JpaRepository<VehiculeSociete, Long> {


	VehiculeSociete findOneByImmatriculation(String immatriculation);
	
}
