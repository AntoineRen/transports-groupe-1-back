package dev.entites.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AnnonceDto {

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

	@NotBlank
	private String immatriculation;
	@NotBlank
	private String marque;
	@NotBlank
	private String modele;

	@NotNull
	private Integer nbPlace;

	/**
	 * Constructeur
	 * 
	 * @param dateDepart
	 * @param dateArrivee
	 * @param lieuDepart
	 * @param lieuDestination
	 * @param dureeTrajet
	 * @param distance
	 * @param responsable_id
	 * @param immatriculation
	 * @param marque
	 * @param modele
	 * @param nbPlace
	 */
	public AnnonceDto(LocalDateTime dateDepart, LocalDateTime dateArrivee, @NotBlank String lieuDepart,
			@NotBlank String lieuDestination, @NotNull Integer dureeTrajet, @NotNull Double distance,
			@NotNull Long responsable_id, @NotBlank String immatriculation, @NotBlank String marque,
			@NotBlank String modele, @NotNull Integer nbPlace) {
		super();
		this.dateDepart = dateDepart;
		this.dateArrivee = dateArrivee;
		this.lieuDepart = lieuDepart;
		this.lieuDestination = lieuDestination;
		this.dureeTrajet = dureeTrajet;
		this.distance = distance;
		this.responsable_id = responsable_id;
		this.immatriculation = immatriculation;
		this.marque = marque;
		this.modele = modele;
		this.nbPlace = nbPlace;
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
	 * Getter
	 * 
	 * @return the dateArrivee
	 */
	public LocalDateTime getDateArrivee() {
		return dateArrivee;
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
	 * Getter
	 * 
	 * @return the lieuDestination
	 */
	public String getLieuDestination() {
		return lieuDestination;
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
	 * Getter
	 * 
	 * @return the distance
	 */
	public Double getDistance() {
		return distance;
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
	 * Getter
	 * 
	 * @return the immatriculation
	 */
	public String getImmatriculation() {
		return immatriculation;
	}

	/**
	 * Getter
	 * 
	 * @return the marque
	 */
	public String getMarque() {
		return marque;
	}

	/**
	 * Getter
	 * 
	 * @return the modele
	 */
	public String getModele() {
		return modele;
	}

	/**
	 * Getter
	 * 
	 * @return the nbPlace
	 */
	public Integer getNbPlace() {
		return nbPlace;
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
	 * Setter
	 * 
	 * @param dateArrivee the dateArrivee to set
	 */
	public void setDateArrivee(LocalDateTime dateArrivee) {
		this.dateArrivee = dateArrivee;
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
	 * Setter
	 * 
	 * @param lieuDestination the lieuDestination to set
	 */
	public void setLieuDestination(String lieuDestination) {
		this.lieuDestination = lieuDestination;
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
	 * Setter
	 * 
	 * @param distance the distance to set
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
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
	 * Setter
	 * 
	 * @param immatriculation the immatriculation to set
	 */
	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}

	/**
	 * Setter
	 * 
	 * @param marque the marque to set
	 */
	public void setMarque(String marque) {
		this.marque = marque;
	}

	/**
	 * Setter
	 * 
	 * @param modele the modele to set
	 */
	public void setModele(String modele) {
		this.modele = modele;
	}

	/**
	 * Setter
	 * 
	 * @param nbPlace the nbPlace to set
	 */
	public void setNbPlace(Integer nbPlace) {
		this.nbPlace = nbPlace;
	}

}