package dev.entites.dto;

public class StatistiquesDto {

	private Integer enCours;
	private Integer termines;
	private Integer total;

	/**
	 * Constructor
	 *
	 */
	public StatistiquesDto() {
	}

	/**
	 * Constructor
	 *
	 * @param enCours
	 * @param termines
	 * @param annules
	 * @param total
	 */
	public StatistiquesDto(Integer enCours, Integer termines, Integer total) {
		this.enCours = enCours;
		this.termines = termines;
		this.total = total;
	}

	/**
	 * Getter
	 *
	 * @return the enCours
	 */
	public Integer getEnCours() {
		return enCours;
	}

	/**
	 * Setter
	 *
	 * @param enCours the enCours to set
	 */
	public void setEnCours(Integer enCours) {
		this.enCours = enCours;
	}

	/**
	 * Getter
	 *
	 * @return the termines
	 */
	public Integer getTermines() {
		return termines;
	}

	/**
	 * Setter
	 *
	 * @param termines the termines to set
	 */
	public void setTermines(Integer termines) {
		this.termines = termines;
	}

	/**
	 * Getter
	 *
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * Setter
	 *
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

}
