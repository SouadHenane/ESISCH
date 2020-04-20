package Controllers;

import DataBaseConnectors.ConnectionDB;
import animations.FadeInLeftTransition;
import animations.FadeInLeftTransition1;
import animations.FadeInTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class addunit implements Initializable {

    public TextField module;
    public TextField coef;
    public ComboBox mpromo;
    public ComboBox semestre;
    public CheckBox cours;
    public CheckBox TD;
    public CheckBox TP;
    public AnchorPane AP;
    public Label error;
    String typemodule = "" ;
    Connection connection ;
    PreparedStatement pst ;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        animate(module);
        animate(coef);
        animate(mpromo);
        animate(semestre);
        animate(cours);
        animate(TD);
        animate(TP);
        coef.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    coef.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        connection = ConnectionDB.CDB();
        mpromo.getItems().addAll("1CPI","2CPI","1CS","2CS","3CS");
        semestre.getItems().addAll("1","2");



    }
    public void animate(Node node ){
        FadeInLeftTransition fade = new FadeInLeftTransition(node);
        fade.play();
    }


    public void Valider(ActionEvent actionEvent) {
        if (!(module.getText().trim().isEmpty()) && !(coef.getText().trim().isEmpty())) {
            error.setText("");
            if (cours.isSelected()) typemodule = typemodule + "cours";
            if (TD.isSelected()) typemodule = typemodule +"TD";
            if (TP.isSelected()) typemodule = typemodule +"TP";
            String Query = "INSERT INTO Module(Nom_Module,Coefficient,Type,Promo,Semestre) values (?,?,?,?,?)";
            try {
                pst = connection.prepareStatement(Query);
                pst.setString(1,module.getText());
                pst.setString(2,coef.getText());
                pst.setString(3,typemodule);
                pst.setString(4,mpromo.getSelectionModel().getSelectedItem().toString());
                pst.setString(5,semestre.getSelectionModel().getSelectedItem().toString());
                pst.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                error.setText("Module existe deja !! ");
                error.setStyle("-fx-text-fill: #ed0202;");
            }
            mpromo.getItems().clear();
            mpromo.getItems().addAll("1CPI","2CPI","1CS","2CS","3CS");
            semestre.getItems().clear();
            semestre.getItems().addAll("1","2");
            module.clear();
            coef.clear();
            typemodule = "";
            cours.setSelected(false);
            TD.setSelected(false);
            TP.setSelected(false);

        } else {
            error.setText("Il y'a quelque chose qui manque !! ");
            error.setStyle("-fx-text-fill: #ed0202;");
        }

        
    }

    public void Vider(ActionEvent actionEvent) {
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
