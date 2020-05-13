package dev.entites;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;

@Embeddable
public class Itineraire{

	private LocalDateTime dateDepart;
	private LocalDateTime dateArrivee;
	private String lieuDepart;
	private String lieuDestination;

	/**dureeTrajet : La duree du trajet exprimée en minute*/
	private Integer dureeTrajet;
	
	/**distance : La distance est exprimée en Kilometre*/
	private Double distance;

	
	
	/**Constructeur
	 * 
	 */
	public Itineraire() {

	}


	/**
	 * Constructeur
	 * 
	 * @param dateDepart
	 * @param dateArrivee
	 * @param lieuDepart
	 * @param lieuDestination
	 * @param dureeTrajet
	 * @param distance
	 */
	public Itineraire(LocalDateTime dateDepart, LocalDateTime dateArrivee, String lieuDepart, String lieuDestination,
			Integer dureeTrajet, Double distance) {
		this.dateDepart = dateDepart;
		this.dateArrivee = dateArrivee;
		this.lieuDepart = lieuDepart;
		this.lieuDestination = lieuDestination;
		this.dureeTrajet = dureeTrajet;
		this.distance = distance;
	}


	/** Getter
	 * @return the dateDepart
	 */
	public LocalDateTime getDateDepart() {
		return dateDepart;
	}


	/** Getter
	 * @return the dateArrivee
	 */
	public LocalDateTime getDateArrivee() {
		return dateArrivee;
	}


	/** Getter
	 * @return the lieuDepart
	 */
	public String getLieuDepart() {
		return lieuDepart;
	}


	/** Getter
	 * @return the lieuDestination
	 */
	public String getLieuDestination() {
		return lieuDestination;
	}


	/** Getter
	 * @return the dureeTrajet
	 */
	public Integer getDureeTrajet() {
		return dureeTrajet;
	}


	/** Getter
	 * @return the distance
	 */
	public Double getDistance() {
		return distance;
	}


	/**Setter
	 * @param dateDepart the dateDepart to set
	 */
	public void setDateDepart(LocalDateTime dateDepart) {
		this.dateDepart = dateDepart;
	}


	/**Setter
	 * @param dateArrivee the dateArrivee to set
	 */
	public void setDateArrivee(LocalDateTime dateArrivee) {
		this.dateArrivee = dateArrivee;
	}


	/**Setter
	 * @param lieuDepart the lieuDepart to set
	 */
	public void setLieuDepart(String lieuDepart) {
		this.lieuDepart = lieuDepart;
	}


	/**Setter
	 * @param lieuDestination the lieuDestination to set
	 */
	public void setLieuDestination(String lieuDestination) {
		this.lieuDestination = lieuDestination;
	}


	/**Setter
	 * @param dureeTrajet the dureeTrajet to set
	 */
	public void setDureeTrajet(Integer dureeTrajet) {
		this.dureeTrajet = dureeTrajet;
	}


	/**Setter
	 * @param distance the distance to set
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "Itineraire{" +
				"dateDepart=" + dateDepart +
				", dateArrivee=" + dateArrivee +
				", lieuDepart='" + lieuDepart + '\'' +
				", lieuDestination='" + lieuDestination + '\'' +
				", dureeTrajet=" + dureeTrajet +
				", distance=" + distance +
				'}';
	}
}
