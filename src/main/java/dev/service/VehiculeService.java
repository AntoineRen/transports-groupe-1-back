package dev.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import dev.entites.VehiculeSociete;
import dev.entites.dto.VehiculeSocieteDto;
import dev.entites.utiles.Categorie;
import dev.entites.utiles.StatutVehiculeSociete;
import dev.repository.VehiculeRepository;

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

	public List<VehiculeSociete> getAllVehicules() {

		return this.vehiculeRepository.findAll();
	}

	@Transactional
	public VehiculeSociete postVehicule(@Valid VehiculeSocieteDto vehiculeDto) {

		// TODO verification doublons

		VehiculeSociete vehicule = new VehiculeSociete(vehiculeDto.getImmatriculation(), vehiculeDto.getMarque(),
				vehiculeDto.getModele(), Categorie.valueOf(vehiculeDto.getCategorie()), vehiculeDto.getNbPlace(),
				StatutVehiculeSociete.EN_SERVICE);

		this.vehiculeRepository.save(vehicule);

		return vehicule;
	}

}
