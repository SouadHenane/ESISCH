package emploiDuTemps;

import java.time.Year;
import java.util.ArrayList;

import javax.security.auth.x500.X500Principal;
import javax.xml.crypto.Data;

import entites.Enseignant;
import entites.Groupe;
import entites.Horaire;
import entites.Module;
import entites.Salle;
import entites.Section;
import entites.Seance;

public class Tableau {
	private ArrayList<Seance> seances;
	private double fitness;
	private boolean fitnessChange = true;
	private int SeanceNum = 0;
	private double numCNS;
	private double numCS;
	private Entree entree;
	public Tableau(Entree entree) {
		this.entree = entree;
		seances = new ArrayList<Seance>();
	}
	public Tableau initialize(){
		new ArrayList<Section>(entree.getSections()).forEach(sec -> {
			sec.getModules().forEach(module -> {
				if (module.getType()=="Cours"){
					Seance newSeance = new Seance(SeanceNum++,sec,module);
					
					newSeance.setSalle(entree.getAmphis().get((int) (entree.getAmphis().size() * Math.random())));
					//Enseignant c  = newSeance.getModule().getChargeCours().get((int) (newSeance.getModule().getChargeCours().size() * Math.random()));
					//newSeance.setEnseignant(c);
					Horaire h = entree.horairesM.get((int) (entree.horairesM.size() * Math.random()));
					boolean test = true;
					boolean test1 = false;
					while (test){
						
						//System.out.println("boucle");
						for (Enseignant enseignant : module.getChargeCours())
							if (h.getEnsDispo().contains(enseignant)){
								
								test1= true;
								break;
							}
						
						if (test1){
							Enseignant e = module.getChargeCours().get((int) (module.getChargeCours().size() * Math.random()));
							while (!h.getEnsDispo().contains(e)){
								
							//	System.out.println("************************************");
								e = module.getChargeCours().get((int) (module.getChargeCours().size() * Math.random()));
							}
								newSeance.setEnseignant(e);
								test=false;
						}
						else
							h = entree.getHoraires().get((int) (entree.getHoraires().size() * Math.random()));
					}
					
					
			
					newSeance.setHoraire(h);
					
					seances.add(newSeance);
				}else if (module.getType()=="TD" || module.getType()=="TP"){
					
					for (Groupe g : sec.getGroupes()){
						Seance newSéance = new Seance(SeanceNum++,g,module);
						Horaire h = entree.getHoraires().get((int) (entree.getHoraires().size() * Math.random()));
						
						
					if (module.getType()=="TD")
						newSéance.setSalle(entree.getSallesTD().get((int) (entree.getSallesTD().size() * Math.random())));
					else 
						newSéance.setSalle(entree.getSallesTP().get((int) (entree.getSallesTP().size() * Math.random())));
						
						boolean test = true;
						boolean test1 = false;
						while (test){
							//System.out.println("boucle");
							for (Enseignant enseignant : module.getEnseignants())
								if (h.getEnsDispo().contains(enseignant)){
									test1= true;
									break;
								}
							
							if (test1){
								Enseignant e = module.getEnseignants().get((int) (module.getEnseignants().size() * Math.random()));
								while (!h.getEnsDispo().contains(e)){
								//	System.out.println("************************************");
									e = module.getEnseignants().get((int) (module.getEnseignants().size() * Math.random()));
								}
									newSéance.setEnseignant(e);
									test=false;
							}
							else
								h = entree.getHoraires().get((int) (entree.getHoraires().size() * Math.random()));
						}
						newSéance.setHoraire(h);
						seances.add(newSéance);	
					}}else {
						Seance newSeance = new Seance(SeanceNum++,sec,module);
						Horaire h = entree.horaires.get((int) (entree.horaires.size() * Math.random()));
						newSeance.setHoraire(h);
						newSeance.setEnseignant(new Enseignant("", ""));
						newSeance.setSalle(new Salle("", "", 0, ""));
						seances.add(newSeance);	
					}
				
					
					
			});
		});
		return this;
	}
	private double calculateFitness(){
		numCNS = 0;
		numCS=0;
		seances.forEach(x -> {
			 
			//if (!x.getSalle().getType().equals(x.getModule().getType())) numCNS++;
			//if (!x.getHoraire().getEnsDispo().contains(x.getEnseignant())) numCNS++;
			seances.stream().filter(y -> seances.indexOf(y) >= seances.indexOf(x)).forEach(y -> {
				if (x.getHoraire().equals(y.getHoraire()) && x.getCode() != y.getCode()){
					if (x.getSalle() == y.getSalle()) numCNS ++;
					if (x.getEnseignant() == y.getEnseignant()) numCNS++;
					if(x.getSection()!=null && y.getSection()!=null ){
					 if (x.getSection() == y.getSection()) numCNS++;
					} else if(x.getGroupe()!=null && y.getGroupe()!=null){
						if (x.getGroupe() == y.getGroupe()) numCNS++;
					}else 
						if(x.getSection()!=null){
							if (x.getSection().getGroupes().contains(y.getGroupe())) numCNS++;
						}else {
							if (y.getSection().getGroupes().contains(x.getGroupe())) numCNS++;
						}
					
				}else if (x.getHoraire()!=y.getHoraire() && x.getHoraire().getJour().equals(y.getHoraire().getJour()) && x.getModule().getType().equals("Cours") && y.getModule().getType().equals("Cours") ){
					if (x.getModule().getCoef()<3 && y.getModule().getCoef()>=3) numCS++;
					else if (x.getModule().getCoef()<3 && y.getModule().getCoef()>=3) numCS++;
				}
			});
			if ((x.getHoraire().getHeur().startsWith("8") || x.getHoraire().getHeur().startsWith("10")) && x.getModule().getType().equals("Cours")){
				numCS++;
			}
			if (x.getEnseignant().getPreferences()==null) numCS++ ;
			else
			if ((x.getEnseignant().getPreferences().contains(x.getHoraire()))) numCS++;
		});
		return 1/(double) (numCNS + 1);
	}
	public double getNumCNS(){
		return numCNS;
	}
	public ArrayList<Seance> getseances(){
		fitnessChange = true;
		return seances;
	}
	public double getFitness(){
		if (fitnessChange){
			fitness = calculateFitness();
			fitnessChange=false;
		}
		return fitness;
	}

	public double getNumCS() {
		return numCS;
	}
	public void setNumCS(double numCS) {
		this.numCS = numCS;
	}
	public String toString(){
		String string="";
		for (int i = 0 ; i < seances.size()-1 ; i++) string +="["+ seances.get(i)+ "]";
		string += seances.get(seances.size()-1);
		return string;
		
				
	}
}

