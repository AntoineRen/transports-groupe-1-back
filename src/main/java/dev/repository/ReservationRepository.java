package dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.entites.Collegue;
import dev.entites.Reservation;
import dev.entites.VehiculeSociete;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	List<Reservation> findAllByResponsable(Collegue collegue);

	List<Reservation> findAllByVehicule(VehiculeSociete vehicule);

}
