package dev.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import dev.entites.Collegue;
import dev.entites.Reservation;
import dev.entites.VehiculeSociete;
import dev.entites.dto.ReservationDto;
import dev.entites.dto.StatistiquesDto;
import dev.entites.utiles.StatutDemandeChauffeur;
import dev.exceptions.CollegueNonTrouveException;
import dev.exceptions.NonChauffeurException;
import dev.exceptions.ReservationHoraireIncompatibleException;
import dev.exceptions.ReservationNonTrouveException;
import dev.exceptions.ReservationNotSavedException;
import dev.exceptions.VehiculeNonTrouveException;
import dev.repository.CollegueRepository;
import dev.repository.ReservationRepository;
import dev.repository.VehiculeSocieteRepository;

@Service
public class ReservationService {

	private ReservationRepository reservationRepository;
	private CollegueRepository collegueRepository;
	private VehiculeSocieteRepository vehiculeRepository;
	private VehiculeSocieteService vehiculeSocieteService;

	/**
	 * Constructor
	 *
	 * @param reservationRepository
	 */
	public ReservationService(ReservationRepository reservationRepository, CollegueRepository collegueRepository,
			VehiculeSocieteRepository vehiculeRepository, VehiculeSocieteService vehiculeSocieteService) {
		this.reservationRepository = reservationRepository;
		this.collegueRepository = collegueRepository;
		this.vehiculeRepository = vehiculeRepository;
		this.vehiculeSocieteService = vehiculeSocieteService;
	}

	public List<Reservation> getAllReservations() {

		return this.reservationRepository.findAll();
	}

	/**
	 * A partir d'un email et d'un object ReservationDto, vérifie que les
	 * informations nécessaire a la création d'une reservation sont correct et
	 * sauvegarde la reservation en base de données
	 * 
	 * @param email
	 * @param reservationDto
	 * @return Reservation
	 */
	@Transactional
	public Reservation postReservation(String email, @Valid ReservationDto reservationDto) {

		Reservation reservation = null;

		// find collegue en tant que responsable
		Optional<Collegue> responsable = this.collegueRepository.findOneByEmail(email);
		// find vehicule
		Optional<VehiculeSociete> vehicule = this.vehiculeRepository.findById(reservationDto.getVehiculeId());

		// si responsable + vehicule
		if (responsable.isPresent() && vehicule.isPresent()) {

			// vérification heure reservation corrects
			List<Reservation> reservations = this.reservationRepository.findAllByVehicule(vehicule.get());

			if (this.vechiculeDispoInReservations(reservations, reservationDto.getDateDepart(),
					reservationDto.getDateArrivee())) {

				// si demande de chauffeur
				if (reservationDto.getAvecChauffeur()) {
					reservation = new Reservation(reservationDto.getDateDepart(), reservationDto.getDateArrivee(),
							responsable.get(), null, vehicule.get(), StatutDemandeChauffeur.EN_ATTENTE);
				} else {
					reservation = new Reservation(reservationDto.getDateDepart(), reservationDto.getDateArrivee(),
							responsable.get(), null, vehicule.get(), StatutDemandeChauffeur.SANS_CHAUFFEUR);
				}

			} else {
				throw new ReservationHoraireIncompatibleException(
						"Impossible de créer la réservation du fait d'horraires incompatibles.");
			}

		} else {
			if (!responsable.isPresent()) {
				throw new CollegueNonTrouveException("Aucun collègue trouvé avec cet email : " + email);
			} else {
				throw new VehiculeNonTrouveException(
						"Aucun véhicule trouvé avec cet id : " + reservationDto.getVehiculeId());
			}
		}

		this.reservationRepository.save(reservation);
		return reservation;
	}

	/**
	 * Vérifie qu'une période ne rentre pas en conflit avec une reservation
	 * existante
	 * 
	 * @param reservations
	 * @param dateDepart
	 * @param dateRetour
	 * @return Boolean si la période ne rentre pas en conflit avec les reservations
	 */
	private Boolean vechiculeDispoInReservations(List<Reservation> reservations, LocalDateTime dateDepart,
			LocalDateTime dateRetour) {

		for (Reservation reservation : reservations) {

			LocalDateTime reservationDepart = reservation.getDateDepart();
			LocalDateTime reservationArrivee = reservation.getDateArrivee();

			if (reservationDepart.isEqual(dateDepart) // reservation depart == periode depart
					|| reservationDepart.isEqual(dateRetour) // reservation depart == periode retour
					|| reservationArrivee.isEqual(dateDepart) // reservation retour == periode depart
					|| reservationArrivee.isEqual(dateRetour) // reservation retour == periode retour
					|| (reservationDepart.isAfter(dateDepart) // periode depart < reservation depart <
																// periode retour
							&& reservationDepart.isBefore(dateRetour))
					|| (reservationArrivee.isAfter(dateDepart) // periode depart < reservation retour <
																// periode retour
							&& reservationArrivee.isBefore(dateRetour))) {
				return false;
			}
		}

		return true;
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
					.filter(resa -> resa.getDateArrivee().isAfter(LocalDateTime.now())
							|| resa.getDateArrivee().isEqual(LocalDateTime.now()))
					.sorted((a, b) -> a.getDateDepart().compareTo((b.getDateDepart()))).collect(Collectors.toList());
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
					.filter(resa -> resa.getDateArrivee().isBefore(LocalDateTime.now()))
					.sorted((a, b) -> a.getDateDepart().compareTo((b.getDateDepart()))).collect(Collectors.toList());
		} else {
			throw new CollegueNonTrouveException("Aucun collègue trouvé avec cet email : " + email);
		}
	}

	/**
	 * A partir d'un email verifie qu'un chauffeur existe avec cet email et renvoie
	 * la liste des reservations dont il est le chauffeur
	 * 
	 * @param email
	 * @return List<Reservation>
	 */
	public List<Reservation> getReservationsByChauffeur(String email) {

		Optional<Collegue> chauffeur = this.collegueRepository.findOneByEmail(email);
		List<Reservation> reservations = null;

		if (chauffeur.isPresent() && chauffeur.get().isChauffeur()) {

			reservations = this.reservationRepository.findAllByChauffeur(chauffeur.get());

			return reservations;
		} else {
			if (!chauffeur.isPresent()) {
				throw new CollegueNonTrouveException("Aucun collègue trouvé avec cet email : " + email);
			} else {
				throw new NonChauffeurException("Vous n'avez pas le role de chauffeur.");
			}
		}
	}

	/**
	 * Renvoie la liste des réservations en attentes
	 * 
	 * @return List<Reservation>
	 */
	public List<Reservation> getReservationsEnAttentes() {

		return this.reservationRepository.findAllByStatutDemandeChauffeur(StatutDemandeChauffeur.EN_ATTENTE);
	}

	/**
	 * Passe le statut d'une réservation à avec_chauffeur et lui ajoute un chauffeur
	 * 
	 * @param email
	 * @param id
	 * @return Reservation
	 */
	@Transactional
	public Reservation AcceptReservation(String email, String id) {

		Optional<Collegue> chauffeur = this.collegueRepository.findOneByEmail(email);
		Optional<Reservation> reservation = null;

		if (chauffeur.isPresent() && chauffeur.get().isChauffeur()) {

			reservation = this.reservationRepository.findById(Long.parseLong(id));

			if (reservation.isPresent()) {

				reservation.get().setChauffeur(chauffeur.get());
				reservation.get().setStatutDemandeChauffeur(StatutDemandeChauffeur.AVEC_CHAUFFEUR);

				this.reservationRepository.save(reservation.get());

				return reservation.get();
			} else {
				throw new ReservationNonTrouveException("Aucune réservation trouvé avec cet id : " + id);
			}

		} else {
			if (!chauffeur.isPresent()) {
				throw new CollegueNonTrouveException("Aucun collègue trouvé avec cet email : " + email);
			} else {
				throw new NonChauffeurException("Vous n'avez pas le role de chauffeur.");
			}
		}
	}

	/**
	 * Vérifie que le collègue connecté est un chauffeur et lui renvoie sa liste de
	 * réservations de la période indiquée
	 * 
	 * @param email
	 * @param debut
	 * @param fin
	 * @return List<Reservation>
	 */
	public List<Reservation> getReservationsByPeriode(String email, String debut, String fin) {

		Optional<Collegue> chauffeur = this.collegueRepository.findOneByEmail(email);
		List<Reservation> reservations = null;
		LocalDateTime debutPeriode = LocalDateTime.parse(debut + "T00:00:00");
		LocalDateTime finPeriode = LocalDateTime.parse(fin + "T23:59:59");

		if (chauffeur.isPresent() && chauffeur.get().isChauffeur()) {

			reservations = this.reservationRepository.findAllInPeriodeByChauffeur(debutPeriode, finPeriode,
					chauffeur.get());

			return reservations;

		} else {
			if (!chauffeur.isPresent()) {
				throw new CollegueNonTrouveException("Aucun collègue trouvé avec cet email : " + email);
			} else {
				throw new NonChauffeurException("Vous n'avez pas le role de chauffeur.");
			}
		}
	}

	/**
	 * @param immatriculation
	 * @return une liste de réservation encours pour un vehicule retrouver avec son
	 *         immatriculation
	 */
	public List<Reservation> getReservationEncoursByVehicule(String immatriculation) {
		VehiculeSociete vehiculeSociete = this.vehiculeSocieteService.getVehiculeByImmatriculation(immatriculation);
		return this.reservationRepository.findAllByVehiculeWithDateDepartAfter(vehiculeSociete);
	}

	/**
	 * @param immatriculation
	 * @return une liste de réservation historique pour un vehicule retrouver avec
	 *         son immatriculation
	 */
	public List<Reservation> getAnnoncesHistoriqueByVehicule(String immatriculation) {
		VehiculeSociete vehiculeSociete = this.vehiculeSocieteService.getVehiculeByImmatriculation(immatriculation);
		return this.reservationRepository.findAllByVehiculeWithdateArriveeBerore(vehiculeSociete);
	}

	/**
	 * Calcul le nombre de covoiturages en cours, terminé et totale d'un collegue
	 * par rapport a son email
	 * 
	 * @param email
	 * @return StatistiquesDto
	 */
	public StatistiquesDto getStatistiques(String email) {

		Optional<Collegue> collegue = this.collegueRepository.findOneByEmail(email);

		if (collegue.isPresent()) {

			int total = this.reservationRepository.countByResponsable(collegue.get());

			int termine = this.reservationRepository.countByResponsableTermine(collegue.get());

			return new StatistiquesDto(total - termine, termine, total);

		} else {

			throw new CollegueNonTrouveException("Aucun collègue trouvé avec cet email : " + email);
		}
	}

	/**
	 * Permet d'enregister une réservation, renvoi une exception si echec du save
	 * 
	 * @param reservation
	 * @return
	 */
	@Transactional
	public Reservation putReservation(Reservation reservation) {
		Reservation res = reservationRepository.save(reservation);
		if (res != null) {
			return res;
		} else {
			throw new ReservationNotSavedException(
					"Impossible de sauvegarder cette reservation : " + reservation.getId());
		}

	}

}
