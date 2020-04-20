package Controllers;

import DataBaseConnectors.ConnectionDB;
import animations.FadeInTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Controllers.GRHcontroller.selectedID2;

public class upsalle implements Initializable {
    public TextField Desig;
    public TextField Capacite;
    public ComboBox type;
    public Label error;
    public AnchorPane AP;
    Connection con ;
    PreparedStatement pst ;
    ResultSet rs ;
    String txt ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        try {
            Desig.setText(Get("Nom_Salle"));
            Capacite.setText(Get("Capacite"));
            type.getItems().addAll("AMPHI","TD","TP");
            type.setValue(Get("Type"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public String Get (String thiis) throws SQLException {
        String Query = "Select "+thiis+" from Salle where ID_Salle= "+"'"+selectedID2+"'";
        con = ConnectionDB.CDB();
        pst = con.prepareStatement(Query);
        rs = pst.executeQuery();
        while (rs.next()){
            txt =  rs.getString(1).toString();
        }
        return txt ;
    }
    public void animate(Node node ){
        FadeInTransition fade = new FadeInTransition(node);
        fade.play();
    }

    public void valider(ActionEvent event) {
        if (!Desig.getText().trim().isEmpty() && !Capacite.getText().trim().isEmpty() ){
            String Query  = "update Salle set Nom_Salle=?,Capacite=?,Type=? where ID_Salle=?";
            con  = ConnectionDB.CDB();
            try {
                pst = con.prepareStatement(Query);
                pst.setString(1,Desig.getText().toString());
                pst.setString(2,Capacite.getText().toString());
                pst.setString(3,type.getSelectionModel().getSelectedItem().toString());
                pst.setString(4,selectedID2);
                pst.execute();
            } catch (SQLException e){
                error.setText("Cette salle existe deja !! ");
                error.setStyle("-fx-text-fill: #ed0202;");
            }
            AP.getChildren().clear();
            Label label = new Label();
            label.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/img/Opdone"))));
            AP.getChildren().addAll(label);
            label.setLayoutY(175);
            label.setLayoutX(190);
        }else {
            error.setText("Il y'a quelque chose qui manque ! ");
            error.setStyle("-fx-text-fill: #ed0202;");
        }
    }
    public void vider(ActionEvent event) {
        Desig.clear();
        Capacite.clear();
        type.getItems().addAll("AMPHI","TD","TP");
        error.setText("");

    }
}
