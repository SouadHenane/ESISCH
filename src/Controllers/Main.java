package Controllers;

import DataBaseConnectors.ConnectionDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main extends Application  {

    PreparedStatement pst ;
    ResultSet rs ;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Connection con  = ConnectionDB.CDB() ;
        try {
            String qr = "select count(*) from mydb.Admin";
            pst=con.prepareStatement(qr);
            rs=pst.executeQuery();
            Parent root ;
            if(rs.next()) {
                if (Integer.valueOf(rs.getString("count(*)"))>=1){
                    root = FXMLLoader.load(getClass().getResource("/FXML/LOGIN.fxml"));
                    primaryStage.setScene(new Scene(root, 350, 450));
                } else {
                    root = FXMLLoader.load(getClass().getResource("/FXML/signup.fxml"));
                    primaryStage.setScene(new Scene(root, 350, 520));
                }

                primaryStage.initStyle(StageStyle.UNDECORATED);
                primaryStage.show();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }


    public static void main(String[] args) {
        launch(args);
    }
}
