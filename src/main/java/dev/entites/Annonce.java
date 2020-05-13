package dev.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Annonce extends BaseEntite {
	@Embedded
	private Itineraire itineraire;
	@ManyToOne
	@JoinColumn(name = "RESPONSABLE")
	private Collegue responsable;
	@ManyToMany
	private List<Collegue> listPassagers;
	private String immatriculation;
	private String marque;
	private String modele;
	private Integer nbPlace;

	/**
	 * Constructeur
	 * 
	 */
	public Annonce() {
	}

	/**
	 * Constructeur
	 * 
	 * @param itineraire
	 * @param responsable
	 * @param listPassagers
	 * @param immatriculation
	 * @param marque
	 * @param modele
	 * @param nbPlace
	 */
	public Annonce(Itineraire itineraire, Collegue responsable, String immatriculation, String marque, String modele,
			Integer nbPlace) {
		super();
		this.itineraire = itineraire;
		this.responsable = responsable;
		this.listPassagers = new ArrayList<Collegue>();
		this.immatriculation = immatriculation;
		this.marque = marque;
		this.modele = modele;
		this.nbPlace = nbPlace;
	}

	/**
	 * Getter
	 * 
	 * @return the itineraire
	 */
	public Itineraire getItineraire() {
		return itineraire;
	}

	/**
	 * Getter
	 * 
	 * @return the responsable
	 */
	public Collegue getResponsable() {
		return responsable;
	}

	/**
	 * Getter
	 * 
	 * @return the listPassagers
	 */
	public List<Collegue> getListPassagers() {
		return listPassagers;
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
	 * @param itineraire the itineraire to set
	 */
	public void setItineraire(Itineraire itineraire) {
		this.itineraire = itineraire;
	}

	/**
	 * Setter
	 * 
	 * @param responsable the responsable to set
	 */
	public void setResponsable(Collegue responsable) {
		this.responsable = responsable;
	}

	/**
	 * Setter
	 * 
	 * @param listPassagers the listPassagers to set
	 */
	public void setListPassagers(List<Collegue> listPassagers) {
		this.listPassagers = listPassagers;
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

	@Override
	public String toString() {
		return "Annonce{" +
				"itineraire=" + itineraire +
				", responsable=" + responsable +
				", listPassagers=" + listPassagers +
				", immatriculation='" + immatriculation + '\'' +
				", marque='" + marque + '\'' +
				", modele='" + modele + '\'' +
				", nbPlace=" + nbPlace +
				'}';
	}
}
