package dev.entites;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import dev.entites.utiles.StatutDemandeChauffeur;
import dev.entites.utiles.StatutReservation;

@Entity
public class Reservation extends BaseEntite {

	private LocalDateTime dateDepart;
	private LocalDateTime dateArrivee;
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
	@Enumerated(EnumType.STRING)
	private StatutDemandeChauffeur statutDemandeChauffeur;

	/**
	 * Constructeur
	 * 
	 */
	public Reservation() {

	}

	/**
	 * Constructor
	 *
	 * @param dateDepart
	 * @param dateArrivee
	 * @param responsable
	 * @param chauffeur
	 * @param statut
	 * @param vehicule
	 */
	public Reservation(LocalDateTime dateDepart, LocalDateTime dateArrivee, Collegue responsable, Collegue chauffeur,
			StatutReservation statut, VehiculeSociete vehicule, StatutDemandeChauffeur statutDemandeChauffeur) {
		super();
		this.dateDepart = dateDepart;
		this.dateArrivee = dateArrivee;
		this.responsable = responsable;
		this.chauffeur = chauffeur;
		this.statut = statut;
		this.vehicule = vehicule;
		this.statutDemandeChauffeur = statutDemandeChauffeur;
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
	 * @return the responsable
	 */
	public Collegue getResponsable() {
		return responsable;
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

	/**
	 * Getter
	 *
	 * @return the statutDemandeChauffeur
	 */
	public StatutDemandeChauffeur getStatutDemandeChauffeur() {
		return statutDemandeChauffeur;
	}

	/**
	 * Setter
	 *
	 * @param statutDemandeChauffeur the statutDemandeChauffeur to set
	 */
	public void setStatutDemandeChauffeur(StatutDemandeChauffeur statutDemandeChauffeur) {
		this.statutDemandeChauffeur = statutDemandeChauffeur;
	}

}
