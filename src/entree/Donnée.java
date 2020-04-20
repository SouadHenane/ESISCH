package entree;

import Controllers.first;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.collections4.map.StaticBucketMap;

import com.mysql.fabric.xmlrpc.base.Array;


import emploiDuTemps.Entree;
import emploiDuTemps.input;
import entites.Enseignant;
import entites.Groupe;
import entites.Module;

public class Donnée {

	static Connection con;
	ArrayList<String> Salles = new ArrayList<String>(Arrays.asList("ID_Salle,Nom_Salle,Type,Capacite"));
	ArrayList<String> Sections = new ArrayList<String>(Arrays.asList("ID_Section,Nom_Section,Promo"));
	ArrayList<String> Groupes = new ArrayList<String>(Arrays.asList("ID_Groupe,Nom_Groupe,ID_Section,Promo"));
	ArrayList<String> Modules = new ArrayList<String>(Arrays.asList("ID_Module,Nom_Module,Coefficient,Semestre,Type,Promo,ChargéDeCour"));
	
public void extraire() throws SQLException {
	
	con = ConnectionDB.CDB();
  qSalles();
qModule(first.pro, Integer.parseInt(first.se)); // this must be an input
   qSection();



}


public void qSalles() throws SQLException{
	 Statement stmt = null;
    try {
    	 
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from Salle");
        while (rs.next()) {
            String id = rs.getString("ID_Salle");
            if (first.salleID.contains((String) id) ){
            String nom = rs.getString("Nom_Salle");
            String type = rs.getString("Type");
            int capacité = rs.getInt("Capacite");
            entites.Salle salle = new entites.Salle(id,nom,capacité, type);
            if (salle.getType().equals("TD")){
            	input.sallesTD.add(salle);
            }else if(salle.getType().equals("Amphi")){
            	input.Amphis.add(salle);
        }else input.sallesTP.add(salle);
            }
        } 
        
    } catch (SQLException e ) {
        e.printStackTrace();
    } finally {
        if (stmt != null) { stmt.close(); }
        
    }

}

public void qModule(String promo, int se) throws SQLException {
	 Statement stmt = null;
	    try { 
	        stmt = con.createStatement();
	      Statement  stmt1 = con.createStatement();
	        ResultSet rs3 = stmt.executeQuery("select * from module where Promo='"+promo+"' and Semestre="+se);
	        while (rs3.next()) {
	        	 String id = rs3.getString("ID_Module");
	             String nom = rs3.getString("Nom_Module");
	             String type = rs3.getString("Type");
	             int Coef = rs3.getInt("Coefficient");
	             ArrayList<Enseignant> chargé = enseignant(id,true);
	             ArrayList<Enseignant> en = enseignant(id,false);
	 	        
	 	      
	             
	             
	             if (type.contains("Cours")){
	             entites.Module module = new Module(id, nom, "Cours", en, chargé, Coef);
	             module.setType("Cours");
	             input.modules.add(module);
	             }
	             if (type.contains("TP")){
		             entites.Module module = new Module(id, nom, "TP", en, chargé, Coef);
		            
		             input.modules.add(module);
		             }
	             if (type.contains("TD")){
		             entites.Module module = new Module(id, nom, "TD", en,chargé, Coef);
		             
		             input.modules.add(module);
		             }
	             if (type.contains("Projet")) {
	            	 entites.Module module = new Module(id, nom, "Projet", en, chargé, Coef);
		             
		             input.modules.add(module);
	             }
	             
	             input.enseignants.addAll(en);
	             input.enseignants.addAll(chargé);
	            
	 	        }
	            
	             
	             
	    
	           
	        
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { stmt.close(); }
	        
	    }
	
}




public void qSection() throws SQLException{
	 Statement stmt = null;
	    try { 
	        stmt = con.createStatement();
	
	        ResultSet rs = stmt.executeQuery("select * from Section where Promo='"+first.pro+"'");
	        while (rs.next()) {
	            String id = rs.getString("ID_Section");
	            
	            String nom = rs.getString("Nom_Section");
	        
	            //String promo = rs.getString("Promo");
	            ArrayList<Groupe> gr = qGroupes(id);
	            input.sections.add(new entites.Section(nom, input.modules, gr));
	           
	        } 
	        
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { stmt.close(); }
	        
	    }
}
public ArrayList<Groupe> qGroupes(String code) throws SQLException {
	 Statement stmt = null;
	 ArrayList<Groupe> groupes = new ArrayList<>();
try {
	 
    stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery("select * from Groupe where Promo='"+first.pro+"' and ID_Section='"+code+"'");
    
    while (rs.next()) {
        String nom = rs.getString("Nom_Groupe");
        
        entites.Groupe groupe = new entites.Groupe(nom); 
        groupes.add(groupe);
    }    
  
} catch (SQLException e ) {
    e.printStackTrace();
} finally {
    if (stmt != null) { stmt.close(); } 
}
return groupes;

}

public ArrayList<Enseignant> enseignant(String code,boolean b) throws SQLException {
	 Statement stmt = null;
	 ArrayList<Enseignant> enseignants = new ArrayList<>();
try {
	 
   stmt = con.createStatement();
   ResultSet rs;
   if (b==false){
	   rs = stmt.executeQuery("select * from module_enseignant natural join enseignant where ID_Module='"+code+"'");
   }else{
	   rs = stmt.executeQuery("select * from module_enseignant natural join enseignant where ID_Module='"+code+"' and ChargéCours=1");   
   }
	   while (rs.next()) {
	   String id = rs.getString("ID_Enseignant");
       String nom = rs.getString("Nom_Enseigant"); 
       String prenom = rs.getString("Prenom_Enseignant"); 
       String dispo = rs.getString("HoraireDispo");
       String pref = rs.getString("HorairePré");
       entites.Enseignant ens = new entites.Enseignant(id,nom+" "+prenom.substring(0, 1)); 
       ens.dispo=dispo;
       ens.pref=pref;
       enseignants.add(ens);
   }    
} catch (SQLException e ) {
   e.printStackTrace();
} finally {
   if (stmt != null) { stmt.close(); } 
}
return enseignants;
}




}