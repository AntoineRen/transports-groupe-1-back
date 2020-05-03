package dev.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import dev.entites.Vehicule;
import dev.entites.dto.VehiculeDto;
import dev.entites.utiles.Categorie;
import dev.entites.utiles.StatutVehicule;

@Service
public class VehiculeService {

	private VehiculeRepository vehiculeRepository;

	/**
	 * Constructor
	 *
	 * @param vehiculeRepository
	 */
	public VehiculeService(VehiculeRepository vehiculeRepository) {
		this.vehiculeRepository = vehiculeRepository;
	}

	public List<Vehicule> getAllVehicules() {

		return this.vehiculeRepository.findAll();
	}

	@Transactional
	public Vehicule postVehicule(@Valid VehiculeDto vehiculeDto) {

		// TODO verification doublons

		Vehicule vehicule = new Vehicule(vehiculeDto.getImmatriculation(), vehiculeDto.getMarque(),
				vehiculeDto.getModele(), Categorie.valueOf(vehiculeDto.getCategorie()), vehiculeDto.getNbPlace(),
				StatutVehicule.EN_SERVICE, true);

		this.vehiculeRepository.save(vehicule);

		return vehicule;
	}

}
