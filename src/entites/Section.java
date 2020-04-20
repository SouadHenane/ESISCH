package entites;

import java.util.ArrayList;

public class Section {
	private String nom;
	private ArrayList<Module> modules;
	private ArrayList<Groupe> groupes;
	
	public Section(String nom, ArrayList<Module> modules, ArrayList<Groupe> groupes) {
		this.nom = nom;
		this.modules = modules;
		this.groupes= groupes;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public ArrayList<Module> getModules() {
		return modules;
	}

	public void setModules(ArrayList<Module> modules) {
		this.modules = modules;
	}

	public ArrayList<Groupe> getGroupes() {
		return groupes;
	}

	public void setGroupes(ArrayList<Groupe> groupes) {
		this.groupes = groupes;
	}
	
	
	
}
