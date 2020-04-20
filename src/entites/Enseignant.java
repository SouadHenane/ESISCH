package entites;

import java.util.ArrayList;

public class Enseignant {
	private String id;
	private String nom;
	private ArrayList<Horaire> preferences = null;
	public String dispo;
	public String pref;
	
	public Enseignant(String id, String nom) {
		this.id = id;
		this.nom = nom;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	public String toString() {return nom; }


	@Override
	public boolean equals(Object obj) {return  this.id == ((Enseignant) obj).id;}

	public ArrayList<Horaire> getPreferences() {
		return preferences;
	}

	public void setPreferences(ArrayList<Horaire> preferences) {
		this.preferences = preferences;
	}
	
	
	
}
