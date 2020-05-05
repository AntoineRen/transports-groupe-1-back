package dev.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.Reservation;
import dev.entites.dto.ReservationDto;
import dev.exceptions.CollegueNonTrouveException;
import dev.service.ReservationService;

@RestController
@RequestMapping("reservation")
public class ReservationController {

	private ReservationService reservationService;

	/**
	 * Constructor
	 *
	 * @param reservationService
	 */
	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@GetMapping
	public List<Reservation> getAllReservations() {

		return this.reservationService.getAllReservations();
	}

	@PostMapping
	public Reservation postReservation(@RequestBody @Valid ReservationDto reservationDto) {

		return this.reservationService.postReservation(reservationDto);
	}

	/**
	 * Réceptionne une requete sur l'url back_url/reservation/?email="..." et
	 * renvoie la liste des réservations en cours de véhicule de société du collègue
	 * possésant cet email
	 * 
	 * @param email
	 * @return Reservation
	 */
	@GetMapping("current")
	public List<Reservation> getReservationByEmailEnCours(@RequestParam String email) {

		return this.reservationService.getReservationByEmailEnCours(email);
	}

	/**
	 * Réceptionne une requete sur l'url back_url/reservation/?email="..." et
	 * renvoie la liste des réservations passées, de véhicule de société du collègue
	 * possésant cet email
	 * 
	 * @param email
	 * @return Reservation
	 */
	@GetMapping("histo")
	public List<Reservation> getReservationByEmailHisto(@RequestParam String email) {

		return this.reservationService.getReservationByEmailHisto(email);
	}

	@ExceptionHandler(CollegueNonTrouveException.class)
	public ResponseEntity<String> onCollegueNonTrouveException(CollegueNonTrouveException e) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

}
