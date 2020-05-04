package dev.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.VehiculeSociete;
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
		super();
		this.vehiculeService = vehiculeService;
	}

	@GetMapping
	public List<VehiculeSociete> getAllVehicules() {

		return this.vehiculeService.getAllVehicules();
	}

	@PostMapping
	public VehiculeSociete postVehicule(@RequestBody @Valid VehiculeSocieteDto vehiculeDto) {

		return this.vehiculeService.postVehicule(vehiculeDto);
	}
}
