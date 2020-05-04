package dev.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import dev.entites.Annonce;
import dev.entites.Collegue;
import dev.entites.Itineraire;
import dev.entites.dto.AnnonceDto;
import dev.repository.AnnonceRepository;
import dev.repository.CollegueRepository;

@Service
public class AnnonceService {

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

	public List<Annonce> getAllAnnonces() {
		return this.annonceRepository.findAll();
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
