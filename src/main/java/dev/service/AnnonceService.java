package dev.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;


import dev.entites.Annonce;
import dev.entites.Collegue;
import dev.entites.Itineraire;
import dev.entites.dto.AnnonceDto;
import dev.entites.utiles.StatutAnnonce;
import dev.exceptions.AnnonceNonTrouveException;
import dev.exceptions.AnnonceNotDeletedException;
import dev.exceptions.CollegueNonTrouveException;
import dev.exceptions.ReservationNotDeletedException;
import dev.repository.AnnonceRepository;
import dev.repository.CollegueRepository;

/**
 * @author jules
 *
 */
@Service
public class AnnonceService {

	private AnnonceRepository annonceRepository;
	private CollegueRepository collegueRepository;
	private EnvoiMailService envoiMailService;

	/**
	 * Constructor
	 *
	 * @param annonceRepository
	 * @param collegueRepository
	 */
	public AnnonceService(AnnonceRepository annonceRepository, CollegueRepository collegueRepository,
			EnvoiMailService envoiMailService) {
		this.annonceRepository = annonceRepository;
		this.collegueRepository = collegueRepository;
		this.envoiMailService = envoiMailService;
	}

	/**
	 * @param email
	 * @return Liste d'annonces d'un responsable grace à son mail si l'email ne
	 *         correspond a aucun collegue renvois une exception
	 */
	public List<Annonce> getAnnoncesByResponcable(String email) {
		Optional<Collegue> responsable = this.collegueRepository.findOneByEmail(email);
		if (responsable.isPresent()) {
			return this.annonceRepository.findAllByResponsable(responsable.get());
		} else {
			// TODO creer exception adaptée
			throw new CollegueNonTrouveException("Aucun responcable de covoiturage trouvé avec cet email : " + email);
		}

	}

	/**
	 * @param email
	 * @return Liste d'annonces d'un passager grace à son email si l'email ne
	 *         correspond a aucun collegue renvois une exception
	 */
	public List<Annonce> getAnnonceByPassager(String email) {
		Optional<Collegue> passager = this.collegueRepository.findOneByEmail(email);
		if (passager.isPresent()) {
			return this.annonceRepository.findAllBylistPassagers(passager.get());
		} else {
			// TODO creer exception adaptée
			throw new CollegueNonTrouveException("Aucun passager trouvé avec cet email : " + email);
		}
	}

	/**
	 * @param listAnnonces
	 * @return les Annonces de la listAnnonces au statut "EN COURS" (antérieur a la
	 *         date et au temps actuel
	 */
	public List<Annonce> getAnnonceEnCours(List<Annonce> listAnnonces) {
		List<Annonce> annonceEncours = listAnnonces.stream()
				.filter(annonce -> annonce.getItineraire().getDateDepart().isAfter(LocalDateTime.now().plusMinutes(5)))
				.sorted((a, b) -> a.getItineraire().getDateDepart().compareTo((b.getItineraire().getDateDepart())))
				.collect(Collectors.toList());
		return annonceEncours;
	}

	/**
	 * @param listAnnonces
	 * @returnles Annonces de la listAnnonces posterieur a la date et au temps
	 *            actuel, utilisé en historique
	 */
	public List<Annonce> getHistoriqueAnnonce(List<Annonce> listAnnonces) {
		List<Annonce> annonceEncours = listAnnonces.stream()
				.filter(annonce -> annonce.getItineraire().getDateDepart().isBefore(LocalDateTime.now().plusMinutes(5)))
				.sorted((a, b) -> a.getItineraire().getDateDepart().compareTo((b.getItineraire().getDateDepart())))
				.collect(Collectors.toList());
		return annonceEncours;
	}

	/**
	 * TODO soit disparaitre
	 * 
	 * @param email
	 * @return Liste d'annonces en tant que passager ou responcable grace à son mail
	 *         si l'email ne correspond a aucun collegue renvois une exception
	 */
	public List<Annonce> getAllAnnoncesByCollegue(String email) {
		List<Annonce> allAnnonceResponcable = this.getAnnoncesByResponcable(email);
		List<Annonce> allAnnoncePassager = this.getAnnonceByPassager(email);
		List<Annonce> allAnnonce = Stream.concat(allAnnonceResponcable.stream(), allAnnoncePassager.stream())
				.collect(Collectors.toList());

		return allAnnonce;
	}

	/**
	 * @return une liste de toute les reservation au statut en cours
	 */
	public List<Annonce> getAllAnnoncesEnCours() {
		List<Annonce> allAnnonce = this.getAnnonceEnCours(this.annonceRepository.findAllWithDateDepartAfter());
		return allAnnonce;
	}

	@Transactional
	public Annonce postAnnonce(String email, @Valid AnnonceDto annonceDto) {
		Annonce annonce = null;
		// find collegue en tant que responsable
		Optional<Collegue> responsable = this.collegueRepository.findOneByEmail(email);

		Itineraire itineraire = new Itineraire(annonceDto.getDateDepart(), annonceDto.getDateArrivee(),
				annonceDto.getLieuDepart(), annonceDto.getLieuDestination(), annonceDto.getDureeTrajet(),
				annonceDto.getDistance());

		if (responsable.isPresent()) {
			annonce = new Annonce(itineraire, responsable.get(), annonceDto.getImmatriculation(),
					annonceDto.getMarque(), annonceDto.getModele(), annonceDto.getNbPlace(),
					StatutAnnonce.STATUT_EN_COURS);
			this.annonceRepository.save(annonce);

			return annonce;
		} else {
			throw new CollegueNonTrouveException("Aucun collègue trouvé avec cet email : " + email);
		}
	}

	public Annonce annulerAnnonce(Long id) {

		// récuperer une annonce by son id
		Annonce annonce = this.getAnnonceById(id);

		annonce.setStatut(StatutAnnonce.STATUT_ANNULE);
		//si la transaction echoue l'annonce passe a null
		annonce = this.annonceRepository.save(annonce);
		// si la transation a eu lieu, envoie un mail de comfirmation au responcavle et
		// aux different passager
		if (annonce != null) {
			this.envoiMailService.envoyerMailAnnulationAnnonce(annonce);
			return annonce;
		} else {
			throw new AnnonceNotDeletedException("L'annonce n'a pas pu etre annulé");
		}

	}

	public Annonce annulerReservation(Long id, String emailPassager) {

		// récuperer une annonce by son id
		Annonce annonce = this.getAnnonceById(id);
		//réccupere le passager avec son mail 
		Optional<Collegue> collegue = collegueRepository.findOneByEmail(emailPassager);
		if (collegue.isPresent()) {
			Collegue passager = collegue.get(); 
			//Liberation d'une place dans l'annonce 
			Integer nbPlaceAnnonce =annonce.getNbPlace()+1; 
			annonce.setNbPlace(nbPlaceAnnonce);
			
			//La réservation passe en statut annulé
			annonce.setStatut(StatutAnnonce.STATUT_ANNULE);
			
			// retrouve collégue dans la liste et supression du collégue
			List<Collegue> listPassager = annonce.getListPassagers();
			
	        Iterator<Collegue> itr = listPassager.iterator();
	        while(itr.hasNext()){
	            if(emailPassager.equals(itr.next().getEmail())){
	                itr.remove();
	            }
	        }

			
			//si la transaction echoue l'annonce passe a null
			annonce = this.annonceRepository.save(annonce);

			// si la transation a eu lieu, envoie un mail de comfirmation au responcavle et
			// aux different passager
			if (annonce != null) {
				this.envoiMailService.envoyerMailAnnulationReservation(annonce, passager);
				return annonce;
				
			} else {
				throw new ReservationNotDeletedException("La reservation n'a pas pu etre annulé");
			}
			
		} else {
			// TODO creer exception adaptée
			throw new CollegueNonTrouveException("Aucun passager trouvé avec cet email : " + emailPassager);
		}

	}

	/**
	 * @param id
	 * @return une annonce par son id
	 */
	public Annonce getAnnonceById(Long id) {
		Optional<Annonce> annonce = annonceRepository.findById(id);
		if (annonce.isPresent()) {
			return annonce.get();
		} else {
			// TODO creer exception adaptée
			throw new AnnonceNonTrouveException("Aucune annonce trouvé avec cet id : " + id);
		}
	}

	/**
	 * Methode permettant l'update de l'annonce, soit le retrait d'un place dans
	 * nbPlace de l'annonce et ajout du Collegue passager dans la liste des
	 * passagers
	 * 
	 * @param id    de l'annonce
	 * @param email du passager (Collegue)
	 * @return l'annonce mise à jour
	 */
	public Annonce putReservation(Long id, String email) {
		// Réccuperation de l'annonce sujet de la réservationCovoit
		Annonce annonceResa = this.getAnnonceById(id);
		// reccuperation du passager grace a l'email envoyé dans le body de la requete
		Optional<Collegue> passager = collegueRepository.findOneByEmail(email);
		// verification de l'optionnal
		if (passager.isPresent()) {
			// Mise a Jour du nombre de place disponible
			annonceResa.setNbPlace(annonceResa.getNbPlace() - 1);
			// ajout du passager dans l'annonce
			annonceResa.getListPassagers().add(passager.get());
			// update de la base de donnée
			annonceRepository.save(annonceResa);

			return annonceResa;
		} else {
			// TODO creer exception adaptée
			throw new CollegueNonTrouveException("Aucun passager trouvé avec cet email : " + email);
		}

	}

	// New!!

	public List<Annonce> getReservationsEncoursByPassager(String email) {
		List<Annonce> listReservations = this.getAnnonceByPassager(email);
		List<Annonce> listReservationsEncours = this.getAnnonceEnCours(listReservations);

		return listReservationsEncours;
	}

	public List<Annonce> getReservationsHistoriqueByPassager(String email) {
		List<Annonce> listReservations = this.getAnnonceByPassager(email);
		List<Annonce> listReservationsHistorique = this.getHistoriqueAnnonce(listReservations);
		return listReservationsHistorique;
	}

	public List<Annonce> getAnnoncesEncoursByResponsable(String email) {
		List<Annonce> listAnnonces = this.getAnnoncesByResponcable(email);
		List<Annonce> listAnnoncesEnCours = this.getAnnonceEnCours(listAnnonces);
		return listAnnoncesEnCours;
	}

	public List<Annonce> getAnnoncesHistoriqueByResponsable(String email) {
		List<Annonce> listAnnonces = this.getAnnoncesByResponcable(email);
		List<Annonce> listAnnoncesHistorique = this.getHistoriqueAnnonce(listAnnonces);
		return listAnnoncesHistorique;
	}

}
