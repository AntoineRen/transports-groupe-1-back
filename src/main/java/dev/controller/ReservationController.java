package dev.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.Reservation;
import dev.entites.dto.ReservationDto;
import dev.service.ReservationService;

@RestController
@RequestMapping("reservation")
public class ReservationController {

	private ReservationService reservationService;

	/**
	 * Constructor
	 *
	 * @param reservationService
	 */
	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@GetMapping
	public List<Reservation> getAllReservations() {

		return this.reservationService.getAllReservations();
	}

	@PostMapping
	public Reservation postReservation(@RequestBody @Valid ReservationDto reservationDto) {

		return this.reservationService.postReservation(reservationDto);
	}

}
