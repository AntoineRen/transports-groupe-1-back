package dev.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
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
import dev.exceptions.ApplicationException;
import dev.exceptions.ReservationNotSavedException;
import dev.exceptions.VehiculeNonTrouveException;
import dev.exceptions.VehiculeNotSavedException;
import dev.repository.ReservationRepository;
import dev.repository.VehiculeSocieteRepository;

@Service
public class VehiculeSocieteService {

	private VehiculeSocieteRepository vehiculeRepository;
	private ReservationRepository reservationRepository;
	private ReservationService reservationService;
	private EnvoiMailService envoiMailService;

	/**
	 * Constructor
	 *
	 * @param vehiculeRepository
	 */
	public VehiculeSocieteService(VehiculeSocieteRepository vehiculeRepository,
			ReservationRepository reservationRepository, EnvoiMailService envoiMailService) {
		this.vehiculeRepository = vehiculeRepository;
		this.reservationRepository = reservationRepository;
		this.reservationService = reservationService;
		this.envoiMailService = envoiMailService;
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
				vehiculeDto.getModele(), Categorie.getName(vehiculeDto.getCategorie()), vehiculeDto.getNbPlace(),
				StatutVehiculeSociete.EN_SERVICE, vehiculeDto.getPhotoUrl());

		this.vehiculeRepository.save(vehicule);

		return vehicule;
	}

	public VehiculeSociete postVehiculeSociete(VehiculeSociete vehicule) {
		 vehicule= vehiculeRepository.save(vehicule); 
		if(vehicule!=null) {
			return vehicule;
		}else {
			throw new VehiculeNotSavedException("Impossible de sauver le vehicule : "+vehicule.getId()); 
		}


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

	/**
	 * @param immatriculation de la voiture
	 * @return un vehicule société grace a son immatriculation
	 */
	public VehiculeSociete getVehiculeByImmatriculation(String immatriculation) {
		Optional<VehiculeSociete> vehiculeOptional = vehiculeRepository.findOneByImmatriculation(immatriculation);
		VehiculeSociete vehicule = new VehiculeSociete();
		if (vehiculeOptional.isPresent()) {
			vehicule = vehiculeOptional.get();
		} else {
			throw new VehiculeNonTrouveException("Aucun véhicule trouvé avec immatriculation : " + immatriculation);
		}
		return vehicule;
	}

	/**
	 * Permet de changer le statut du vehicule en BDD, dans le cas des statuts
	 * "HORS_SERVICE" et "EN_REPARATION" modifie le statut des réservations en cours
	 * pour ce vehicule pour le mettre en annulé, envoi également un mail aux
	 * passagers
	 * 
	 * @param statut du vehicule
	 * @param immat  du vehicule
	 * @return
	 * @throws Exception
	 */
	public VehiculeSociete putStatutVehiculeService(String statut, String immat) {
		VehiculeSociete vehicule = this.getVehiculeByImmatriculation(immat);
		List<Reservation> listReservation = this.reservationService.getReservationEncoursByVehicule(immat);

		switch (statut) {

		case "HorsService":
			vehicule.setStatut(StatutVehiculeSociete.HORS_SERVICE);

			// change le statut de toutes les reservation en cours
			for (Reservation courante : listReservation) {
				courante.setVehicule(vehicule);
				this.reservationService.putReservation(courante);

				// envoi mail a tout les collab concerné pour une réservation , chauffeur et
				// responsable
				envoiMailService.envoyerMailAnnulationReservationVehiculeSociete(courante);
			}
			break;

		case "enReparation":
			vehicule.setStatut(StatutVehiculeSociete.EN_REPARATION);

			// change le statut de toutes les reservation en cours
			for (Reservation courante : listReservation) {
				courante.setVehicule(vehicule);
				this.reservationService.putReservation(courante);

				// envoi mail a tout les collab concerné pour une réservation , chauffeur et
				// responsable
				envoiMailService.envoyerMailAnnulationReservationVehiculeSociete(courante);
			}
			break;

		case "enService":
			//mise en service du vehicule, sauvegarde en base de donée
			vehicule.setStatut(StatutVehiculeSociete.EN_SERVICE);
			vehicule = postVehiculeSociete(vehicule);

			break;

		default:
			throw new ApplicationException("Le statut" + statut + "n'éxiste pas pour une reservation");
		}

		return vehicule;
	}

}
