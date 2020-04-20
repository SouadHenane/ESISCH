package sortie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import emploiDuTemps.Main2;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import sun.font.StrikeCache;
 
public class Affichage extends Application {

    private TableView<List<String>> table ;
    public static void main() {
    	Stage stage = new Stage();
    	
    }
 
    @Override
    public void start(Stage stage) {
    	table = new TableView();
        Scene scene = new Scene(new Group());
        scene.getStylesheets().add("../CSS/Tableview.css");
        stage.setTitle("Table View Sample");
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
 
        final Label label = new Label("Emploi du temps");
        label.setAlignment(Pos.CENTER);
        label.setFont(new Font("Arial", 20));
        
  
        
        String [] columnNames = new String[6];
        columnNames[0]="Heur";
        columnNames[1]="Dimanche";
        columnNames[2]="Lundi";
        columnNames[3]="Mardi";
        columnNames[4]="Mercredi";
        columnNames[5]="Jeudi";
        //columnNames[6]="Vendredi";
       // columnNames[7]="Samedi";
        
        for (int i = 0; i < columnNames.length; i++) {

            TableColumn<List<String>, String> column = new TableColumn<>(columnNames[i]);

            final int colIndex = i ;
            column.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().get(colIndex)));
            if (i==0)
            column.setMinWidth(100);
            else if (i==6 || i==7) column.setMinWidth(100);
            else column.setMinWidth(200);
            table.getColumns().add(column);

        }
        populate();
    	
      //	table.setEditable(true);

       int k =1; 

    		
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }
    
    public void populate (){
   

        List<List<String>> data = new ArrayList<List<String>>();

        for (int i = 0; i < 8; i++) {
            List<String> row = new ArrayList<String>();
            for (int j = 0; j < 5; j++) {
            
           //     row.add(Main2.tab[j][i]);

            }

            data.add(row);

        }

        ObservableList<List<String>> inpData = FXCollections.observableArrayList(data);



        table.setItems(inpData); 
       
        
       // table.setFixedCellSize(80);
    }
    
 
}