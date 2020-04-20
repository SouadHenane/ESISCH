package Controllers;

import DataBaseConnectors.ConnectionDB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LOGINcontroller implements Initializable {
    // Declaration des variables
    public TextField Email;
    public PasswordField pwd;
    public Label mini;
    public Label exit;
    public Label LOGO;
    public Label pwdf;
    public Button logbutton;
    public Label error;
    Connection con;
    PreparedStatement pst ;
    ResultSet rs ;


    public void loginn(ActionEvent event){
        try {
            String qr = "SELECT email,Password FROM Admin where email=? and Password=?" ;
            pst = con.prepareStatement(qr);
            pst.setString(1,Email.getText());
            pst.setString(2,pwd.getText());
            rs=pst.executeQuery();
            if(rs.next()) {
                ((Node)  (event.getSource())).getScene().getWindow().hide();
                Parent parent = FXMLLoader.load(getClass().getResource("/FXML/redirect.fxml"));
                Stage stage = new Stage() ;
                Scene scence = new Scene(parent);
                stage.setScene(scence);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            }else {
                // Adding ERROR MESSAGES OF INVALID email OR password
                error.setText("Login ou mot de passe incorrecte !");
                error.setStyle("-fx-text-fill: #ed0202;");

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }




    public void Logm(ActionEvent actionEvent) {
        loginn(actionEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = ConnectionDB.CDB();
        exit.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/img/exit"))));
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Platform.exit();
                System.exit(0);
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

    }


    public void login(ActionEvent actionEvent) {
        loginn(actionEvent);
    }
}
