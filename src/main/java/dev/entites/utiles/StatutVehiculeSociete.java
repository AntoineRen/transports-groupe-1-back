package dev.entites.utiles;

public enum StatutVehiculeSociete {
	EN_SERVICE("En service"), EN_REPARATION("En réparation"), HORS_SERVICE("Hors service");
	
	private String detail;
	
	private StatutVehiculeSociete(String detail) {
		this.detail = detail;
	}
	
	public String getDetail() {
		return detail;
	};
	
	public static StatutVehiculeSociete getName(String nom) {

        for(StatutVehiculeSociete c : StatutVehiculeSociete.values()) {

            if(c.getDetail().equals(nom)) {
                return c;
            }
        }
        return null; //TODO throw une exception
    }
}
