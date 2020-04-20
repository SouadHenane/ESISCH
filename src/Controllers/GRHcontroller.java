package Controllers;

import DataBaseConnectors.ConnectionDB;
import DataBaseConnectors.Tableviewdb;
import javafx.application.Platform;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ResourceBundle;

public class GRHcontroller implements Initializable {
    @FXML
    public TreeView <String> tree;
    public static String promo ;
    public AnchorPane AP;
    public AnchorPane scene;
    public Label back;
    public Label exit;
    public Label mini;
    public Label LOGO;
    Connection con ;
    PreparedStatement pst ;
    ResultSet rs ;
    public TableView enstable ;
    String selecteditem ;
    public static String section,promo2 ;
    public static int groupnbr;
    ArrayList<String> list = new ArrayList<String>(Arrays.asList("1CPI","2CPI","1CS","2CS","3CS"));
    TreeItem TI ;
    public static String selectedID2 ;
    public static Object obj ;




    @Override
    public void initialize (URL location, ResourceBundle resources) {
        back.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/img/back"))));
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((Node)  (event.getSource())).getScene().getWindow().hide();
                Parent parent = null;
                try {
                    parent = FXMLLoader.load(getClass().getResource("/FXML/redirect.fxml"));
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
        /**********************************
         * INIT THE TREEVIEW WITH NECESSARY DATA FROM MYQSL DATABASE  *
         **********************************/
        TreeItem<String> root  = new TreeItem<String>("ESI-SBA");
        root.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/img/school"))));
        treechild(root);
        /**********************************
         * populate the treeview from DataBase  *
         **********************************/
        treepop ();
        /**********************************
         * CONTEXTMENU AND POPUP *
         **********************************/
       tree.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> arg0) {
                try {
                    return new MyTreeCell();

                } catch (IOException e) {
                    e.printStackTrace();
                    return null; 
                }
            }
        });

        /**********************************
         * SELECTED ITEM FROM TREEVIEW AND CHAGING SCENCE*
         **********************************/
        tree.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TI= tree.getSelectionModel().getSelectedItem();
                selecteditem = tree.getSelectionModel().getSelectedItem().getValue().toString();
                promo = selecteditem ;
                if (selecteditem.equals("Enseignants")){
                    Tableviewdb table = new Tableviewdb(650,450);
                    table.populate("select ID_Enseignant,Nom_Enseigant,Prenom_Enseignant,Email_Ensignant from Enseignant");
                    enstable = table.getTable();
                    enstable.setColumnResizePolicy(enstable.CONSTRAINED_RESIZE_POLICY);
                    AP.getChildren().clear();
                    AP.getChildren().add(enstable);
                    enstable.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (event.getClickCount()==2){
                                obj = enstable.getItems().get(enstable.getSelectionModel().getSelectedIndex());
                                Loadpage("../FXML/dispo.fxml");
                            }
                        }
                    });
                } else if(selecteditem.equals("Salles")){
                   Tableviewdb table = new Tableviewdb(650,450);
                    table.populate("SELECT * from Salle");
                    enstable = table.getTable();
                    enstable.setColumnResizePolicy(enstable.CONSTRAINED_RESIZE_POLICY);
                    AP.getChildren().clear();
                    AP.getChildren().add(enstable);
                } else if (!(TI.getParent().getValue().toString().equals("ESI-SBA")) && (!(list.contains(TI.getValue().toString())))){
                    String Query  = "select ID_Etudiant,Nom_Etudiant,Prenom_Etudiant,Email_Etudiant from Etudiant natural join Groupe natural join Section where Promo='"+TI.getParent().getValue().toString()+"'"+"and Nom_Section='"+TI.getValue().toString().substring(TI.getValue().toString().length()-1)+"'";
                    System.out.println("this is the query :"+Query);
                    Tableviewdb table = new Tableviewdb(650,450);
                    table.populate(Query);
                    enstable = table.getTable();
                    enstable.setColumnResizePolicy(enstable.CONSTRAINED_RESIZE_POLICY);
                    AP.getChildren().clear();
                    AP.getChildren().addAll(enstable);
                    String q = "select count(*) from Section natural join Groupe where Promo =? and Nom_Section=?";
                    try {
                        con = ConnectionDB.CDB();
                        pst = con.prepareStatement(q);
                        promo2 = TI.getParent().getValue().toString();
                        pst.setString(1,TI.getParent().getValue().toString());
                        String s = TI.getValue().toString();
                        s = s.substring(s.length()-1);
                        section=s ;
                        pst.setString(2,s);
                        rs = pst.executeQuery();
                            while (rs.next()){

                                groupnbr = Integer.parseInt(rs.getString(1));
                            }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else if (list.contains(TI.getValue().toString())){
                    Tableviewdb table = new Tableviewdb(650,450);
                    table.populate("select ID_Module,Nom_Module,Coefficient,Type,Semestre from Module where Promo = '"+TI.getValue().toString()+"'");
                    enstable = table.getTable();
                    enstable.setColumnResizePolicy(enstable.CONSTRAINED_RESIZE_POLICY);
                    AP.getChildren().clear();
                    AP.getChildren().addAll(enstable);
                    enstable.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (event.getClickCount()==2){
                                obj = enstable.getItems().get(enstable.getSelectionModel().getSelectedIndex());
                                Loadpage("../FXML/Affectation.fxml");
                            }
                        }
                    });
                }

            }

        });





    }





    /***************************************
     * Methode witch populate the treeview
     */
    public void treepop(){
        poptree t = new poptree();
        t.setPtree(tree);
        t.populate();
        tree = t.getPtree();
    }


    public void treechild (TreeItem<String> root ){
        TreeItem <String> CP1  = new TreeItem<>("1CPI");
        TreeItem <String> CP2  = new TreeItem<>("2CPI");
        TreeItem <String> CS1  = new TreeItem<>("1CS");
        TreeItem <String> CS2  = new TreeItem<>("2CS");
        TreeItem <String> CS3  = new TreeItem<>("3CS");
        TreeItem <String> prof = new TreeItem<>("Enseignants");
        TreeItem <String> Room = new TreeItem<>("Salles");
        root.getChildren().setAll(CP1,CP2,CS1,CS2,CS3,prof,Room);
        root.setExpanded(true);
        tree.setRoot(root);
    }


    public void Loadpage(String fxml){
        try {

            URL url = getClass().getResource(fxml);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(url);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            AnchorPane page = (AnchorPane) fxmlLoader.load(url.openStream());

            AP.getChildren().clear();
            AP.getChildren().add(page);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void Cancel(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void Add(ActionEvent actionEvent) {
        if (promo.equals("Enseignants")){
            Loadpage("../FXML/AddProf.fxml");
        } else if (promo.equals("Salles")){
            Loadpage("../FXML/AddSalle.fxml");
        } else if (!(list.contains(promo))){
            Loadpage("../FXML/AddStudent.fxml");
        } else if(list.contains(promo)){
            Loadpage("../FXML/Addunit.fxml");
        }

    }

    /********************************************
     * METHOD USED TO DELETE FROM THE DATABASE
     * and REPOPULATE THE TABLEVIEW
     * @param Delq
     * @param repopulate
     * @throws SQLException
     ********************************************/
    public void delete (String Delq,String repopulate) throws SQLException {
        Object obj = enstable.getItems().get(enstable.getSelectionModel().getSelectedIndex());
        ArrayList<String> l = new ArrayList<String>();
        l.addAll((Collection<? extends String>) obj);
        String selectedID = l.get(0).toString();
        con = ConnectionDB.CDB();
        String Query  = Delq+"'"+selectedID+"'" ;
        pst = con.prepareStatement(Query);
        pst.execute();
        Tableviewdb table = new Tableviewdb(650,450);
        table.populate(repopulate);
        enstable = table.getTable();
        enstable.setColumnResizePolicy(enstable.CONSTRAINED_RESIZE_POLICY);
        AP.getChildren().clear();
        AP.getChildren().add(enstable);
    }


    public void delete(ActionEvent actionEvent) throws SQLException {
        if (selecteditem.equals("Enseignants")){
            delete("delete from Enseignant where ID_Enseignant= ","select ID_Enseignant,Nom_Enseigant,Prenom_Enseignant,Email_Ensignant from Enseignant; ");
        }  else if (selecteditem.equals("Salles")){
            delete("delete from Salle where ID_Salle= ","Select * from Salle");
        } else if (!(TI.getParent().getValue().toString().equals("ESI-SBA")) && (!(list.contains(TI.getValue().toString())))){
            delete("delete from Etudiant where ID_Etudiant= ","select ID_Etudiant,Nom_Etudiant,Prenom_Etudiant,Email_Etudiant from Etudiant natural join Section where Promo='"+TI.getParent().getValue().toString()+"'"+"and Nom_Section='"+TI.getValue().toString().substring(TI.getValue().toString().length()-1)+"'");
        } else if ( list.contains(TI.getValue().toString())){
            delete("delete from Module where ID_Module =","select ID_Module,Nom_Module,Coefficient,Type,Semestre from Module where Promo = '"+TI.getValue().toString()+"'");
        }
    }

    public void Update (String URL){
        Object obj = enstable.getItems().get(enstable.getSelectionModel().getSelectedIndex());
        System.out.println("This is the selected row : "+obj);
        ArrayList<String> l = new ArrayList<String>();
        l.addAll((Collection<? extends String>) obj);
        System.out.println(l.get(0));
        selectedID2 = l.get(0);
        Loadpage(URL);
    }

    public void update(ActionEvent actionEvent) {
        if(selecteditem.equals("Enseignants")){
            Update("../FXML/Uprof.fxml");
        } else if (selecteditem.equals("Salles")){
            Update("../FXML/upsalle.fxml");
        }else if (list.contains(TI.getValue().toString())){
            Update("../FXML/upunit.fxml");
        } else if (!(TI.getParent().getValue().toString().equals("ESI-SBA")) && (!(list.contains(TI.getValue().toString())))) {
            Update("../FXML/upstudent.fxml");
        }
    }

    public void Refresh(ActionEvent actionEvent) {
        tree.getRoot().getChildren().clear();
        treechild(tree.getRoot());
        treepop();
        AP.getChildren().clear();
    }

    public void Schedule(ActionEvent actionEvent) {
        if (list.contains(TI.getValue().toString())) {
            Loadpage("/FXML/schdule.fxml");
        }
    }
}
