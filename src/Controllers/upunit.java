package Controllers;

import DataBaseConnectors.ConnectionDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
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
import java.util.function.Predicate;

import static Controllers.GRHcontroller.selectedID2;

public class upunit implements Initializable{
    public TextField module;
    public TextField coef;
    public ComboBox mpromo;
    public ComboBox semestre;
    public CheckBox cours;
    public CheckBox TD;
    public CheckBox TP;
    public AnchorPane AP;
    public Label error;
    Connection con ;
    PreparedStatement pst ;
    ResultSet rs ;
    String txt ;
    String type ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        coef.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    coef.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        try {
            module.setText(Get("Nom_Module"));
            coef.setText(Get("Coefficient"));
            mpromo.getItems().addAll("1CPI","2CPI","1CS","2CS","3CS");
            mpromo.setValue(Get("Promo"));
            semestre.getItems().addAll("1","2");
            semestre.setValue(Get("Semestre"));
            type = Get("Type");
            if (type.length()==9) {
                cours.setSelected(true);
                TD.setSelected(true);
                TP.setSelected(true);
            } else if (type.length()==5){
                cours.setSelected(true);
            } else if (type.length()==4){
                TP.setSelected(true);
                TD.setSelected(true);
            } else if (type.length()==7){
                cours.setSelected(true);
                if (type.substring(6).equals("P")) TP.setSelected(true);
                if (type.substring(6).equals("D")) TD.setSelected(true);
            } else if (type.length()==2){
                if (type.substring(1).equals("P")) TP.setSelected(true);
                if (type.substring(1).equals("D")) TD.setSelected(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    public String Get (String thiis) throws SQLException {
        String Query = "Select "+thiis+" from Module where ID_Module= "+"'"+selectedID2+"'";
        con = ConnectionDB.CDB();
        pst = con.prepareStatement(Query);
        rs = pst.executeQuery();
        while (rs.next()){
            txt =  rs.getString(1).toString();
        }
        return txt ;
    }

    public void Valider(ActionEvent event) {
        String typemodule = "";
        if (cours.isSelected()) typemodule = typemodule + "cours";
        if (TD.isSelected()) typemodule = typemodule +"TD";
        if (TP.isSelected()) typemodule = typemodule +"TP";
        if (!module.getText().trim().isEmpty() && !coef.getText().trim().isEmpty() && typemodule!=""){
            String Query  = "update Module set Nom_Module=?,Coefficient=?,Type=?,Promo=?,Semestre=? where ID_Module='"+selectedID2+"'";
            con  = ConnectionDB.CDB();
            try {
                pst = con.prepareStatement(Query);
                pst.setString(1,module.getText().toString());
                pst.setString(2,coef.getText().toString());
                pst.setString(3,typemodule);
                pst.setString(4,mpromo.getSelectionModel().getSelectedItem().toString());
                pst.setString(5,semestre.getSelectionModel().getSelectedItem().toString());
                pst.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                error.setText("Module existe deja !! ");
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

    public void Vider(ActionEvent event) {
        module.clear();
        coef.clear();
        mpromo.getItems().clear();
        semestre.getItems().clear();
        mpromo.getItems().addAll("1CPI","2CPI","1CS","2CS","3CS");
        semestre.getItems().addAll("1","2");
        cours.setSelected(false);
        TD.setSelected(false);
        TP.setSelected(false);
    }
}
