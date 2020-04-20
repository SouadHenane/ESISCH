package Controllers;

import DataBaseConnectors.ConnectionDB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Controllers.GRHcontroller.selectedID2;

public class Settings implements Initializable {
    Connection con ;
    PreparedStatement pst ;
    ResultSet rs ;

    public Label error;
    public Label LOGO;
    public Label exit;
    public Label mini;
    public TextField email;
    public TextField user;
    public PasswordField pwd1;
    public PasswordField pwdc;
    String key ;

    String txt ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exit.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/img/exit"))));
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((Node)  (event.getSource())).getScene().getWindow().hide();
            }
        });
        mini.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/img/minimize"))));
        mini.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage)((Label)event.getSource()).getScene().getWindow();
                stage.setIconified(true);
            }
        });
        LOGO.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/img/LOGIN"))));
        try {
            user.setText(Get("Fullname"));
            email.setText(Get("Email"));
            pwd1.setText("Password");
            pwdc.setText("Password");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public String Get (String thiis) throws SQLException {
        String Query = "Select "+thiis+" from Admin where idAdmin= '"+1+"'";
        con = ConnectionDB.CDB();
        pst = con.prepareStatement(Query);
        rs = pst.executeQuery();
        if (rs.next()){
            txt =  rs.getString(1).toString();
        }
        return txt ;
    }

    public void Valider(ActionEvent event) throws SQLException {
        con = ConnectionDB.CDB();
        String Query = "update Admin set Fullname=?,Email=?,Password=? where idAdmin='1' ";
        if (!user.getText().trim().isEmpty() && !email.getText().trim().isEmpty() && !pwd1.getText().trim().isEmpty()){
            pst = con.prepareStatement(Query);
            pst.setString(1,user.getText().toString());
            pst.setString(2,email.getText().toString());
            pst.setString(3,pwd1.getText().toString());
            if(pwd1.getText().equals(pwdc.getText())){
                pst.execute();
                ((Node)  (event.getSource())).getScene().getWindow().hide();
            } else {
                error.setText("Mots de passe non identique!");
                error.setStyle("-fx-text-fill: #ed0202;");
            }
        } else {
            error.setText("Il y'a un champ qui manque!");
            error.setStyle("-fx-text-fill: #ed0202;");
        }
    }
}
