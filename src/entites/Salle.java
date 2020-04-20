package entites;

public class Salle {
	private String code;
	private String nom;
	private int capacité;
	private String type;
	public Salle(String code, String nom,int capacité,String type) {
		this.code = code;
		this.capacité = capacité;
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getCapacité() {
		return capacité;
	}
	public void setCapacité(int capacité) {
		this.capacité = capacité;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
}
