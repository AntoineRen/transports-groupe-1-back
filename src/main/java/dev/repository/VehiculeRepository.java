package dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.entites.VehiculeSociete;

@Repository
public interface VehiculeRepository extends JpaRepository<VehiculeSociete, Integer> {

}
