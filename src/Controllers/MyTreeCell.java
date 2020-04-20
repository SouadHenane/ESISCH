package Controllers;

import DataBaseConnectors.ConnectionDB;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Controllers.GRHcontroller.promo2;
import static Controllers.GRHcontroller.section;

class MyTreeCell extends TextFieldTreeCell<String> {
    private ContextMenu rootContextMenu;


    public MyTreeCell() throws IOException {
        //Create Menu item of Delete
        Object delitem = new MenuItem() ;
        // instantiate the root context menu
        rootContextMenu =
                ContextMenuBuilder.create()
                        .items(new javafx.scene.control.MenuItem[]{MenuItemBuilder.create()
                                .text("Ajouter Section")
                                .onAction(
                                        new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent arg0) {
                                                try {
                                                    Parent root = FXMLLoader.load(getClass().getResource("/FXML/popup.fxml"));
                                                    Stage dialog = new Stage();
                                                    Scene scene = new Scene(root, 380, 180);
                                                    dialog.initStyle(StageStyle.UNDECORATED);
                                                    dialog.setScene(scene);
                                                    dialog.setTitle("Add section");
                                                    dialog.show();
                                                } catch (Exception e) {
                                                    System.out.println("This is the message ");
                                                    System.out.println(e.getMessage());
                                                }
                                            }
                                        }
                                )
                                .build()})
                        .build();
            MenuItem delete = new MenuItem("Supprimer");
            rootContextMenu.getItems().add(delete);
            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String q = "delete from Section where Nom_Section="+"'"+ section+"'"+"and Promo ="+"'"+promo2+"'";
                    Connection con = ConnectionDB.CDB();
                    try {
                        PreparedStatement pst = con.prepareStatement(q);
                        pst.execute();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            });



    }

    @Override
    public void updateItem(String item , boolean empty) {
        super.updateItem(item, empty);


        if (!empty && getTreeItem().getParent() != null && !(getTreeItem().getValue().toString() == "Enseignants") &&  !(getTreeItem().getValue().toString() == "Salles"))
        {

            setContextMenu(rootContextMenu);
        }
    }
}