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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class firstlog implements Initializable {
    public Label error;
    public Label LOGO;
    public Label exit;
    public Label mini;
    public TextField email;
    public TextField user;
    public PasswordField pwd1;
    public PasswordField pwdc;

    Connection con ;
    PreparedStatement pst ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    public void loadpage(ActionEvent event) throws IOException {
        ((Node)  (event.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/FXML/redirect.fxml"));
        Stage stage = new Stage() ;
        Scene scence = new Scene(parent);
        stage.setScene(scence);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public void Valider(ActionEvent event) throws SQLException, IOException {
        con = ConnectionDB.CDB();
        String Query = "INSERT INTO Admin (Fullname,Email,Password) values (?,?,?)" ;
        if (!user.getText().trim().isEmpty() && !email.getText().trim().isEmpty() && !pwd1.getText().trim().isEmpty()){
            pst = con.prepareStatement(Query);
            pst.setString(1,user.getText().toString());
            pst.setString(2,email.getText().toString());
            pst.setString(3,pwd1.getText().toString());
            if(pwd1.getText().equals(pwdc.getText())){
                pst.execute();
                loadpage(event);
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
