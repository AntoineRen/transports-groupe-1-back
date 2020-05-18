package dev.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import dev.entites.utiles.Role;

@Entity
public class Collegue extends BaseEntite {

	@NotBlank
	private String nom;

	@NotBlank
	private String prenom;

	private String email;

	private String motDePasse;

	private String numTelephone;
	
	private String permis;
	
	@NotBlank
	private String matricule;
	
	private String photoUrl;

	@JsonManagedReference
	@OneToMany(mappedBy = "collegue", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private List<RoleCollegue> roles;

	@ManyToMany
	private List<Annonce> listCovoiturageAnnonces;

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
	public Collegue(String nom, String prenom, String email, String motDePasse, String numTelephone,
			List<RoleCollegue> roles, String permis, String matricule, String photoUrl) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.motDePasse = motDePasse;
		this.numTelephone = numTelephone;
		this.roles = roles;
		this.permis = permis;
		this.matricule = matricule;
		this.photoUrl = photoUrl;
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
	public Collegue(String nom, String prenom, String email, String motDePasse, String numTelephone) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.motDePasse = motDePasse;
		this.numTelephone = numTelephone;
		this.roles = new ArrayList<>();
	}

	/**
	 * Si un collegue a le role de chauffeur
	 * 
	 * @return boolean
	 */
	public boolean isChauffeur() {

		for (RoleCollegue role : this.getRoles()) {

			if (role.getRole().equals(Role.ROLE_CHAUFFEUR)) {
				return true;
			}
		}

		return false;
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
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	
	public String getMatricule() {
		return matricule;
	}
	

	public void setPermis(String permis) {
		this.permis = permis;
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



	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}



	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	

	public String getPermis() {
		return permis;
	}

}
