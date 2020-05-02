package dev.entites;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.sun.xml.bind.v2.TODO;

import dev.entites.utiles.Role;
import dev.entites.utiles.StatutReservation;

@Entity
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Embedded
	private Itineraire itineraire;
	@ManyToOne
	private Collegue responsable;
	@ManyToOne
	private Collegue chauffeur;
	@ManyToMany
	private List<Collegue> listPassagers;
	@Enumerated(EnumType.STRING)
	private StatutReservation statut;
	@ManyToOne
	private Vehicule vehicule;

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
	 * @param chauffeur
	 * @param listPassagers
	 * 
	 *                      {@link TODO} Verifier si possible d'instancier sans
	 *                      chauffeur
	 */
	public Reservation(Long id, Itineraire itineraire, Collegue responsable, Collegue chauffeur,
			List<Collegue> listPassagers, StatutReservation statut) {
		this.id = id;
		this.itineraire = itineraire;
		this.responsable = responsable;
		this.chauffeur = chauffeur;
		this.listPassagers = listPassagers;
		this.statut = statut;
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
	 * @return the chauffeur
	 */
	public Collegue getChauffeur() {
		return chauffeur;
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
	 * @param chauffeur the chauffeur to set
	 */
	public void setChauffeur(Collegue chauffeur) {
		this.chauffeur = chauffeur;
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
