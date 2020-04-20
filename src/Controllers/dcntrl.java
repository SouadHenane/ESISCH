package Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class dcntrl implements Initializable {

    public Label LOGO;
    public Label exit;
    public Label mini;

    public void Gesres(ActionEvent actionEvent) throws Exception {
        ((Node)  (actionEvent.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/FXML/GRH.fxml"));
        Stage stage = new Stage() ;
        Scene scence = new Scene(parent);
        stage.setScene(scence);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public void Schedule(ActionEvent actionEvent) throws IOException {
        ((Node)  (actionEvent.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/FXML/first.fxml"));
        Stage stage = new Stage() ;
        Scene scence = new Scene(parent);
        stage.setScene(scence);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

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

    public void Parametre(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/FXML/settings.fxml"));
        Stage stage = new Stage() ;
        stage.setScene(new Scene(parent, 350, 520));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

    }
}
