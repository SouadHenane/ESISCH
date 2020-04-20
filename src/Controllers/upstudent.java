package Controllers;

import DataBaseConnectors.ConnectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.time.ZoneId;
import java.util.ResourceBundle;

import static Controllers.GRHcontroller.*;

public class upstudent implements Initializable {
    public TextField ID;
    public TextField nom;
    public TextField prenom;
    public TextField email;
    public DatePicker Bday;
    public Label error;
    public AnchorPane AP;
    Connection con ;
    PreparedStatement pst ;
    ResultSet rs ;
    String txt ;
    Date date ;

    public void valider(ActionEvent event) throws SQLException {
        if (!nom.getText().trim().isEmpty() && !prenom.getText().trim().isEmpty() && !email.getText().trim().isEmpty() && (Bday.getValue()!=null)){
            String Query  = "update Etudiant set Nom_Etudiant=?,Prenom_Etudiant=?,Email_Etudiant=?,DateDeNaissance=? where ID_Etudiant='"+selectedID2+"'";
            con  = ConnectionDB.CDB();
            try {
                pst = con.prepareStatement(Query);
                pst.setString(1,nom.getText().toString());
                pst.setString(2,prenom.getText().toString());
                pst.setString(3,email.getText().toString());
                java.util.Date date =
                        java.util.Date.from(Bday.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                pst.setString(4, String.valueOf(sqlDate));
                pst.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                error.setText("");
                error.setStyle("-fx-text-fill: #ed0202;");
            }

            AP.getChildren().clear();
            Label label = new Label();
            label.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/img/Opdone"))));
            AP.getChildren().addAll(label);
            label.setLayoutY(175);
            label.setLayoutX(190);
        } else {
            error.setText("il y'a un champ qui manque!! ");
            error.setStyle("-fx-text-fill: #ed0202;");
        }
    }

    public void vider(ActionEvent event) {
        nom.clear();
        prenom.clear();
        email.clear();
        Bday.setValue(null);
    }


    public String Get (String thiis) throws SQLException {
        String Query = "Select "+thiis+" from Etudiant where ID_Etudiant= "+"'"+selectedID2+"'";
        con = ConnectionDB.CDB();
        pst = con.prepareStatement(Query);
        rs = pst.executeQuery();
        while (rs.next()){
            txt =  rs.getString(1).toString();
        }
        return txt ;
    }
    public java.sql.Date bday () throws SQLException  {
        String Query = "Select DateDeNaissance from Etudiant where ID_Etudiant= "+"'"+selectedID2+"'";
        con = ConnectionDB.CDB();
        pst = con.prepareStatement(Query);
        rs = pst.executeQuery();
        while (rs.next()){
            try {
                date =  rs.getDate(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return  date ;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ID.setText(Get("ID_Etudiant"));
            ID.setEditable(false);
            nom.setText(Get("Nom_Etudiant"));
            prenom.setText(Get("Prenom_Etudiant"));
            email.setText(Get("Email_Etudiant"));
            Bday.setValue(bday().toLocalDate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
