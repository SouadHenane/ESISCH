package emploiDuTemps;

import java.util.ArrayList;
import java.util.stream.IntStream;


public class Population {
	private ArrayList<Tableau> tableaux;
	public Population(int taille, Entree entree){
		tableaux = new ArrayList<Tableau>(taille);
		IntStream.range(0, taille).forEach(x -> tableaux.add(new Tableau(entree).initialize()));
		}
	public ArrayList<Tableau> getTableaux() { return this.tableaux;}
	public Population trie(){
		
		tableaux.sort((tableau1, tableau2) -> {
			int v = 0 ;
		
			if (tableau1 != null && tableau2 != null){
			
			if (tableau1.getFitness() > tableau2.getFitness()) v = -1;
			else if (tableau1.getFitness() < tableau2.getFitness()) v = 1;
			else {if (tableau1.getNumCS() > tableau2.getNumCS()) v = -1;
			else if (tableau1.getNumCS() < tableau2.getNumCS()) v=1;}
			}
			return v;
			
		});
		return this;
	}
	public void setTableaux(ArrayList<Tableau> tableaux) {
		this.tableaux = tableaux;
	}
}
