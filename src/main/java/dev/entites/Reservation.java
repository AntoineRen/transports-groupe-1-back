package dev.entites;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import dev.entites.utiles.StatutReservation;

@Entity
public class Reservation extends BaseEntite {

	@Embedded
	private Itineraire itineraire;
	@ManyToOne
	@JoinColumn(name = "RESPONSABLE")
	private Collegue responsable;
	@ManyToOne
	@JoinColumn(name = "CHAUFFEUR")
	private Collegue chauffeur;
	@Enumerated(EnumType.STRING)
	private StatutReservation statut;
	@ManyToOne
	@JoinColumn(name = "VEHICULE")
	private VehiculeSociete vehicule;

	/**
	 * Constructeur
	 * 
	 */
	public Reservation() {

	}

	/**
	 * Constructor
	 *
	 * @param itineraire
	 * @param responsable
	 * @param chauffeurId
	 * @param listPassagers
	 * @param statut
	 * @param vehiculeId
	 */
	public Reservation(Itineraire itineraire, Collegue responsable, Collegue chauffeur, StatutReservation statut,
			VehiculeSociete vehicule) {
		super();
		this.itineraire = itineraire;
		this.responsable = responsable;
		this.chauffeur = chauffeur;
		this.statut = statut;
		this.vehicule = vehicule;
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
	 * @return the chauffeurId
	 */
	public Collegue getChauffeurId() {
		return chauffeur;
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
	 * @param chauffeurId the chauffeurId to set
	 */
	public void setChauffeurId(Collegue chauffeur) {
		this.chauffeur = chauffeur;
	}

	/**
	 * Getter
	 * 
	 * @return the statut
	 */
	public StatutReservation getStatut() {
		return statut;
	}

	/**
	 * Setter
	 * 
	 * @param statut the statut to set
	 */
	public void setStatut(StatutReservation statut) {
		this.statut = statut;
	}

	/**
	 * Getter
	 *
	 * @return the chauffeur
	 */
	public Collegue getChauffeur() {
		return chauffeur;
	}

	/**
	 * Setter
	 *
	 * @param chauffeur the chauffeur to set
	 */
	public void setChauffeur(Collegue chauffeur) {
		this.chauffeur = chauffeur;
	}

	/**
	 * Getter
	 *
	 * @return the vehicule
	 */
	public VehiculeSociete getVehicule() {
		return vehicule;
	}

	/**
	 * Setter
	 *
	 * @param vehicule the vehicule to set
	 */
	public void setVehicule(VehiculeSociete vehicule) {
		this.vehicule = vehicule;
	}

}
