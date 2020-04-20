package emploiDuTemps;

import java.io.IOException;
import java.security.acl.Group;
import java.sql.SQLException;
import java.util.ArrayList;

import Controllers.first;
import entites.Groupe;
import entites.Seance;
import entites.Section;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sortie.Affichage;
import sortie.Excel;

public class Main2 {

	public static final int Taille_Population = 100;
	public static final double Taux_Mutation = 0.9;
	public static final double Taux_Crossover = 0.2;
	public static final int Taille_Tournoi_Select = 30;
	public static final int Num_Elite_Tab = 10;
	//public static String[][] tab = new String[8][10];
	public static Population population;
	private Entree entree;
	public static Tableau emploi;
	
	public static ArrayList sep = new ArrayList<>();
	public static boolean main() throws SQLException {
	
		
	Main2 main = new Main2();
	
		first first = new first();
	
		
		int générations = 0; 
		main.entree = new Entree();
		GA ga = new GA(main.entree);
		System.out.println("Génération Num ");
		population = new Population(Main2.Taille_Population, main.entree).trie();
		
		
			//population.getTableaux().forEach(tableau -> System.out.println("##" + main.emploiNum++ + "|"+ tableau + "|"+ String.format("%.5f", tableau.getFitness()) + " | "+ tableau.getNumCNS()));
		générations = 1;
		
		
		while (population.getTableaux().get(0).getFitness()!=1){
			
			System.out.println("Génération Num "+ générations++);
			
			population = ga.envolve(population).trie();
			
			générations++;
			population.getTableaux().forEach(tableau -> System.out.println("##"+ "|"+ "|"+ String.format("%.5f", tableau.getFitness()) + " | "+ tableau.getNumCNS()+" | "+ tableau.getNumCS()));
			
	
				
			}
		emploi = population.getTableaux().get(0);
		 
		Main2.affichage();
		first.showTable();
		
	
		return true;


	}
	public static void affichage (){
		Tableau tableau = emploi;
		int i=0;
		int j=0;
		for (Section s : input.sections){
			String[][] tab = new String[8][10];
		
		for (int y=0 ; y<8 ; y++ ){
			for (int x= 0 ; x<10 ; x++){
				tab[y][x]="";
			}
		}
		tab[0][0]="(8h- 9h)";
		tab[0][1]="(9h- 10h)";
		tab[0][2]="(10h- 11h)";
		tab[0][3]="(11h- 12h)";
		tab[0][4]="(12h- 13h)";
		tab[0][5]="(13h- 14h)";
		tab[0][6]="(14h- 15h)";
		tab[0][7]="(15h- 16h)";
		tab[0][8]="(16h- 17h)";
		for( Seance seance : tableau.getseances() ){
			System.out.println(seance.toString2());
			if (seance.getHoraire().getJour().startsWith("Di")) j=1;
			if (seance.getHoraire().getJour().startsWith("Lu")) j=2;
			if (seance.getHoraire().getJour().startsWith("Ma")) j=3;
			if (seance.getHoraire().getJour().startsWith("Me")) j=4;
			if (seance.getHoraire().getJour().startsWith("Je")) j=5;
			if (seance.getHoraire().getJour().startsWith("Ve")) j=6;
			if (seance.getHoraire().getJour().startsWith("Sa")) j=7;
			if (seance.getHoraire().getHeur().endsWith(":10")) i=0;
			if (seance.getHoraire().getHeur().endsWith(":12")) i=2;
			if (seance.getHoraire().getHeur().endsWith(":15")) i=5;
			if (seance.getHoraire().getHeur().endsWith(":16")) i=6;
			if (seance.getHoraire().getHeur().endsWith(":17")) i=7;
		
				if (seance.toString2().contains("("+s.getNom()+")")){
					tab[j][i]=tab[j][i]+"\n"+seance.toString2();
					tab[j][i+1]=tab[j][i+1]+"\n"+seance.toString2();
				}else {
					for (Groupe g : s.getGroupes()){
						if (seance.toString2().contains("("+g.getId()+")")){
							tab[j][i]=tab[j][i]+"\n"+seance.toString2();
							tab[j][i+1]=tab[j][i+1]+"\n"+seance.toString2();
					}else {
						
					}
				}
				
			}
				
			//tab[j][i] = tab[j][i] +"\n"+seance.toString2();
			//tab[j][i+1] = tab[j][i+1] +"\n"+seance.toString2();
		}
		sep.add(tab);
		for (int yy=1 ; yy<8 ; yy++ ){
			System.out.println("");
			
			for (int xx= 0; xx<10 ; xx++){
			
			//	System.out.print("** "+tab [yy][xx]	) ;
				
			}
		}
		
		}
		
		//System.out.print("	                 Dimanche			|			Lundi			|			Mardi			|			Mercredi			|			Jeudi			| \n");
		
	
		int k = 0;
		for (Section section : input.sections){
		Excel excel = new Excel();
		String[] args = new String[0];
		try {
			Excel.main(args,(String[][]) sep.get(k++),"Section "+section.getNom());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
}