package dev.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.Reservation;
import dev.entites.dto.ReservationDto;
import dev.exceptions.CollegueNonTrouveException;
import dev.exceptions.NonChauffeurException;
import dev.exceptions.ReservationHoraireIncompatibleException;
import dev.exceptions.VehiculeNonTrouveException;
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
	 * Catch l'exception throw par le service si aucun collègue n'a été trouvé et
	 * renvoie une ResponseEntity avec le statut 404 et le message de l'exception
	 * 
	 * @param e
	 * @return ResponseEntity<String>
	 */
	@ExceptionHandler(CollegueNonTrouveException.class)
	public ResponseEntity<String> onCollegueNonTrouveException(CollegueNonTrouveException e) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	/**
	 * Catch l'exception throw par le service si aucun véchicule n'a été trouvé et
	 * renvoie une ResponseEntity avec le statut 404 et le message de l'exception
	 * 
	 * @param e
	 * @return ResponseEntity<String>
	 */
	@ExceptionHandler(VehiculeNonTrouveException.class)
	public ResponseEntity<String> onVehiculeNonTrouveException(VehiculeNonTrouveException e) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	/**
	 * Catch l'exception throw par le service si les horaires de la réservation à
	 * crée sont incompatibles avec les reservations présentes en base de données et
	 * renvoie une ResponseEntity avec le statut 404 et le message de l'exception
	 * 
	 * @param e
	 * @return ResponseEntity<String>
	 */
	@ExceptionHandler(ReservationHoraireIncompatibleException.class)
	public ResponseEntity<String> onReservationHoraireIncompatibleException(ReservationHoraireIncompatibleException e) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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
	 * Catch l'exception throw par le service si un collegue n'a pas le statut
	 * chauffeur
	 * 
	 * @param e
	 * @return ResponseEntity<String>
	 */
	@ExceptionHandler(NonChauffeurException.class)
	public ResponseEntity<String> onNonChauffeurException(NonChauffeurException e) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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
}
