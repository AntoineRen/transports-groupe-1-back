package dev.entites.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class PeriodeDto {

	@NotNull
	private LocalDateTime dateDepart;
	@NotNull
	private LocalDateTime dateRetour;

	/**
	 * Constructor
	 *
	 */
	public PeriodeDto() {
	}

	/**
	 * Constructor
	 *
	 * @param dateDepart
	 * @param dateRetour
	 */
	public PeriodeDto(LocalDateTime dateDepart, LocalDateTime dateRetour) {
		super();
		this.dateDepart = dateDepart;
		this.dateRetour = dateRetour;
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
	 * @return the dateRetour
	 */
	public LocalDateTime getDateRetour() {
		return dateRetour;
	}

	/**
	 * Setter
	 *
	 * @param dateRetour the dateRetour to set
	 */
	public void setDateRetour(LocalDateTime dateRetour) {
		this.dateRetour = dateRetour;
	}

}
