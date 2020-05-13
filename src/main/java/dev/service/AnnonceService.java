package dev.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.aspectj.weaver.patterns.ThrowsPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import dev.entites.Annonce;
import dev.entites.Collegue;
import dev.entites.Itineraire;
import dev.entites.dto.AnnonceDto;
import dev.exceptions.CollegueNonTrouveException;
import dev.repository.AnnonceRepository;
import dev.repository.CollegueRepository;
import net.bytebuddy.implementation.bytecode.Throw;

/**
 * @author jules
 *
 */
@Service
public class AnnonceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AnnonceService.class);

	private AnnonceRepository annonceRepository;
	private CollegueRepository collegueRepository;

	/**
	 * Constructor
	 *
	 * @param annonceRepository
	 * @param collegueRepository
	 */
	public AnnonceService(AnnonceRepository annonceRepository, CollegueRepository collegueRepository) {
		this.annonceRepository = annonceRepository;
		this.collegueRepository = collegueRepository;
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
		LOGGER.info("getHistoriqueAnnonce {} LocalDateTime.now().plusMinutes(5)", LocalDateTime.now().plusMinutes(5));
		List<Annonce> annonceEncours = listAnnonces.stream().filter(
				annonce -> annonce.getItineraire().getDateArrivee().isBefore(LocalDateTime.now().plusMinutes(5)))
				.sorted((a, b) -> a.getItineraire().getDateDepart().compareTo((b.getItineraire().getDateDepart())))
				.collect(Collectors.toList());
		return annonceEncours;
	}

	/**TODO soit disparaitre
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

	@Transactional
	public Annonce postAnnonce(@Valid AnnonceDto annonceDto) {
		Annonce annonce = null;

		Itineraire itineraire = new Itineraire(annonceDto.getDateDepart(), annonceDto.getDateArrivee(),
				annonceDto.getLieuDepart(), annonceDto.getLieuDestination(), annonceDto.getDureeTrajet(),
				annonceDto.getDistance());

		// find collegue en tant que responsable
		Optional<Collegue> responsable = this.collegueRepository.findById(annonceDto.getResponsable_id());

		if (responsable.isPresent()) {
			annonce = new Annonce(itineraire, responsable.get(), annonceDto.getImmatriculation(),
					annonceDto.getMarque(), annonceDto.getModele(), annonceDto.getNbPlace());
			this.annonceRepository.save(annonce);

			return annonce;
		} else {
			throw new RuntimeException(); // TODO creer exception responsable non trouvé
		}
	}

}
