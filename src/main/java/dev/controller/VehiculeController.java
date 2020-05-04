package dev.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.Vehicule;
import dev.entites.dto.VehiculeDto;
import dev.service.VehiculeService;

@RestController
@RequestMapping("vehicule")
public class VehiculeController {

	private VehiculeService vehiculeService;

	/**
	 * Constructor
	 *
	 * @param vehiculeService
	 */
	public VehiculeController(VehiculeService vehiculeService) {
		super();
		this.vehiculeService = vehiculeService;
	}

	@GetMapping
	public List<Vehicule> getAllVehicules() {

		return this.vehiculeService.getAllVehicules();
	}

	@PostMapping
	public Vehicule postVehicule(@RequestBody @Valid VehiculeDto vehiculeDto) {

		return this.vehiculeService.postVehicule(vehiculeDto);
	}
}
