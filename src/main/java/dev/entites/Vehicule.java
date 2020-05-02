package dev.entites;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import dev.entites.utiles.Categorie;
import dev.entites.utiles.StatutVehicule;

@Entity
public class Vehicule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String immatriculation;
	private String marque;
	private String modele;
	@Enumerated(EnumType.STRING)
	private Categorie categorie;
	private Integer nbPlace;
	@Enumerated(EnumType.STRING)
	private StatutVehicule statut;
	/**
	 * proprietaireSociete : Boolean pour indiquer si le vehicule appartient a la
	 * sociéte, en opposition à vehicule personnel
	 */
	private Boolean proprietaireSociete;
	@OneToMany
	private List<Reservation> listReservations;



	/**
	 * Constructeur
	 * 
	 */
	public Vehicule() {
	}

	/**
	 * Constructeur
	 * 
	 * @param id
	 * @param immatriculation
	 * @param marque
	 * @param modele
	 * @param categorie
	 * @param nbPlace
	 * @param statut
	 * @param proprietaireSociete
	 * @param listReservations
	 */
	public Vehicule(Long id, String immatriculation, String marque, String modele, Categorie categorie, Integer nbPlace,
			StatutVehicule statut, Boolean proprietaireSociete, List<Reservation> listReservations) {
		super();
		this.id = id;
		this.immatriculation = immatriculation;
		this.marque = marque;
		this.modele = modele;
		this.categorie = categorie;
		this.nbPlace = nbPlace;
		this.statut = statut;
		this.proprietaireSociete = proprietaireSociete;
		this.listReservations = listReservations;
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
	public StatutVehicule getStatut() {
		return statut;
	}

	/**
	 * Getter
	 * 
	 * @return the proprietaireSociete
	 */
	public Boolean getProprietaireSociete() {
		return proprietaireSociete;
	}

	/**
	 * Getter
	 * 
	 * @return the listReservations
	 */
	public List<Reservation> getListReservations() {
		return listReservations;
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
	public void setStatut(StatutVehicule statut) {
		this.statut = statut;
	}

	/**
	 * Setter
	 * 
	 * @param proprietaireSociete the proprietaireSociete to set
	 */
	public void setProprietaireSociete(Boolean proprietaireSociete) {
		this.proprietaireSociete = proprietaireSociete;
	}

	/**
	 * Setter
	 * 
	 * @param listReservations the listReservations to set
	 */
	public void setListReservations(List<Reservation> listReservations) {
		this.listReservations = listReservations;
	}

}
