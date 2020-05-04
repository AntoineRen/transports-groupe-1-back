package dev.entites.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class VehiculeSocieteDto {

	@NotBlank
	private String immatriculation;
	@NotBlank
	private String marque;
	@NotBlank
	private String modele;
	@NotBlank
	private String categorie; // TODO se renseigner sur Enum
	@NotNull
	private Integer nbPlace;

	/**
	 * Constructor
	 *
	 * @param immatriculation
	 * @param marque
	 * @param modele
	 * @param categorie
	 * @param nbPlace
	 */
	public VehiculeSocieteDto(@NotBlank String immatriculation, @NotBlank String marque, @NotBlank String modele,
			@NotBlank String categorie, @NotNull Integer nbPlace) {
		super();
		this.immatriculation = immatriculation;
		this.marque = marque;
		this.modele = modele;
		this.categorie = categorie;
		this.nbPlace = nbPlace;
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

}
