package dev.entites;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.sun.xml.bind.v2.TODO;

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
	 * Constructeur
	 * 
	 * @param id
	 * @param itineraire
	 * @param responsable
	 * @param chauffeurId
	 * @param listPassagers
	 * 
	 *                      {@link TODO} Verifier si possible d'instancier sans
	 *                      chauffeurId
	 */
	public Reservation(Itineraire itineraire, Collegue responsable, Collegue chauffeur, List<Collegue> listPassagers,
			StatutReservation statut) {
		super();
		this.itineraire = itineraire;
		this.responsable = responsable;
		this.chauffeur = chauffeur;
		this.statut = statut;
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

}
