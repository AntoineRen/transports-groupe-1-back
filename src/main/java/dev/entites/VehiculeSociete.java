package dev.entites;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import dev.entites.utiles.Categorie;
import dev.entites.utiles.StatutVehiculeSociete;

@Entity
public class VehiculeSociete extends BaseEntite {

	private String immatriculation;
	private String marque;
	private String modele;
	@Enumerated(EnumType.STRING)
	private Categorie categorie;
	private Integer nbPlace;
	@Enumerated(EnumType.STRING)
	private StatutVehiculeSociete statut;

	/**
	 * Constructeur
	 * 
	 */
	public VehiculeSociete() {
	}

	/**
	 * Constructor
	 *
	 * @param immatriculation
	 * @param marque
	 * @param modele
	 * @param categorie
	 * @param nbPlace
	 * @param statut
	 * @param proprietaireSociete
	 * @param listReservations
	 */
	public VehiculeSociete(String immatriculation, String marque, String modele, Categorie categorie, Integer nbPlace,
			StatutVehiculeSociete statut) {
		super();
		this.immatriculation = immatriculation;
		this.marque = marque;
		this.modele = modele;
		this.categorie = categorie;
		this.nbPlace = nbPlace;
		this.statut = statut;
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
	 * @return the categorie
	 */
	public Categorie getCategorie() {
		return categorie;
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
	 * Getter
	 * 
	 * @return the statut
	 */
	public StatutVehiculeSociete getStatut() {
		return statut;
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
	 * @param categorie the categorie to set
	 */
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
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
	 * Setter
	 * 
	 * @param statut the statut to set
	 */
	public void setStatut(StatutVehiculeSociete statut) {
		this.statut = statut;
	}

}
