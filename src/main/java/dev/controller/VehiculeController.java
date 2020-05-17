package dev.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.VehiculeSociete;
import dev.entites.dto.DisponibiliteDto;
import dev.entites.dto.PeriodeDto;
import dev.entites.dto.VehiculeSocieteDto;
import dev.service.VehiculeSocieteService;

@RestController
@RequestMapping("vehicule")
public class VehiculeController {

	private VehiculeSocieteService vehiculeService;

	/**
	 * Constructor
	 *
	 * @param vehiculeService
	 */
	public VehiculeController(VehiculeSocieteService vehiculeService) {
		this.vehiculeService = vehiculeService;
	}

	@GetMapping
	public List<VehiculeSocieteDto> getAllVehicules() {

		return this.vehiculeService.getAllVehicules();
	}
	
	@GetMapping(params = { "immatriculation" })
	public VehiculeSociete getAllVehicules(@RequestParam String immatriculation) {

		return this.vehiculeService.getVehiculeByImmatriculation(immatriculation);
	}

	@PostMapping
	public VehiculeSociete postVehicule(@RequestBody @Valid VehiculeSocieteDto vehiculeDto) {

		return this.vehiculeService.postVehicule(vehiculeDto);
	}

	/**
	 * Réceptionne une requete post sur l'url back_url/vehicule/disponibilite
	 * contenant dans le body une periode et renvoie une liste contenant la
	 * disponibilité de chaque véhicules en fonction de cette période
	 * 
	 * @param periodeDto
	 * @return List<DisponibiliteDto>
	 */
	@PostMapping("disponibilite")
	public List<DisponibiliteDto> disponibilitesVehicules(@RequestBody @Valid PeriodeDto periodeDto) {

		return this.vehiculeService.disponibilitesVehicules(periodeDto);
	}
}
