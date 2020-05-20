package dev.entites.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import dev.entites.VehiculeSociete;

public class VehiculeSocieteDto {

	private Long id;
	@NotBlank
	private String immatriculation;
	@NotBlank
	private String marque;
	@NotBlank
	private String modele;
	@NotBlank
	private String categorie;
	@NotNull
	private Integer nbPlace;

	private String photoUrl;

	private String statut;

	private Double latitude;

	private Double longitude;

	/**
	 * Constructor
	 *
	 */
	public VehiculeSocieteDto() {
		super();
	}

	/**
	 * Constructor
	 *
	 * @param id
	 * @param immatriculation
	 * @param marque
	 * @param modele
	 * @param categorie
	 * @param nbPlace
	 * @param photoUrl
	 */
	public VehiculeSocieteDto(Long id, @NotBlank String immatriculation, @NotBlank String marque,
			@NotBlank String modele, @NotBlank String categorie, @NotNull Integer nbPlace, String photoUrl,
			String statut) {
		super();
		this.id = id;
		this.immatriculation = immatriculation;
		this.marque = marque;
		this.modele = modele;
		this.categorie = categorie;
		this.nbPlace = nbPlace;
		this.photoUrl = photoUrl;
		this.statut = statut;
	}

	public VehiculeSocieteDto(VehiculeSociete vehicule) {
		this.id = vehicule.getId();
		this.immatriculation = vehicule.getImmatriculation();
		this.marque = vehicule.getMarque();
		this.modele = vehicule.getModele();
		this.categorie = vehicule.getCategorie().getDetail();
		this.nbPlace = vehicule.getNbPlace();
		this.photoUrl = vehicule.getPhotoUrl();
		this.statut = vehicule.getStatut().getDetail();
		this.latitude = vehicule.getLatitude();
		this.longitude = vehicule.getLongitude();
	}

	/**
	 * Constructor
	 *
	 * @param id
	 * @param immatriculation
	 * @param marque
	 * @param modele
	 * @param categorie
	 * @param nbPlace
	 * @param photoUrl
	 * @param statut
	 * @param latitude
	 * @param longitude
	 */
	public VehiculeSocieteDto(Long id, @NotBlank String immatriculation, @NotBlank String marque,
			@NotBlank String modele, @NotBlank String categorie, @NotNull Integer nbPlace, String photoUrl,
			String statut, Double latitude, Double longitude) {
		super();
		this.id = id;
		this.immatriculation = immatriculation;
		this.marque = marque;
		this.modele = modele;
		this.categorie = categorie;
		this.nbPlace = nbPlace;
		this.photoUrl = photoUrl;
		this.statut = statut;
		this.latitude = latitude;
		this.longitude = longitude;
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
	 * Setter
	 *
	 * @param immatriculation the immatriculation to set
	 */
	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
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
	 * Setter
	 *
	 * @param marque the marque to set
	 */
	public void setMarque(String marque) {
		this.marque = marque;
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
	 * Setter
	 *
	 * @param modele the modele to set
	 */
	public void setModele(String modele) {
		this.modele = modele;
	}

	/**
	 * Getter
	 *
	 * @return the categorie
	 */
	public String getCategorie() {
		return categorie;
	}

	/**
	 * Setter
	 *
	 * @param categorie the categorie to set
	 */
	public void setCategorie(String categorie) {
		this.categorie = categorie;
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
	 * @param nbPlace the nbPlace to set
	 */
	public void setNbPlace(Integer nbPlace) {
		this.nbPlace = nbPlace;
	}

	/**
	 * Getter
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter
	 *
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter
	 *
	 * @return the photoUrl
	 */
	public String getPhotoUrl() {
		return photoUrl;
	}

	/**
	 * Setter
	 *
	 * @param photoUrl the photoUrl to set
	 */
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	/**
	 * Getter
	 *
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * Setter
	 *
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Getter
	 *
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * Setter
	 *
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
