package Controllers;

import DataBaseConnectors.ConnectionDB;
import DataBaseConnectors.Tableviewdb;
import animations.FadeInLeftTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import static Controllers.GRHcontroller.obj;

public class Affectation implements Initializable {
    public Label desc;
    public AnchorPane AP1;
    public AnchorPane AP3;
    public AnchorPane AP2;
    public Label Addens;
    public Label Delens;
    public Label Addcc;
    public Label Delcc;
    public Label error;
    public AnchorPane AP;
    Connection con ;
    PreparedStatement pst ;
    ResultSet rs ;
    Statement stmt ;
    TableView table,table1,table2 ;
    public ArrayList<String> info = new ArrayList<String>();

    public void animate(Node node ){
        FadeInLeftTransition fade = new FadeInLeftTransition(node);
        fade.play();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /******************
         * charge the label
         */
        animate(AP);


        info.addAll((Collection<? extends String>) obj);
        desc.setText("Affectation des profs pour le module :"+info.get(1));

        /*String finalize ="DROP TABLE IF EXISTS  `mydb`.`tmpcc`, `mydb`.`tmpens`";
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(finalize);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        /****************************
         * create the tomparary table in SQL
         */
        con = ConnectionDB.CDB();
        String CreateQuery = "CREATE table IF NOT EXISTS tmpens(\n" +
                "\tMatricule VARCHAR(50) NOT NULL unique , \n" +
                "    Nom_Prenom VARCHAR(50) NOT NULL )";
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(CreateQuery);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        String CreateQuery2 = "CREATE table IF NOT EXISTS tmpcc(\n" +
                "\tMatricule VARCHAR(50) NOT NULL unique , \n" +
                "    Nom_Prenom VARCHAR(50) NOT NULL )";
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(CreateQuery2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PopulateAP(AP1,table,"select ID_Enseignant AS Matricule , concat(Nom_Enseigant,\" \",Prenom_Enseignant) as Nom_Prenom from Enseignant ");
        PopulateAP(AP2,table1,"select * from tmpens");
        PopulateAP(AP3,table2,"select * from tmpcc");
        // the first add button
        Node rootIcon2 = new ImageView(new Image(Main.class.getResourceAsStream("/img/Add")));
        Addens.setGraphic(rootIcon2);
        Addens.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object o = ((TableView) AP1.getChildren().get(0)).getSelectionModel().getSelectedItem();
                ArrayList<String> l1 = new ArrayList<String>();
                l1.addAll((Collection<? extends String>) o);
                String Addq = "INSERT INTO tmpens values (?,?)";

                try {
                    pst = con.prepareStatement(Addq);
                    pst.setString(1,l1.get(0));
                    pst.setString(2,l1.get(1));
                    pst.execute();
                    error.setText("");
                } catch (SQLException e) {
                    e.printStackTrace();
                    error.setText("Ca existe deja !!");
                    error.setStyle("-fx-text-fill: #ed0202;");
                }

                PopulateAP(AP2,table1,"select * from tmpens");
            }
        });
        // Delete enseignant
        Delens.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/img/Del"))));
        Delens.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                error.setText("");
                Object o = ((TableView) AP2.getChildren().get(0)).getSelectionModel().getSelectedItem();
                ArrayList<String> l1 = new ArrayList<String>();
                l1.addAll((Collection<? extends String>) o);
                System.out.println("++++++++++++++"+o.toString());
                String Delq = "Delete from tmpens where Matricule= '"+l1.get(0)+"'";
                System.out.println(Delq);
                try {
                    pst = con.prepareStatement(Delq);
                    pst.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                PopulateAP(AP2,table1,"select * from tmpens");

            }
        });
        // Charge de cours

        Addcc.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/img/Add"))));
        Addcc.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object o = ((TableView) AP2.getChildren().get(0)).getSelectionModel().getSelectedItem();
                ArrayList<String> l1 = new ArrayList<String>();
                l1.addAll((Collection<? extends String>) o);
                String Addq = "INSERT INTO tmpcc values (?,?)";
                try {
                    pst = con.prepareStatement(Addq);
                    pst.setString(1,l1.get(0));
                    pst.setString(2,l1.get(1));
                    pst.execute();
                    error.setText("");
                } catch (SQLException e) {
                    e.printStackTrace();
                    error.setText("Ca existe deja !!");
                    error.setStyle("-fx-text-fill: #ed0202;");
                }

                PopulateAP(AP3,table2,"select * from tmpcc");
            }
        });
        // Delete chaer c
        Delcc.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/img/Del"))));
        Delcc.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object o = ((TableView) AP3.getChildren().get(0)).getSelectionModel().getSelectedItem();
                ArrayList<String> l1 = new ArrayList<String>();
                l1.addAll((Collection<? extends String>) o);
                System.out.println("++++++++++++++"+o.toString());
                String Delq = "Delete from tmpcc where Matricule= '"+l1.get(0)+"'";
                System.out.println(Delq);
                try {
                    pst = con.prepareStatement(Delq);
                    pst.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                PopulateAP(AP3,table2,"select * from tmpcc");
            }
        });


    }

    public void PopulateAP(AnchorPane A , TableView T , String Sql ){
        Tableviewdb tdb = new Tableviewdb(200,300);
        tdb.populate(Sql);
        T = tdb.getTable();
        T.setColumnResizePolicy(T.CONSTRAINED_RESIZE_POLICY);
        A.getChildren().clear();
        A.getChildren().addAll(T);
    }









    public void Valider(ActionEvent actionEvent) throws SQLException {

        String q  = "Select Matricule from tmpens";
        pst = con.prepareStatement(q);
        rs = pst.executeQuery();
        while (rs.next()){
            String insert = "INSERT INTO Module_Enseignat(ID_Enseignant,ID_Module) values ('"+rs.getString(1)+"',"+"'"+info.get(0)+"')";
            pst = con.prepareStatement(insert);
            pst.execute();
        }
        String q2 = "Select Matricule from tmpcc";
        pst = con.prepareStatement(q2);
        rs = pst.executeQuery();
        while(rs.next()){
            String trueq = "Update Module_Enseignat SET ChargéCours = TRUE WHERE ID_Enseignant ='"+rs.getString(1)+"'";
            pst = con.prepareStatement(trueq);
            pst.execute();
        }
        String finalize ="DROP TABLE `mydb`.`tmpcc`, `mydb`.`tmpens`";
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(finalize);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        AP.getChildren().clear();
        Label label = new Label();
        label.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("/img/Opdone"))));
        AP.getChildren().addAll(label);
        label.setLayoutY(175);
        label.setLayoutX(190);
    }

    public void Vider(ActionEvent actionEvent) throws SQLException {
        String delq1 = "delete from tmpens";
        String delq2 = "delete from tmpcc";
        pst = con.prepareStatement(delq1);
        PreparedStatement pst2 = con.prepareStatement(delq2);
        pst2.execute();
        pst.execute();
        PopulateAP(AP2,table1,"select * from tmpens");
        PopulateAP(AP3,table2,"select * from tmpcc");
    }
}

