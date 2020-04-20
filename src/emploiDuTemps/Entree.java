package emploiDuTemps;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import entites.*;

public class Entree {
	private ArrayList<Salle> sallesTD;
	private ArrayList<Salle> sallesTP;
	private ArrayList<Salle> Amphis;
	private HashSet<Enseignant> enseignants;
	private ArrayList<Module> modules;
	private ArrayList<Section> sections;
	public ArrayList<Horaire> horaires;
	public ArrayList<Horaire> horairesM;
	public ArrayList<Horaire> horairesD;
	public ArrayList<Horaire> horairesA;
	
	private int numSeances = 0;
	public Entree() throws SQLException{ initilise();}
	private Entree initilise() throws SQLException{
	input in = new input();
	this.horairesM= input.horairesM;
	this.horairesA= input.horairesA;
	this.horaires=input.horaires;
	this.sallesTD=input.sallesTD;
	this.sallesTP=input.sallesTP;
	this.Amphis=input.Amphis;
	this.enseignants=input.enseignants;
	this.sections=input.sections;
	this.modules=input.modules;
		return this;
	}

	public ArrayList<Salle> getSallesTP() {
		return sallesTP;
	}
	public ArrayList<Salle> getSallesTD() {
		return sallesTD;
	}
	public ArrayList<Salle> getAmphis() {
		return Amphis;
	}

	public HashSet<Enseignant> getEnseignants() {
		return enseignants;
	}

	public ArrayList<Module> getModules() {
		return modules;
	}

	public ArrayList<Section> getSections() {
		return sections;
	}

	public ArrayList<Horaire> getHoraires() {
		return horaires;
	}

	public int getNumSeances() {
		return numSeances;
	}
	
}
