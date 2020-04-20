package Controllers;

import DataBaseConnectors.ConnectionDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static Controllers.GRHcontroller.promo;

public class popupcontroller implements Initializable {
    public  TextField Designiation;
    public  TreeItem<String> Node ;
    public TextField Studentnbr;
    public TextField groupsnbr;
    Connection con ;
    PreparedStatement pst ;
    String primaryKey ;
    PreparedStatement pst1 ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

            Designiation.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                    if (Designiation.getText().length() > 1) {
                        String s = Designiation.getText().substring(0, 1);
                        Designiation.setText(s);
                    }
                }
            });
        Studentnbr.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    Studentnbr.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        groupsnbr.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    groupsnbr.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });


    }




    public  void create(ActionEvent actionEvent) throws SQLException {
        con = ConnectionDB.CDB();
        String qr  = "insert into Section (Nom_Section,Nombre_etudiant_S,PROMO) values(?,?,?);";
        try {
            pst = con.prepareStatement(qr);
            pst.setString(1,Designiation.getText());
            pst.setString(2, String.valueOf(Integer.parseInt(Studentnbr.getText())));
            pst.setString(3,promo);
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String q = "SELECT LAST_INSERT_ID();" ;
        ResultSet rs = pst.executeQuery(q);
        while (rs.next()){
            System.out.println("This is the last Primary Key "+rs.getString(1));
            primaryKey = rs.getString(1);
        }



        int nbrgroup = Integer.parseInt(groupsnbr.getText().toString());
        System.out.println("this is the number of groups " + nbrgroup);
        String q2 = "INSERT INTO Groupe(Nom_Groupe,ID_Section,Promo) values (?,?,?)";
                for (int i = 0 ; i < nbrgroup ; i++){
                    int g = i+1;
                    String group = "G" + g  ;
                    System.out.println(group);
                    pst1 = con.prepareStatement(q2);
                    pst1.setString(1,group);
                    pst1.setString(2,primaryKey);
                    pst1.setString(3,promo);
                    pst1.execute();

                }

        ((Node)  (actionEvent.getSource())).getScene().getWindow().hide();

    }

    public void Cancel(ActionEvent actionEvent) {
        ((Node)  (actionEvent.getSource())).getScene().getWindow().hide();
    }


}
