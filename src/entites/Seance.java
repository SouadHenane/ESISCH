package entites;

import javafx.geometry.Side;

public class Seance implements Comparable<Seance>{
	private int code ;
	private Section section ;
	private Groupe groupe;
	private Module module;
	private Enseignant enseignant;
	private Horaire horaire;
	private Salle salle;
	public int num;
	public Seance(int seanceNum,Section section,Module module) {
		this.code = seanceNum;
		this.section=section;
		this.module = module;
	}
	
	public Seance(int seanceNum,Groupe groupe,Module module) {
		this.code = seanceNum;
		this.groupe=groupe;
		this.module = module;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Section getSection() {
		return section;
	}
	public void setSection(Section section) {
		this.section = section;
	}
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public Enseignant getEnseignant() {
		return enseignant;
	}
	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}
	public Horaire getHoraire() {
		return horaire;
	}
	public void setHoraire(Horaire horaire) {
		this.horaire = horaire;
	}
	public Salle getSalle() {
		return salle;
	}
	public void setSalle(Salle salle) {
		this.salle = salle;
	}
	
	public Groupe getGroupe() {
		return groupe;
	}
	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}
	@Override
	public String toString() {
		String a;
		if (section == null)
			a = groupe.getId();
		else {
			a = section.getNom();
		}
		return horaire.getJour()+"("+horaire.getHeur()+")"+a+ "," + module.getNom()+","+ enseignant.getNom()+","+salle.getCode()+"("+salle.getCapacité()+")";
	}

	public String toString2() {
		String a;
		if (section == null)
			a = groupe.getId();
		else {
			a = section.getNom();
		}
		if (module.getType().equals("Cours")){
			return module.getType()+" ("+a+") "+ module.getNom()+" \n"+ enseignant.getNom()+" "+salle.getCode();
		}
		return module.getType()+" ("+a+") "+ module.getNom()+" "+ enseignant.getNom()+" "+salle.getCode();
	}

	@Override
	public int compareTo(Seance o) {
		// TODO Auto-generated method stub
		
		
		return 0;
	}
	
	
	
}
