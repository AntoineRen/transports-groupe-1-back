package dev.entites.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class ReservationDto {

	@NotNull
	private LocalDateTime dateDepart;
	@NotNull
	private LocalDateTime dateArrivee;
	@NotNull
	private Long vehicule_id;

	/**
	 * Constructor
	 *
	 * @param dateDepart
	 * @param dateArrivee
	 * @param vehicule_id
	 */
	public ReservationDto(@NotNull LocalDateTime dateDepart, @NotNull LocalDateTime dateArrivee,
			@NotNull Long vehicule_id) {
		super();
		this.dateDepart = dateDepart;
		this.dateArrivee = dateArrivee;
		this.vehicule_id = vehicule_id;
	}

	/**
	 * Getter
	 *
	 * @return the dateDepart
	 */
	public LocalDateTime getDateDepart() {
		return dateDepart;
	}

	/**
	 * Setter
	 *
	 * @param dateDepart the dateDepart to set
	 */
	public void setDateDepart(LocalDateTime dateDepart) {
		this.dateDepart = dateDepart;
	}

	/**
	 * Getter
	 *
	 * @return the dateArrivee
	 */
	public LocalDateTime getDateArrivee() {
		return dateArrivee;
	}

	/**
	 * Setter
	 *
	 * @param dateArrivee the dateArrivee to set
	 */
	public void setDateArrivee(LocalDateTime dateArrivee) {
		this.dateArrivee = dateArrivee;
	}

	/**
	 * Getter
	 *
	 * @return the vehicule_id
	 */
	public Long getVehicule_id() {
		return vehicule_id;
	}

	/**
	 * Setter
	 *
	 * @param vehicule_id the vehicule_id to set
	 */
	public void setVehicule_id(Long vehicule_id) {
		this.vehicule_id = vehicule_id;
	}

}
