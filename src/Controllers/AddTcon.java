package Controllers;

import DataBaseConnectors.ConnectionDB;
import animations.FadeInLeftTransition;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddTcon implements Initializable {
    public Label error;
    Connection con ;
    PreparedStatement pst;
    public TextField ID;
    public TextField Nom;
    public TextField Prenom;
    public TextField email;

    public Boolean empty (TextField txt){
        if (!(txt.getText().trim().isEmpty())) return true ;
        else return false ;
    }

    public void Vlider(ActionEvent actionEvent) throws SQLException {
        String Query = "INSERT INTO Enseignant (ID_Enseignant,Nom_Enseigant,Prenom_Enseignant,Email_Ensignant) values (?,?,?,?)";
        if (empty(ID)&& empty(Nom) && empty(Prenom) && empty(email)) {
            pst = con.prepareStatement(Query);
            pst.setString(1,ID.getText());
            pst.setString(2,Nom.getText());
            pst.setString(3,Prenom.getText());
            pst.setString(4,email.getText());
            pst.execute();
            System.out.println("prof Added");
            ID.clear();Nom.clear();Prenom.clear();email.clear();
        } else {
            error.setText("Il y'a quelque chose qui manque !! ");
            error.setStyle("-fx-text-fill: #ed0202;");
        }
    }

    public void Vider(ActionEvent actionEvent) {
        ID.clear();Nom.clear();Prenom.clear();email.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeInLeftTransition fade = new FadeInLeftTransition(ID);
        fade.play();
        FadeInLeftTransition fade2 = new FadeInLeftTransition(Nom);
        fade2.play();
        FadeInLeftTransition fade3 = new FadeInLeftTransition(Prenom);
        fade3.play();
        FadeInLeftTransition fade4 = new FadeInLeftTransition(email);
        fade4.play();
        con = ConnectionDB.CDB();
    }
}
