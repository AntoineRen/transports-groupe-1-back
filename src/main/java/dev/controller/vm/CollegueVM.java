package dev.controller.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dev.entites.Collegue;
import dev.entites.utiles.Role;

/**
 * Structure modèlisant un collègue servant à communiquer avec l'extérieur (WEB
 * API).
 */
public class CollegueVM {

	private String email;
	private String nom;
	private String prenom;
	private List<Role> roles = new ArrayList<>();
	private String numTelephone;
	private String photoUrl;

	public CollegueVM(Collegue col) {
		this.email = col.getEmail();
		this.nom = col.getNom();
		this.prenom = col.getPrenom();
		this.roles = col.getRoles().stream().map(roleCollegue -> roleCollegue.getRole()).collect(Collectors.toList());
		this.numTelephone = col.getNumTelephone();
		this.photoUrl = col.getPhotoUrl();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
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

	/**
	 * Getter
	 *
	 * @return the photoUrl
	 */
	public String getPhotoUrl() {
		return photoUrl;
	}

	/**
	 * Setter
	 *
	 * @param photoUrl the photoUrl to set
	 */
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
}
