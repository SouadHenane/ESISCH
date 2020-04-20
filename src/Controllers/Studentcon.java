package Controllers;

import DataBaseConnectors.ConnectionDB;
import animations.FadeInLeftTransition;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static Controllers.GRHcontroller.*;

public class Studentcon implements Initializable{

    public Label error;
    Connection con ;
    PreparedStatement pst ;
    ResultSet rs ;
    String Id;

    public TextField ID;
    public TextField nom;
    public TextField prenom;
    public TextField email;
    public DatePicker Bday;
    public ComboBox groups;

    public Boolean empty (TextField txt){
        if (!(txt.getText().trim().isEmpty())) return true ;
        else return false ;
    }

    public void valider(ActionEvent actionEvent) throws SQLException {
        if(empty(ID) && empty(nom) && empty(prenom) && empty(email) && !(Bday.getValue()==null)){
            error.setText("");
            String Query = "select ID_Groupe from Groupe natural join Section where Nom_Groupe =? and Promo = ? and Nom_Section=?";
            pst = con.prepareStatement(Query);
            System.out.println("Group : "+groups.getSelectionModel().getSelectedItem().toString());
            System.out.println("promo : "+ promo2);
            System.out.println("Section :"+section);
            pst.setString(1,groups.getSelectionModel().getSelectedItem().toString());
            pst.setString(2,promo2);
            pst.setString(3,section);
            rs = pst.executeQuery();
            while (rs.next()){
                Id = rs.getString(1);
                System.out.println(ID);
            }
            String Q = "INSERT INTO Etudiant values (?,?,?,?,?,?,?)";
            pst = con.prepareStatement(Q);
            pst.setString(1,ID.getText().toString());
            pst.setString(2,nom.getText().toString());
            pst.setString(3,prenom.getText().toString());
            java.util.Date date =
                    java.util.Date.from(Bday.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            pst.setString(4, String.valueOf(sqlDate));
            pst.setString(5,email.getText().toString());
            pst.setString(6,Id);
            pst.setString(7,promo2);
            pst.execute();
            System.out.println("good it s done ");
            clearall();

        } else {
            error.setText("Il y'a quelque chose qui manque !! ");
            error.setStyle("-fx-text-fill: #ed0202;");
        }
    }
    public void clearall(){
        ID.clear();
        nom.clear();
        prenom.clear();
        email.clear();
        Bday.setValue(null);
    }
    public void vider(ActionEvent actionEvent) {
        clearall();
    }

    public void animate(Node e){
        FadeInLeftTransition fade = new FadeInLeftTransition(e);
        fade.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        animate(ID);
        animate(nom);
        animate(prenom);
        animate(email);
        animate(Bday);
        animate(groups);
        /****************************
         * populate groupcomboBox
         ****************************/
        con = ConnectionDB.CDB();
        ArrayList<String> list = new ArrayList<String>();
        for (int i=1; i <=groupnbr; i++){
            list.add("G"+i);
        }
        groups.getItems().addAll(list);




    }
}
