package Controllers;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FirstMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/first.fxml"));
        primaryStage.setTitle("ESI-SCHEDULER");

        primaryStage.setResizable(false);
        Scene scene = new Scene(root, 650, 500);
        //scene.getStylesheets().addAll(this.getClass().getResource("/sources/pane.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}