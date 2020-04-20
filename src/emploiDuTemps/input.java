package emploiDuTemps;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import Controllers.first;
import entites.Enseignant;
import entites.Groupe;
import entites.Horaire;
import entites.Module;
import entites.Salle;
import entites.Section;
import entree.Donnée;

public class input {

		public static ArrayList<Salle> sallesTD = new ArrayList<>();
		public static ArrayList<Salle> sallesTP = new ArrayList<>();
		public static ArrayList<Salle> Amphis = new ArrayList<>();
		public static HashSet<Enseignant> enseignants = new HashSet<>();
		public static ArrayList<Module> modules= new ArrayList<>();
		public static ArrayList<Section> sections= new ArrayList<>();
		public static ArrayList<Horaire> horaires= new ArrayList<>();
		public static ArrayList<Horaire> horairesM= new ArrayList<>();
		public static ArrayList<Horaire> horairesA= new ArrayList<>();

		public  input() throws SQLException { initilise(); }
		private input initilise() throws SQLException{
		Donnée donnée = new Donnée();
		donnée.extraire();
		ArrayList<Enseignant> list;
		if (first.jours.contains("Dimanche")){
		list = DispoListe("DiPM");
		horairesM.add(new Horaire("Di1", "Dimanche","8:10",list));
		horairesM.add(new Horaire("Di2", "Dimanche","10:12",list));
		horaires.add(new Horaire("Di1", "Dimanche","8:10",list));
		horaires.add(new Horaire("Di2", "Dimanche","10:12",list));
		list = DispoListe("DiAM");
			horaires.add(new Horaire("Di3", "Dimanche","13:15",list));
			horaires.add(new Horaire("Di4", "Dimanche","15:17",list));
		
			//horaires.add( new Horaire("Di5", "Dimanche", "14:16",new ArrayList<Enseignant>(input.enseignants)));
		
		}
		if (first.jours.contains("Lundi")){
			list = DispoListe("LuAM");
			horaires.add( new Horaire("Lu1", "Lundi", "8:10",list));
			horaires.add( new Horaire("Lu2", "Lundi","10:12",list));

			horairesM.add( new Horaire("Lu1", "Lundi", "8:10",list));
			horairesM.add( new Horaire("Lu2", "Lundi","10:12",list));
			list = DispoListe("LuPM");
			horaires.add(new Horaire("Lu3", "Lundi", "13:15",list));
			horaires.add(new Horaire("Lu4", "Lundi", "15:17",list));
	
			//horaires.add(new Horaire("Lu5", "Lundi", "14:16",new ArrayList<Enseignant>(input.enseignants)));
		}
		if (first.jours.contains("Mardi")){
			list = DispoListe("MaAM");
			horaires.add(new Horaire("Ma1", "Mardi","8:10",list));
			horaires.add( new Horaire("Ma2", "Mardi", "10:12",list));
			horairesM.add(new Horaire("Ma1", "Mardi","8:10",list));
			horairesM.add( new Horaire("Ma2", "Mardi", "10:12",list));
			list = DispoListe("MaPM");
			horaires.add( new Horaire("Ma5", "Mardi", "14:16",list));
		}
		if (first.jours.contains("Jeudi")){
			list = DispoListe("JeAM");
			horaires.add( new Horaire("Je1", "Jeudi", "8:10",list));
			horaires.add( new Horaire("Je2", "Jeudi", "10:12",list));
			horairesM.add( new Horaire("Je1", "Jeudi", "8:10",list));
			horairesM.add( new Horaire("Je2", "Jeudi", "10:12",list));
		
			//	horaires.add( new Horaire("Je3", "Jeudi", "13:15",new ArrayList<Enseignant>(input.enseignants)));
			//	horaires.add( new Horaire("Je4", "Jeudi", "15:17",new ArrayList<Enseignant>(input.enseignants)));
			list = DispoListe("JePM");
				horaires.add( new Horaire("Je3", "Jeudi", "13:15",list));
				horaires.add( new Horaire("Je4", "Jeudi", "15:1",list));
		}
		if (first.jours.contains("Mercredi")){
			list = DispoListe("MeAM");
			horaires.add( new Horaire("Me1", "Mercredi", "10:12",list));
			horaires.add( new Horaire("Me2", "Mercredi", "8:10",list));
			horairesM.add( new Horaire("Me1", "Mercredi", "10:12",list));
			horairesM.add( new Horaire("Me2", "Mercredi", "8:10",list));	
			list = DispoListe("MePM");
				horaires.add( new Horaire("Me3", "Mercredi", "13:15",list));
				horaires.add( new Horaire("Me4", "Mercredi", "15:17",list));
			
				//horaires.add( new Horaire("Me5", "Mercredi", "14:16",new ArrayList<Enseignant>(input.enseignants)));
			
		}
		if (first.jours.contains("Vendredi")){
			list = DispoListe("JeAM");
			horaires.add( new Horaire("Ve2", "Vendredi", "10:12",list));
			horaires.add( new Horaire("Ve1", "Vendredi", "8:10",list));
			horairesM.add( new Horaire("Ve2", "Vendredi", "10:12",list));
			horairesM.add( new Horaire("Ve1", "Vendredi", "8:10",list));	
			//if (first.lunch.equals(" 12h - 13h")){	
			//	horaires.add( new Horaire("Ve3", "Vendredi", "13:15",new ArrayList<Enseignant>(input.enseignants)));
			//	horaires.add( new Horaire("Ve4", "Vendredi", "15:17",new ArrayList<Enseignant>(input.enseignants)));
			//}else
			list = DispoListe("MePM");
				horaires.add( new Horaire("Ve5", "Vendredi", "14:16",list));
			
		}
		if (first.jours.contains("Samedi")){
			list = DispoListe("SaAM");
			horaires.add( new Horaire("Sa1", "Samedi", "10:12",list));
			horaires.add( new Horaire("Sa2", "Samedi", "8:10",list));
			horairesM.add( new Horaire("Sa1", "Samedi", "10:12",list));
			horairesM.add( new Horaire("Sa2", "Samedi", "8:10",list));	
			//if (first.lunch.equals(" 12h - 13h")){	
			//	horaires.add( new Horaire("Sa3", "Samedi", "13:15",new ArrayList<Enseignant>(input.enseignants)));
			//	horaires.add( new Horaire("Sa4", "Samedi", "15:17",new ArrayList<Enseignant>(input.enseignants)));
			//}else
			list = DispoListe("SaPM");
				horaires.add( new Horaire("Sa5", "Samedi", "14:16",list));
			
		}
		
		return this;
		}
		
		public ArrayList<Enseignant> DispoListe(String s){
			ArrayList<Enseignant> l = new ArrayList<>();
			for (Enseignant e : input.enseignants){
				if (e.dispo.contains(s)){
					l.add(e);
				}
			}
			return l;
		}
}
