package Controllers;

import DataBaseConnectors.ConnectionDB;
import animations.FadeInLeftTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class Sallecon implements Initializable {
    public Label error;
    Connection con ;
    PreparedStatement pst ;
    public TextField Desig;
    public ComboBox type;
    public TextField Capacite;



    public Boolean empty (TextField txt){
        if (!(txt.getText().trim().isEmpty())) return true ;
        else return false ;
    }
    public void animate(Node e){
        FadeInLeftTransition fade = new FadeInLeftTransition(e);
        fade.play();
    }


    public void valider(ActionEvent actionEvent) {
        String Query = "insert into Salle (Nom_Salle,Type,Capacite) values (?,?,?)";
        if (empty(Desig) && empty(Capacite)){
            try {
                pst = con.prepareStatement(Query);
                pst.setString(1,Desig.getText().toUpperCase());
                pst.setString(3,Capacite.getText());
                pst.setString(2,type.getSelectionModel().getSelectedItem().toString());
                pst.execute();
            } catch (SQLException e) {
                error.setText("Cette salle existe deja !! ");
                error.setStyle("-fx-text-fill: #ed0202;");
            }

            Desig.clear();
            Capacite.clear();
            type.getItems().clear();
            type.getItems().addAll("AMPHI","TD","TP");

        } else {
            error.setText("Il y'a quelque chose qui manque !");
            error.setStyle("-fx-text-fill: #ed0202;");
        }

    }

    public void vider(ActionEvent actionEvent) {
        Desig.clear();Capacite.clear();
        type.getItems().clear();
        type.getItems().addAll("AMPHI","TD","TP");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        animate(Desig);
        animate(type);
        animate(Capacite);
        con = ConnectionDB.CDB();
        type.getItems().addAll("AMPHI","TD","TP");
        Capacite.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    Capacite.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        Desig.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (Desig.getText().length() > 1) {
                    String s = Desig.getText().substring(0,3);
                    Desig.setText(s);
                }
            }
        });


    }
}
