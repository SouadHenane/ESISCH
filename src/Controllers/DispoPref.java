package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.ResourceBundle;

import DataBaseConnectors.ConnectionDB;
import animations.FadeInLeftTransition;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static Controllers.GRHcontroller.obj;

public class DispoPref implements Initializable {
	 public CheckBox DDiAM ;
	 public CheckBox DMeAM;
	 public CheckBox DMaPM ;
	 public CheckBox DMaAM ;
	 public CheckBox DLuPM ;
	 public CheckBox DLuAM ;
	 public CheckBox DDiPM ;
	 public CheckBox DJePM ;
	 public CheckBox DJeAM;
	 public CheckBox DMePM ;
	 public CheckBox DSaPM; 
	 public CheckBox DSaAM; 
	 public CheckBox DVePM; 
	 public CheckBox DVeAM;
     
	 public CheckBox PDiAM ;
	 public CheckBox PMeAM;
	 public CheckBox PMaPM ;
	 public CheckBox PMaAM ;
	 public CheckBox PLuPM ;
	 public CheckBox PLuAM ;
	 public CheckBox PDiPM ;
	 public CheckBox PJePM ;
	 public CheckBox PJeAM;
	 public CheckBox PMePM ;
	 public CheckBox PSaPM; 
	 public CheckBox PSaAM; 
	 public CheckBox PVePM; 
	 public CheckBox PVeAM;
    public Label error;
	public AnchorPane AP;

	Connection con ;
	PreparedStatement pst ;
	 
	 String dispo = "";
	 String pref = "";
     
     public void Valider (ActionEvent actionEvent) throws SQLException, IOException{
    	
    	int i=0;
    	 if (DMaAM.isSelected()){
    		 dispo = dispo + "MaAM";
    		 i++;
    	 }
    	 
    	 if (DMaPM.isSelected()){
    		 dispo = dispo + "MaPM";
    		 i++;
    	 }
    	 if (DMeAM.isSelected()){
    		 dispo = dispo + "MeAM";
    		 i++;
    	 }
    	 if (DMePM.isSelected()){
    		 dispo = dispo + "MePM";
    		 i++;
    	 }
    	 if (DDiAM.isSelected()){
    		 dispo = dispo + "DiAM";
    		 i++;
    	 }
    	 if (DDiPM.isSelected()){
    		 dispo = dispo + "DiPM";
    		 i++;
    	 }
    	 if (DLuAM.isSelected()){
    		 dispo = dispo + "LuAM";
    		 i++;
    	 }
    	 if (DLuPM.isSelected()){
    		 dispo = dispo + "LuPM";
    		 i++;
    	 }
    	 if (DJeAM.isSelected()){
    		 dispo = dispo + "JeAM";
    		 i++;
    	 }
    	 if (DJePM.isSelected()){
    		 dispo = dispo + "JePM";
    		 i++;
    	 }
    	 if (DVeAM.isSelected()){
    		 dispo = dispo + "VeAM";
    		 i++;
    	 }
    	 if (DVePM.isSelected()){
    		 dispo = dispo + "VePM";
    		 i++;
    	 }
    	 if (DSaAM.isSelected()){
    		 dispo = dispo + "SaAM";
    		 i++;
    	 }
    	 if (DSaPM.isSelected()){
    		 dispo = dispo + "SaPM";
    		 i++;
    	 }
     	
    	 
    	  	
        	
        	 if (PMaAM.isSelected()){
        		 pref=pref + "MaAM";
        	
        	 }
        	 if (PMaPM.isSelected()){
        		 pref=pref + "MaPM";
        		 
        	 }
        	 if (PMeAM.isSelected()){
        		 pref=pref + "MeAM";
        		 
        	 }
        	 if (PMePM.isSelected()){
        		 pref=pref+ "MePM";
        		 
        	 }
        	 if (PDiAM.isSelected()){
        		 pref=pref + "DiAM";
        		
        	 }
        	 if (PDiPM.isSelected()){
        		 pref=pref + "DiPM";
        		 
        	 }
        	 if (PLuAM.isSelected()){
        		 pref=pref+ "LuAM";
        		 
        	 }
        	 if (PLuPM.isSelected()){
        		 pref=pref + "LuPM";
        		
        	 }
        	 if (PJeAM.isSelected()){
        		 pref=pref+ "JeAM";
        		 
        	 }
        	 if (PJePM.isSelected()){
        		 pref=pref + "JePM";
        		
        	 }
        	 if (PVeAM.isSelected()){
        		 pref=pref + "VeAM";
        		 
        	 }
        	 if (PVePM.isSelected()){
        		 pref=pref + "VePM";
        		 
        	 }
        	 if (PSaAM.isSelected()){
        		 pref=pref+ "SaAM";
        		 
        	 }
        	 if (PSaPM.isSelected()){
        		 pref = pref+ "SaPM";
        	 }
        	 
        	  add();
        	  
     }
     public void add () throws SQLException{
		 if (!(dispo=="")){
			 ArrayList<String> l = new ArrayList<String>();
			 l.addAll((Collection<? extends String>) obj);
			 String Query = "update Enseignant set HoraireDispo='"+dispo+"', HorairePré='" + pref+"' where ID_Enseignant='"+l.get(0)+"'";
			 con = ConnectionDB.CDB();
			 pst = con.prepareStatement(Query);
			 pst.execute();
			 AP.getChildren().clear();
			 Label label = new Label();
			 label.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/img/Opdone"))));
			 AP.getChildren().addAll(label);
			 label.setLayoutY(175);
			 label.setLayoutX(190);
		 } else {
			 error.setText("Cochez au moins une heure de disponibilité");
			 error.setStyle("-fx-text-fill: #ed0202;");
		 }
	 }
	public void animate(Node node ){
		FadeInLeftTransition fade = new FadeInLeftTransition(node);
		fade.play();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		animate(AP);
	}
}
