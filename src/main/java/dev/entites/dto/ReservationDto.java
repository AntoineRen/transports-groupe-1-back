package dev.entites.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ReservationDto {

	private LocalDateTime dateDepart; // TODO ajouter NOTNULL
	private LocalDateTime dateArrivee; // TODO ajouter NOTNULL
	@NotBlank
	private String lieuDepart;
	@NotBlank
	private String lieuDestination;

	/** dureeTrajet : La duree du trajet exprimée en minute */
	@NotNull
	private Integer dureeTrajet;

	/** distance : La distance est exprimée en Kilometre */
	@NotNull
	private Double distance;

	@NotNull
	private Long responsable_id;

	@NotNull
	private Long vehicule_id;

	/**
	 * Constructor
	 *
	 * @param dateDepart
	 * @param dateArrivee
	 * @param lieuDepart
	 * @param lieuDestination
	 * @param dureeTrajet
	 * @param distance
	 * @param responsable_id
	 * @param chauffeur_id
	 * @param vehicule_id
	 */
	public ReservationDto(LocalDateTime dateDepart, LocalDateTime dateArrivee, String lieuDepart,
			String lieuDestination, Integer dureeTrajet, Double distance, Long responsable_id, Long vehicule_id) {
		super();
		this.dateDepart = dateDepart;
		this.dateArrivee = dateArrivee;
		this.lieuDepart = lieuDepart;
		this.lieuDestination = lieuDestination;
		this.dureeTrajet = dureeTrajet;
		this.distance = distance;
		this.responsable_id = responsable_id;
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
	 * @return the lieuDepart
	 */
	public String getLieuDepart() {
		return lieuDepart;
	}

	/**
	 * Setter
	 *
	 * @param lieuDepart the lieuDepart to set
	 */
	public void setLieuDepart(String lieuDepart) {
		this.lieuDepart = lieuDepart;
	}

	/**
	 * Getter
	 *
	 * @return the lieuDestination
	 */
	public String getLieuDestination() {
		return lieuDestination;
	}

	/**
	 * Setter
	 *
	 * @param lieuDestination the lieuDestination to set
	 */
	public void setLieuDestination(String lieuDestination) {
		this.lieuDestination = lieuDestination;
	}

	/**
	 * Getter
	 *
	 * @return the dureeTrajet
	 */
	public Integer getDureeTrajet() {
		return dureeTrajet;
	}

	/**
	 * Setter
	 *
	 * @param dureeTrajet the dureeTrajet to set
	 */
	public void setDureeTrajet(Integer dureeTrajet) {
		this.dureeTrajet = dureeTrajet;
	}

	/**
	 * Getter
	 *
	 * @return the distance
	 */
	public Double getDistance() {
		return distance;
	}

	/**
	 * Setter
	 *
	 * @param distance the distance to set
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	/**
	 * Getter
	 *
	 * @return the responsable_id
	 */
	public Long getResponsable_id() {
		return responsable_id;
	}

	/**
	 * Setter
	 *
	 * @param responsable_id the responsable_id to set
	 */
	public void setResponsable_id(Long responsable_id) {
		this.responsable_id = responsable_id;
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
