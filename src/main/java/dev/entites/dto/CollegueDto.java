package dev.entites.dto;

import javax.validation.constraints.NotBlank;

public class CollegueDto {

	@NotBlank
	private String nom;
	@NotBlank
	private String prenom;
	@NotBlank
	private String email;
	@NotBlank
	private String motDePasse;
	@NotBlank
	private String numTelephone;

	/**
	 * Constructor
	 *
	 * @param nom
	 * @param prenom
	 * @param email
	 * @param motDePasse
	 * @param matricule
	 * @param numTelephone
	 */
	public CollegueDto(String nom, String prenom, String email, String motDePasse,
			String numTelephone) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.motDePasse = motDePasse;
		this.numTelephone = numTelephone;
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
	 * Setter
	 *
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
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
	 * Setter
	 *
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
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
	 * Setter
	 *
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * Setter
	 *
	 * @param motDePasse the motDePasse to set
	 */
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
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
	 * Setter
	 *
	 * @param numTelephone the numTelephone to set
	 */
	public void setNumTelephone(String numTelephone) {
		this.numTelephone = numTelephone;
	}

}
