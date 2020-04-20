package Controllers;

import DataBaseConnectors.ConnectionDB;
import animations.FadeInTransition;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
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


public class Updateprof implements Initializable {
    public AnchorPane AP;
    Connection con ;
    PreparedStatement pst ;
    ResultSet rs ;

    public TextField ID;
    public TextField Nom;
    public TextField Prenom;
    public TextField email;
    String txt ;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ID.setText(selectedID2);
        ID.setEditable(false);
        try {
            Nom.setText((Get("Nom_Enseigant")).toString());
            Prenom.setText((Get("Prenom_Enseignant")).toString());
            email.setText((Get("Email_Ensignant")).toString());
        } catch (Exception e ){
            System.out.println(e.getMessage());
        }
    }

    public void Update(ActionEvent actionEvent)  {
        String Query  = "update Enseignant set Nom_Enseigant=?,Prenom_Enseignant=?,Email_Ensignant=? where ID_Enseignant=?";
        con  = ConnectionDB.CDB();
        try {
            pst = con.prepareStatement(Query);
            pst.setString(1,Nom.getText().toString());
            pst.setString(2,Prenom.getText().toString());
            pst.setString(3,email.getText().toString());
            pst.setString(4,ID.getText().toString());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AP.getChildren().clear();
        Label label = new Label();
        label.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/img/Opdone"))));
        AP.getChildren().addAll(label);
        label.setLayoutY(175);
        label.setLayoutX(190);
    }

    


    public String Get (String thiis) throws SQLException {
        String Query = "Select "+thiis+" from Enseignant where ID_Enseignant= "+"'"+selectedID2+"'";
        con = ConnectionDB.CDB();
        pst = con.prepareStatement(Query);
        rs = pst.executeQuery();
        while (rs.next()){
             txt =  rs.getString(1).toString();
        }
        return txt ;
    }

    public void Vider(ActionEvent actionEvent) {
        Nom.clear();
        Prenom.clear();
        email.clear();
    }
}
