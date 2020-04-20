package entites;

import java.util.ArrayList;

public class Horaire {
	private String code;
	private String jour;
	private String heur;
	private ArrayList<Enseignant> EnsDispo;
	public ArrayList<Section> sections = new ArrayList<>();
	public Horaire(String code, String jour,String heur,ArrayList<Enseignant> EnsDispo) {
		this.code = code;
		this.jour = jour;
		this.heur=heur;
		this.EnsDispo=EnsDispo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public ArrayList<Enseignant> getEnsDispo() {
		return EnsDispo;
	}
	public void setEnsDispo(ArrayList<Enseignant> ensDispo) {
		EnsDispo = ensDispo;
	}
	public String getJour() {
		return jour;
	}
	public void setJour(String jour) {
		this.jour = jour;
	}
	public String getHeur() {
		return heur;
	}
	public void setHeur(String heur) {
		this.heur = heur;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Horaire other = (Horaire) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}


	
	
	
	
}
