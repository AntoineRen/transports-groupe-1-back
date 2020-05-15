package dev.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.Reservation;
import dev.entites.dto.ReservationDto;
import dev.exceptions.ApplicationException;
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

	/**
	 * Réceptionne une requète post sur l'url back_url/reservation contenant les
	 * informations nécessaires a la création d'une reservation et la renvoie si la
	 * sauvegarde en base de données à été un succes
	 * 
	 * @param reservationDto
	 * @return Reservation
	 */
	@PostMapping
	public Reservation postReservation(@RequestBody @Valid ReservationDto reservationDto) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return this.reservationService.postReservation(email, reservationDto);
	}

	/**
	 * Réceptionne une requete get sur l'url back_url/reservation/current et renvoie
	 * la liste des réservations en cours du collègue connecté
	 * 
	 * @return List<Reservation>
	 */
	@GetMapping("current")
	public List<Reservation> getReservationByEmailEnCours() {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return this.reservationService.getReservationByEmailEnCours(email);
	}

	/**
	 * Réceptionne une requete get sur l'url back_url/reservation/histo" et renvoie
	 * la liste des réservations passées du collègue connecté
	 * 
	 * @return List<Reservation>
	 */
	@GetMapping("histo")
	public List<Reservation> getReservationByEmailHisto() {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return this.reservationService.getReservationByEmailHisto(email);
	}

	/**
	 * Réceptionne une requete get sur l'url back_url/reservation/chauffeur" et
	 * renvoie la liste des réservations dont le collègue connecté est le chauffeur
	 * 
	 * @return List<Reservation>
	 */
	@GetMapping("chauffeur")
	public List<Reservation> getReservationsByChauffeur() {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return this.reservationService.getReservationsByChauffeur(email);
	}

	/**
	 * Réceptionne une requete get sur l'url back_url/reservation/enattente" et
	 * renvoie la liste des réservations en attente d'un chauffeur
	 * 
	 * @return List<Reservation>
	 */
	@GetMapping("enattente")
	public List<Reservation> getReservationsEnAttentes() {

		return this.reservationService.getReservationsEnAttentes();
	}

	/**
	 * Réceptionne une requete get sur l'url back_url/reservation/?id=" modifie une
	 * reservation en attente de chauffeur avec le statut avec_chauffeur et lui
	 * ajoute le chauffeur courant
	 * 
	 * @param id
	 * @return Reservation
	 */
	@PutMapping(params = "id")
	public Reservation AcceptReservation(@RequestParam String id) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return this.reservationService.AcceptReservation(email, id);
	}

	/**
	 * Catch les exceptions throw par le service et renvoie une responseEntity avec
	 * le statut not found et le message d'erreur
	 * 
	 * @param e
	 * @return ResponseEntity<String>
	 */
	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<String> onNonChauffeurException(ApplicationException e) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
}
