package emploiDuTemps;

import java.util.ArrayList;
import java.util.stream.IntStream;


public class GA {
	private Entree entree;
	public GA (Entree entree) { this.entree = entree;}
	
	public Population envolve(Population population){return mutatePopulation(crossoverP(population));}
	
	Population crossoverP (Population population){
		Population crossoverP = new Population(population.getTableaux().size(), entree);
		IntStream.range(0, Main2.Num_Elite_Tab).forEach(x -> crossoverP.getTableaux().set(x, population.getTableaux().get(x)));
		IntStream.range(Main2.Num_Elite_Tab, population.getTableaux().size()).forEach(x -> {
			if (Main2.Taux_Crossover > Math.random()){
				Tableau tableau1 = selectTournoiP(population).trie().getTableaux().get(0);
				Tableau tableau2 = population.getTableaux().get(0);
				crossoverP.getTableaux().set(x, crossoverT(tableau1, tableau2));
				
			}else crossoverP.getTableaux().set(x, population.getTableaux().get(x));
		});
		return crossoverP;
	}
	Tableau crossoverT(Tableau tableau1,Tableau tableau2){
		Tableau crossoverT = new Tableau(entree).initialize();
		IntStream.range(0, crossoverT.getseances().size()).forEach(x -> {
			if (Math.random() > 0.5) crossoverT.getseances().set(x, tableau1.getseances().get(x));
			else crossoverT.getseances().set(x, tableau2.getseances().get(x));
		});
		return crossoverT;
	}
	Population mutatePopulation(Population population){
		Population mutateP = new Population(population.getTableaux().size(), entree);
		ArrayList<Tableau> tableaux = mutateP.getTableaux();
		IntStream.range(0, Main2.Num_Elite_Tab).forEach(x -> tableaux.set(x, population.getTableaux().get(x)));
		IntStream.range(Main2.Num_Elite_Tab, population.getTableaux().size()).forEach(x -> {
		tableaux.set(x, mutateTableau(population.getTableaux().get(x)));
		});
		mutateP.setTableaux(tableaux);
		return mutateP;
	}
	Tableau mutateTableau(Tableau mTableau){
		Tableau tableau = new Tableau(entree).initialize();
		IntStream.range(0, mTableau.getseances().size()).forEach(x -> {
			if (Main2.Taux_Mutation > Math.random()) tableau.getseances().set(x, mTableau.getseances().get(x));
		});
		return tableau;
	}
	Population selectTournoiP(Population population){
		Population tournoiP = new Population(Main2.Taille_Tournoi_Select, entree);
		IntStream.range(0, Main2.Taille_Tournoi_Select).forEach(x -> {
			tournoiP.getTableaux().set(x, population.getTableaux().get((int)(Math.random() * population.getTableaux().size())));
		});
		return tournoiP;
	}
}
