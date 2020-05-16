package dev.entites.utiles;

public enum Categorie {
	CATEGORIE_MU("Micro-urbaines"), CATEGORIE_MP("Mini-citadines"), CATEGORIE_CP("Citadines polyvalente"),
	CATEGORIES_C("Compactes"), CATEGORIE_SUV("SUV - Tout-terrains et Pick-up"), CATEGORIE_BTL("Berlines Taille L"),
	CATEGORIE_BTM("Berlines Taille M"), CATEGORIE_BTS("Berlines Taille S");

	private String detail;

	private Categorie(String detail) {
		this.detail = detail;
	}

	/**
	 * Getter
	 * 
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	};
	public static Categorie getName(String nom) {

        for(Categorie c : Categorie.values()) {

            if(c.getDetail().equals(nom)) {
                return c;
            }
        }
        return null; //TODO throw une exception
    }
}
