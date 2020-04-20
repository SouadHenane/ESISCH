package DataBaseConnectors;

import DataBaseConnectors.ConnectionDB;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.*;

public class Tableviewdb {
    TableView table  ;
    private ObservableList<ObservableList> data;
    Connection con ;
    PreparedStatement pst ;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Tableviewdb (double x , double y){
        table = new TableView();
        table.setPrefWidth(x);
        table.setPrefHeight(y);
        System.out.println("create the table");
    }

    public void populate(String Query){
        data = FXCollections.observableArrayList();
        try{
            con = ConnectionDB.CDB();
            String SQL = Query;
            //ResultSet
            ResultSet rs = con.createStatement().executeQuery(SQL);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                table.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row );
                data.add(row);

            }

            // ADD TO THE TABLEVIEW
            table.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public TableView getTable() {
        return table;
    }

    public void setTable(TableView table) {
        this.table = table;
    }
}
