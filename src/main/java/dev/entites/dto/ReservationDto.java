package dev.entites.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class ReservationDto {

	@NotNull
	private LocalDateTime dateDepart;
	@NotNull
	private LocalDateTime dateArrivee;
	@NotNull
	private Long vehiculeId;
	@NotNull
	private Boolean avecChauffeur;

	/**
	 * Constructor
	 *
	 * @param dateDepart
	 * @param dateArrivee
	 * @param vehiculeId
	 * @param avecChauffeur
	 */
	public ReservationDto(@NotNull LocalDateTime dateDepart, @NotNull LocalDateTime dateArrivee,
			@NotNull Long vehiculeId, @NotNull Boolean avecChauffeur) {
		super();
		this.dateDepart = dateDepart;
		this.dateArrivee = dateArrivee;
		this.vehiculeId = vehiculeId;
		this.avecChauffeur = avecChauffeur;
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
	 * @return the vehiculeId
	 */
	public Long getVehiculeId() {
		return vehiculeId;
	}

	/**
	 * Setter
	 *
	 * @param vehiculeId the vehiculeId to set
	 */
	public void setVehiculeId(Long vehiculeId) {
		this.vehiculeId = vehiculeId;
	}

	/**
	 * Getter
	 *
	 * @return the avecChauffeur
	 */
	public Boolean getAvecChauffeur() {
		return avecChauffeur;
	}

	/**
	 * Setter
	 *
	 * @param avecChauffeur the avecChauffeur to set
	 */
	public void setAvecChauffeur(Boolean avecChauffeur) {
		this.avecChauffeur = avecChauffeur;
	}

}
