package dev.entites.dto;

public class DisponibiliteDto {

	/** Long : id du véhicule */
	private Long id;
	/** Boolean : disponibilite du vehicule */
	private Boolean disponibilite;

	/**
	 * Constructor
	 *
	 */
	public DisponibiliteDto() {
	}

	/**
	 * Constructor
	 *
	 * @param id
	 * @param disponibilite
	 */
	public DisponibiliteDto(Long id, Boolean disponibilite) {
		super();
		this.id = id;
		this.disponibilite = disponibilite;
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
	 * Setter
	 *
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter
	 *
	 * @return the disponibilite
	 */
	public Boolean getDisponibilite() {
		return disponibilite;
	}

	/**
	 * Setter
	 *
	 * @param disponibilite the disponibilite to set
	 */
	public void setDisponibilite(Boolean disponibilite) {
		this.disponibilite = disponibilite;
	}

}
