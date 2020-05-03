package dev.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import dev.entites.Collegue;
import dev.entites.Itineraire;
import dev.entites.Reservation;
import dev.entites.Vehicule;
import dev.entites.dto.ReservationDto;
import dev.entites.utiles.StatutReservation;
import dev.repository.CollegueRepository;
import dev.repository.ReservationRepository;

@Service
public class ReservationService {

	private ReservationRepository reservationRepository;
	private CollegueRepository collegueRepository; // TODO voir si appeller un service au lieu du repo
	private VehiculeRepository vehiculeRepository; // TODO voir si appeller un service au lieu du repo

	/**
	 * Constructor
	 *
	 * @param reservationRepository
	 */
	public ReservationService(ReservationRepository reservationRepository, CollegueRepository collegueRepository,
			VehiculeRepository vehiculeRepository) {
		this.reservationRepository = reservationRepository;
		this.collegueRepository = collegueRepository;
		this.vehiculeRepository = vehiculeRepository;
	}

	public List<Reservation> getAllReservations() {

		return this.reservationRepository.findAll();
	}

	@Transactional
	@Transient
	public Reservation postReservation(@Valid ReservationDto reservationDto) {

		Boolean avecChauffeur = reservationDto.getChauffeur_id() != null;

		Reservation reservation = null;

		Itineraire itineraire = new Itineraire(reservationDto.getDateDepart(), reservationDto.getDateArrivee(),
				reservationDto.getLieuDepart(), reservationDto.getLieuDestination(), reservationDto.getDureeTrajet(),
				reservationDto.getDistance());

		// find collegue en tant que responsable
		Optional<Collegue> responsable = this.collegueRepository.findById(reservationDto.getResponsable_id());
		// find vehicule
		Optional<Vehicule> vehicule = this.vehiculeRepository.findById(reservationDto.getVehicule_id());

		Optional<Collegue> chauffeur = null;

		if (avecChauffeur) {
			// find collegue en tant que chauffeur //TODO verifier qu'il a le role de
			// chauffeur
			chauffeur = this.collegueRepository.findById(reservationDto.getChauffeur_id());
		}

		// si responsable + chauffeur + vehicule
		if (responsable.isPresent() && avecChauffeur && chauffeur.isPresent() && vehicule.isPresent()) {

			reservation = new Reservation(itineraire, responsable.get(), chauffeur.get(),
					StatutReservation.STATUT_EN_COURS, vehicule.get());

			// si pas de chauffeur
		} else if (responsable.isPresent() && !avecChauffeur && vehicule.isPresent()) {

			reservation = new Reservation(itineraire, responsable.get(), null, StatutReservation.STATUT_EN_COURS,
					vehicule.get());

		} else {
			if (responsable.isEmpty()) {
				throw new RuntimeException(); // TODO creer exception responsable non trouvé
			} else if (avecChauffeur && chauffeur.isEmpty()) {
				throw new RuntimeException(); // TODO creer exception chauffeur non trouvé
			} else {
				throw new RuntimeException(); // TODO creer exception vehicule non trouvé
			}
		}

		// TODO voir comment update les différentes liste de réservation des collègues +
		// celle du véhicule
		this.reservationRepository.save(reservation);
		return reservation;
	}

}
