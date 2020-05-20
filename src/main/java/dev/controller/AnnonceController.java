package dev.controller;

import java.util.ArrayList;
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

import dev.entites.Annonce;
import dev.entites.dto.AnnonceDto;
import dev.entites.dto.StatistiquesDto;
import dev.exceptions.ApplicationException;
import dev.service.AnnonceService;

@RestController
@RequestMapping("annonce")
public class AnnonceController {

	private AnnonceService annonceService;

	public AnnonceController(AnnonceService annonceService) {
		this.annonceService = annonceService;
	}


	

	@PostMapping
	public Annonce postAnnonce(@RequestBody @Valid AnnonceDto annonceDto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return this.annonceService.postAnnonce(email, annonceDto);
	}

	@PutMapping("annuler")
	public Annonce annulerAnnonce(@RequestBody @Valid Long id) {
		return this.annonceService.annulerAnnonce(id);
	}

	@PutMapping("self/reservation/annulation")
	public Annonce annulerReservation(@RequestBody @Valid Long id) {
		String emailPassager = SecurityContextHolder.getContext().getAuthentication().getName();
		return this.annonceService.annulerReservation(id, emailPassager);

	}

	@PutMapping("reservationCovoit")
	public Annonce putAnnonce(@RequestBody Long id) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return this.annonceService.putReservation(id, email);

	}

	/**
	 * Catch l'exception throw par le service si une annumalie s'est produite dans
	 * le service annonce et renvoie une ResponseEntity avec le statut 404 et le
	 * message de lexception
	 * 
	 * @param e
	 * @return ResponseEntity<String>
	 */
	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<String> onAnnonceException(ApplicationException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}


	@GetMapping("self/reservations")
	public List<Annonce> getReservationsByPassager(@RequestParam("encours") Boolean isEnCours) {
		List<Annonce> listReservations = new ArrayList<>();
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		if (isEnCours) {
			listReservations = annonceService.getReservationsEncoursByPassager(email);
		} else {
			listReservations = annonceService.getReservationsHistoriqueByPassager(email);

		}

		return listReservations;
	}

	@GetMapping("self/annonces")
	public List<Annonce> getAnnoncesByResponsable(@RequestParam("encours") Boolean isEnCours) {
		List<Annonce> listAnnonces = new ArrayList<>();
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		if (isEnCours) {
			listAnnonces = annonceService.getAnnoncesEncoursByResponsable(email);
		} else {
			listAnnonces = annonceService.getAnnoncesHistoriqueByResponsable(email);

		}

		return listAnnonces;
	}

	@GetMapping("annonces")
	public List<Annonce> getAnnoncesEnCours() {
		List<Annonce> listAnnonces = new ArrayList<>();
		listAnnonces = annonceService.getAllAnnoncesEnCours();

		return listAnnonces;
	}

	/**
	 * Réceptionne une requete sur l'url back_url/annonce/self/statistiques les
	 * statistiques des covoiturages du collegue connecté
	 * 
	 * @return StatistiquesDto
	 */
	@GetMapping("self/statistiques")
	public StatistiquesDto getStatistiques() {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return this.annonceService.getStatistiques(email);
	}
}
