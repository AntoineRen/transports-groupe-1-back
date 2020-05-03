package dev.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.entites.Vehicule;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Integer> {

}
