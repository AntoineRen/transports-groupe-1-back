package dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.entites.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

}
