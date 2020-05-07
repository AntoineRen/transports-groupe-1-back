package dev.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import dev.entites.Collegue;
import dev.entites.Itineraire;
import dev.entites.Reservation;
import dev.entites.VehiculeSociete;
import dev.entites.dto.ReservationDto;
import dev.entites.utiles.StatutReservation;
import dev.exceptions.CollegueNonTrouveException;
import dev.repository.CollegueRepository;
import dev.repository.ReservationRepository;
import dev.repository.VehiculeSocieteRepository;

@Service
public class ReservationService {

	private ReservationRepository reservationRepository;
	private CollegueRepository collegueRepository; // TODO voir si appeller un service au lieu du repo
	private VehiculeSocieteRepository vehiculeRepository; // TODO voir si appeller un service au lieu du repo

	/**
	 * Constructor
	 *
	 * @param reservationRepository
	 */
	public ReservationService(ReservationRepository reservationRepository, CollegueRepository collegueRepository,
			VehiculeSocieteRepository vehiculeRepository) {
		this.reservationRepository = reservationRepository;
		this.collegueRepository = collegueRepository;
		this.vehiculeRepository = vehiculeRepository;
	}

	public List<Reservation> getAllReservations() {

		return this.reservationRepository.findAll();
	}

	@Transactional
	public Reservation postReservation(@Valid ReservationDto reservationDto) {

		Reservation reservation = null;

		Itineraire itineraire = new Itineraire(reservationDto.getDateDepart(), reservationDto.getDateArrivee(),
				reservationDto.getLieuDepart(), reservationDto.getLieuDestination(), reservationDto.getDureeTrajet(),
				reservationDto.getDistance());

		// find collegue en tant que responsable
		Optional<Collegue> responsable = this.collegueRepository.findById(reservationDto.getResponsable_id());
		// find vehicule
		Optional<VehiculeSociete> vehicule = this.vehiculeRepository.findById(reservationDto.getVehicule_id());

		// si responsable + vehicule
		if (responsable.isPresent() && vehicule.isPresent()) {

			reservation = new Reservation(itineraire, responsable.get(), null, StatutReservation.STATUT_EN_COURS,
					vehicule.get());

		} else {
			if (!responsable.isPresent()) {
				throw new RuntimeException(); // TODO creer exception responsable non trouvé
			} else {
				throw new RuntimeException(); // TODO creer exception vehicule non trouvé
			}
		}

		this.reservationRepository.save(reservation);
		return reservation;
	}

	/**
	 * A partir d'un email verifie qu'un collegue existe avec cet email et renvoie
	 * la liste de ses réservations en cours, de véhicules de sociétés
	 * 
	 * @param email
	 * @return List<Reservation>
	 */
	public List<Reservation> getReservationByEmailEnCours(String email) {

		Optional<Collegue> responsable = this.collegueRepository.findOneByEmail(email);

		if (responsable.isPresent()) {
			// recupere toutes les reservations du responsable, les filtrent pour garder les
			// réservations actuelles
			return this.reservationRepository.findAllByResponsable(responsable.get()).stream()
					.filter(resa -> resa.getItineraire().getDateArrivee().isAfter(LocalDateTime.now())
							|| resa.getItineraire().getDateArrivee().isEqual(LocalDateTime.now()))
					.sorted((a, b) -> a.getItineraire().getDateDepart().compareTo((b.getItineraire().getDateDepart())))
					.collect(Collectors.toList());
		} else {
			throw new CollegueNonTrouveException("Aucun collègue trouvé avec cet email : " + email);
		}

	}

	/**
	 * A partir d'un email verifie qu'un collegue existe avec cet email et renvoie
	 * la liste de ses réservations passées, de véhicules de sociétés
	 * 
	 * @param email
	 * @return List<Reservation>
	 */
	public List<Reservation> getReservationByEmailHisto(String email) {

		Optional<Collegue> responsable = this.collegueRepository.findOneByEmail(email);

		if (responsable.isPresent()) {
			// recupere toutes les reservations du responsable, les filtrent pour garder les
			// réservations passées
			return this.reservationRepository.findAllByResponsable(responsable.get()).stream()
					.filter(resa -> resa.getItineraire().getDateArrivee().isBefore(LocalDateTime.now()))
					.sorted((a, b) -> a.getItineraire().getDateDepart().compareTo((b.getItineraire().getDateDepart())))
					.collect(Collectors.toList());
		} else {
			throw new CollegueNonTrouveException("Aucun collègue trouvé avec cet email : " + email);
		}
	}

}
