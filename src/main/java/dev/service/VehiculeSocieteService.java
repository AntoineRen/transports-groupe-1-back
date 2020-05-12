package dev.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import dev.entites.Reservation;
import dev.entites.VehiculeSociete;
import dev.entites.dto.DisponibiliteDto;
import dev.entites.dto.PeriodeDto;
import dev.entites.dto.VehiculeSocieteDto;
import dev.entites.utiles.Categorie;
import dev.entites.utiles.StatutVehiculeSociete;
import dev.repository.ReservationRepository;
import dev.repository.VehiculeSocieteRepository;

@Service
public class VehiculeSocieteService {

	private VehiculeSocieteRepository vehiculeRepository;
	private ReservationRepository reservationRepository;

	/**
	 * Constructor
	 *
	 * @param vehiculeRepository
	 */
	public VehiculeSocieteService(VehiculeSocieteRepository vehiculeRepository,
			ReservationRepository reservationRepository) {
		this.vehiculeRepository = vehiculeRepository;
		this.reservationRepository = reservationRepository;
	}

	/**
	 * Renvoie tous les véhicules présents en base de données
	 * 
	 * @return List<VehiculeSociete>
	 */
	public List<VehiculeSocieteDto> getAllVehicules() {

		return this.vehiculeRepository.findAll().stream().map(vehicule -> new VehiculeSocieteDto(vehicule))
				.collect(Collectors.toList());
	}

	@Transactional
	public VehiculeSociete postVehicule(@Valid VehiculeSocieteDto vehiculeDto) {

		// TODO verification doublons

		VehiculeSociete vehicule = new VehiculeSociete(vehiculeDto.getImmatriculation(), vehiculeDto.getMarque(),
				vehiculeDto.getModele(), Categorie.valueOf(vehiculeDto.getCategorie()), vehiculeDto.getNbPlace(),
				StatutVehiculeSociete.EN_SERVICE, null);

		this.vehiculeRepository.save(vehicule);

		return vehicule;
	}

	/**
	 * A partir d'une période donnée vérifie la disponibilitée de tous les véhicules
	 * et la renvoie sous forme de liste
	 * 
	 * @param periodeDto
	 * @return List<DisponibiliteDto>
	 */
	public List<DisponibiliteDto> disponibilitesVehicules(@Valid PeriodeDto periode) {

		List<VehiculeSociete> vehicules = this.vehiculeRepository.findAll();
		List<DisponibiliteDto> disponibilites = new ArrayList<>();

		for (VehiculeSociete vehicule : vehicules) {

			// si véhicule Hors service ou en reparation
			if (vehicule.getStatut() == StatutVehiculeSociete.EN_REPARATION
					|| vehicule.getStatut() == StatutVehiculeSociete.HORS_SERVICE) {

				disponibilites.add(new DisponibiliteDto(vehicule.getId(), false));

			} else {
				List<Reservation> reservations = this.reservationRepository.findAllByVehicule(vehicule);

				disponibilites.add(new DisponibiliteDto(vehicule.getId(),
						this.vechiculeDispoInReservations(reservations, periode)));
			}

		}

		return disponibilites;
	}

	/**
	 * Vérifie qu'une période ne rentre pas en conflit avec une reservation
	 * existante
	 * 
	 * @param reservations
	 * @param periode
	 * @return Boolean si la période ne rentre pas en conflit avec les reservations
	 */
	private Boolean vechiculeDispoInReservations(List<Reservation> reservations, PeriodeDto periode) {

		for (Reservation reservation : reservations) {

			LocalDateTime reservationDepart = reservation.getDateDepart();
			LocalDateTime reservationArrivee = reservation.getDateArrivee();

			if (reservationDepart.isEqual(periode.getDateDepart()) // reservation depart == periode depart
					|| reservationDepart.isEqual(periode.getDateRetour()) // reservation depart == periode retour
					|| reservationArrivee.isEqual(periode.getDateDepart()) // reservation retour == periode depart
					|| reservationArrivee.isEqual(periode.getDateRetour()) // reservation retour == periode retour
					|| (reservationDepart.isAfter(periode.getDateDepart()) // periode depart < reservation depart <
																			// periode retour
							&& reservationDepart.isBefore(periode.getDateRetour()))
					|| (reservationArrivee.isAfter(periode.getDateDepart()) // periode depart < reservation retour <
																			// periode retour
							&& reservationArrivee.isBefore(periode.getDateRetour()))) {
				return false;
			}
		}

		return true;
	}

}
