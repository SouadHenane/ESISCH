package Controllers;



import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Controllers.Tableviewdb;

import emploiDuTemps.Main2;
import emploiDuTemps.input;

import entites.Section;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class first implements Initializable {
	 private TableView<List<String>> table ;
	public AnchorPane firstanchor;
	public CheckBox Di;
	public CheckBox Lu;
	public CheckBox Ma;
	public CheckBox Me;
	public CheckBox Je;
	public CheckBox Ve;
	public CheckBox Sa;
	public Button Valider;
	public ListView<String> List;
	public ComboBox<String> sem;
	public TableView tableView;
	   public Label LOGO;
	    public Label mini;
	    public Label exit;
	    public Label back;
	

	public static ArrayList<String> jours = new ArrayList<String>();
	public static String pro;
	public static String se;
	public static ArrayList<String> salleID = new ArrayList<String>();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
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


    


		// TODO Auto-generated method stub
		Tableviewdb table = new Tableviewdb();
		table.populate("Select * from Salle");
		 tableView = table.getTable();
		 tableView.setLayoutX(421.0);
		 tableView.setLayoutY(146.0);
		 tableView.getSelectionModel().setSelectionMode(
				    SelectionMode.MULTIPLE
				);
		 tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent event) {
			        ObservableList<ObservableList<String>> selectedItems =  tableView.getSelectionModel().getSelectedItems();
			        	//System.out.println(selectedItems);
			        
			        for (ObservableList<String> obs: selectedItems){
			        	salleID.add(obs.get(0));
			        }

			    }
			});
		 



         //firstanchor.getChildren().clear();
        firstanchor.getChildren().add(tableView);
		List.getItems().addAll("1CPI","2CPI","1CS","2CS","3CS");
		sem.getItems().addAll("1","2");
		
	}
	
	public void Valider (ActionEvent actionEvent) throws SQLException, IOException{
		
		boolean sucess=false;
		boolean sucess1=false;
		boolean sucess2=false;
	  	if (Di.isSelected()){
	  		jours.add("Dimanche");
	  		sucess = true;
	  	}
    	if (Lu.isSelected()){
    		jours.add("Lundi");
    		sucess = true;
    	}
    	if (Ma.isSelected()){
    		jours.add("Mardi");
    		sucess = true;
    	}
    	if (Me.isSelected()){
    		jours.add("Mercredi");
    		sucess = true;
    	}
    	if (Je.isSelected()){
    		jours.add("Jeudi");
    		sucess = true;
    	}
    	if (Ve.isSelected()){
    		jours.add("Vendredi");
    		sucess = true;
    	}
    	if (Sa.isSelected()){
    		jours.add("Samedi");
    		sucess = true;
    	}
    	try{
    		if (!sucess) throw new Exception();
    	}catch(Exception e1) {
    		 Alert dialogE = new Alert(AlertType.ERROR);
           	 dialogE.setTitle("Erreur");
           	 dialogE.setContentText("Error : Selectionnez au moins une journée !");
           	 dialogE.showAndWait();
           	 
           	 }
    	
    	pro = List.getSelectionModel().getSelectedItem();
    	if (pro!=null) sucess1=true;
    	try{
    		if (!sucess1) throw new Exception();
    	}catch(Exception e1) {
    		 Alert dialogE = new Alert(AlertType.ERROR);
           	 dialogE.setTitle("Erreur");
           	 dialogE.setContentText("Error : Selectionnez une promo !");
           	 dialogE.showAndWait();
           	 
           	 }
    	se = sem.getSelectionModel().getSelectedItem();
    	if (se!=null) sucess2=true;
    	try{
    		if (!sucess2) throw new Exception();
    	}catch(Exception e1) {
    		 Alert dialogE = new Alert(AlertType.ERROR);
           	 dialogE.setTitle("Erreur");
           	 dialogE.setContentText("Error : Selectionnez un semestre !");
           	 dialogE.showAndWait();
           	 
           	 }
    	if (sucess && sucess1 && sucess2){
    		firstanchor.setDisable(true);
    	//((Node)  (actionEvent.getSource())).getScene().getWindow().hide();
    	 
       /* Parent parent = FXMLLoader.load(getClass().getResource("/FXML/pb.fxml"));
         Stage stage = new Stage() ;
         Scene scence = new Scene(parent);
         stage.setScene(scence);
         stage.initStyle(StageStyle.UNDECORATED);
         stage.show();
    	*/
         
         
    
    	Main2.main();
    
       
    	 
    	
	}}
    public void populate (TableView table, String[][] tab){
        

        List<List<String>> data = new ArrayList<List<String>>();
        for (int j = 0; j < 9; j++) {
        	  List<String> row = new ArrayList<String>();
        	 
        for (int i = 0; i < 8; i++) {
          
            

                row.add(tab[i][j]);
              
            }

            data.add(row);
           

        }

        ObservableList<List<String>> inpData = FXCollections.observableArrayList(data);



        table.setItems(inpData); 
       
        
        table.setFixedCellSize(70);
	}
	
    public void showTable(){

		Stage stage = new Stage();
    	
    	
    	Group root = new Group();
        Scene scene = new Scene(root);
        TabPane tabPane = new TabPane();

        BorderPane borderPane = new BorderPane();
        String css = this.getClass().getResource("/CSS/emploi.css").toExternalForm();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(css);
       // scene.getStylesheets().add("Tableview.css");
        stage.setTitle("Table View Sample");
        //stage.setWidth(2000);
        stage.setHeight(1000);
        

        
        final Label label1 = new Label("Ecole supérieure en informatique");
        label1.setFont(new Font("Arial", 20));
 
        final Label label = new Label("Emploi du temps Semestre :"+first.se );
        label.setFont(new Font("Arial", 20));
        
        //************** Séparation
        int k=0;
        
        for (Section s : input.sections){
        	table = new TableView();
        	table.setPrefHeight(600);
        	
            String [] columnNames = new String[8];
            columnNames[0]="Heur";
            columnNames[1]="Dimanche";
            columnNames[2]="Lundi";
            columnNames[3]="Mardi";
            columnNames[4]="Mercredi";
            columnNames[5]="Jeudi";
            columnNames[6]="Vendredi";
            columnNames[7]="Samedi";
            
            for (int i = 0; i < columnNames.length; i++) {

                TableColumn<List<String>, String> column = new TableColumn<>(columnNames[i]);

                final int colIndex = i ;
                column.setCellValueFactory(cellData -> 
                    new SimpleStringProperty(cellData.getValue().get(colIndex)));
                if (i==0)
                    column.setMinWidth(50);
                    else if (i==6 || i==7) column.setMinWidth(100);
                    else column.setMinWidth(200);
                table.getColumns().add(column);

            }
        	
        populate(table,(String[][]) Main2.sep.get(k++));
        
        Tab tab = new Tab();
        tab.setText("Section "+s.getNom());
        HBox hbox = new HBox();
        hbox.getChildren().addAll(table);
       hbox.setAlignment(Pos.CENTER);
        tab.setContent(hbox);
        tabPane.getTabs().add(tab);
        }
  
        

        

        
        
        
        

    	
      //	table.setEditable(true);

  

 
        
    	borderPane.setCenter(tabPane);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label1,label, borderPane);
        vbox.setAlignment(Pos.CENTER);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }
    }
	
	


