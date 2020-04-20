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
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class first implements Initializable {
    public ListView List;
    public Label LOGO;
    public Label mini;
    public Label exit;
    public Label back;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List.getItems().addAll("1CPI","2CPI","1CS","2CS","3CS");
        back.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/img/back"))));
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((Node)  (event.getSource())).getScene().getWindow().hide();
                Parent parent = null;
                try {
                    parent = FXMLLoader.load(getClass().getResource("../FXML/redirect.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage() ;
                Scene scence = new Scene(parent);
                stage.setScene(scence);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            }
        });
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
        LOGO.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/img/logo"))));


    }

    public void Valider(ActionEvent actionEvent) {
    }
}
