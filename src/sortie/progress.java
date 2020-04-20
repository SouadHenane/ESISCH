package sortie;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class progress {

	
	public progress(){
		
        ProgressIndicator progressIndicator = new ProgressIndicator();
        Stage stage1 = new Stage();
        HBox h = new HBox();
        h.getChildren().add(progressIndicator);
        h.setAlignment(Pos.CENTER);
        FlowPane root1 = new FlowPane();
        root1.setPadding(new Insets(10));
        root1.setHgap(10);
        root1.getChildren().addAll( h);
 
        Scene scene1 = new Scene(root1, 400, 300);
 
        stage1.setTitle("JavaFX ProgressBar");
 
        stage1.setScene(scene1);
        stage1.show();
	}
}
