package dev.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Collegue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // TODO Long ????

	private String nom;

	private String prenom;

	private String email;

	private String motDePasse;

	private String matricule;

	private String numTelephone;
	// @OneToMany(mappedBy = "collegue", cascade = CascadeType.PERSIST)
	@OneToMany(mappedBy = "collegue", fetch = FetchType.LAZY)
	private List<RoleCollegue> roles;

	/**
	 * listReservationP : Represente les reservations d'un collegue etant passager
	 */
	@ManyToMany
	private List<Reservation> listReservationP;

	/**
	 * listReservationC : Represente les reservations d'un collegue etant chauffeur
	 */
	@OneToMany
	private List<Reservation> listReservationC;

	/**
	 * listReservationRa : Represente les reservations d'un collegue etant
	 * Responsable d'annonce
	 */
	@OneToMany
	private List<Reservation> listReservationRA;

	/**
	 * Constructeur
	 * 
	 */
	public Collegue() {

	}

	/**
	 * Constructeur
	 * 
	 * @param id
	 * @param nom
	 * @param prenom
	 * @param email
	 * @param motDePasse
	 * @param matricule
	 * @param numTelephone
	 * @param roles
	 * @param listReservationP
	 * @param listReservationC
	 * @param listReservationRA
	 */
	public Collegue(Long id, String nom, String prenom, String email, String motDePasse, String matricule,
			String numTelephone, List<RoleCollegue> roles, List<Reservation> listReservationP,
			List<Reservation> listReservationC, List<Reservation> listReservationRA) {

		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.motDePasse = motDePasse;
		this.matricule = matricule;
		this.numTelephone = numTelephone;
		this.roles = roles;
		this.listReservationP = listReservationP;
		this.listReservationC = listReservationC;
		this.listReservationRA = listReservationRA;
	}

	/**
	 * Constructor
	 *
	 * @param nom
	 * @param prenom
	 * @param email
	 * @param motDePasse
	 * @param matricule
	 * @param numTelephone
	 * @param roles
	 * @param listReservationP
	 * @param listReservationC
	 * @param listReservationRA
	 */
	public Collegue(String nom, String prenom, String email, String motDePasse, String matricule, String numTelephone) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.motDePasse = motDePasse;
		this.matricule = matricule;
		this.numTelephone = numTelephone;
		this.roles = new ArrayList<>();
		this.listReservationP = new ArrayList<>();
		this.listReservationC = new ArrayList<>();
		this.listReservationRA = new ArrayList<>();
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
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Getter
	 * 
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Getter
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Getter
	 * 
	 * @return the motDePasse
	 */
	public String getMotDePasse() {
		return motDePasse;
	}

	/**
	 * Getter
	 * 
	 * @return the matricule
	 */
	public String getMatricule() {
		return matricule;
	}

	/**
	 * Getter
	 * 
	 * @return the numTelephone
	 */
	public String getNumTelephone() {
		return numTelephone;
	}

	/**
	 * Getter
	 * 
	 * @return the roles
	 */
	public List<RoleCollegue> getRoles() {
		return roles;
	}

	/**
	 * Getter
	 * 
	 * @return the listReservationP
	 */
	public List<Reservation> getListReservationP() {
		return listReservationP;
	}

	/**
	 * Getter
	 * 
	 * @return the listReservationC
	 */
	public List<Reservation> getListReservationC() {
		return listReservationC;
	}

	/**
	 * Getter
	 * 
	 * @return the listReservationRA
	 */
	public List<Reservation> getListReservationRA() {
		return listReservationRA;
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
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Setter
	 * 
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * Setter
	 * 
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Setter
	 * 
	 * @param motDePasse the motDePasse to set
	 */
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	/**
	 * Setter
	 * 
	 * @param matricule the matricule to set
	 */
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	/**
	 * Setter
	 * 
	 * @param numTelephone the numTelephone to set
	 */
	public void setNumTelephone(String numTelephone) {
		this.numTelephone = numTelephone;
	}

	/**
	 * Setter
	 * 
	 * @param roles the roles to set
	 */
	public void setRoles(List<RoleCollegue> roles) {
		this.roles = roles;
	}

	/**
	 * Setter
	 * 
	 * @param listReservationP the listReservationP to set
	 */
	public void setListReservationP(List<Reservation> listReservationP) {
		this.listReservationP = listReservationP;
	}

	/**
	 * Setter
	 * 
	 * @param listReservationC the listReservationC to set
	 */
	public void setListReservationC(List<Reservation> listReservationC) {
		this.listReservationC = listReservationC;
	}

	/**
	 * Setter
	 * 
	 * @param listReservationRA the listReservationRA to set
	 */
	public void setListReservationRA(List<Reservation> listReservationRA) {
		this.listReservationRA = listReservationRA;
	}

}
