package entites;


import java.awt.Window.Type;
import java.util.ArrayList;

public class Module {
	private String code = null;
	private String nom = null;
	private String type;
	private ArrayList<Enseignant> enseignants;
	private ArrayList<Enseignant> ChargeCours;
	private int coef;

	public Module(String code, String nom,String type, ArrayList<Enseignant> enseignants, ArrayList<Enseignant> chargé,int coef) {
		this.code = code;
		this.nom = nom;
		this.enseignants = enseignants;
		this.type=type;
		this.ChargeCours=chargé;
		this.coef=coef;
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
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public ArrayList<Enseignant> getEnseignants() {
		return enseignants;
	}
	public void setEnseignants(ArrayList<Enseignant> enseignants) {
		this.enseignants = enseignants;
	}
	
	public ArrayList<Enseignant>  getChargeCours() {
		return ChargeCours;
	}
	public void setChargeCours(ArrayList<Enseignant> chargeCours ) {
		ChargeCours = chargeCours;
	}
	public String toString() {
		return nom;
	}
	public int getCoef() {
		return coef;
	}
	public void setCoef(int coef) {
		this.coef = coef;
	}
	
}
