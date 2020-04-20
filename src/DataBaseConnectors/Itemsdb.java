package DataBaseConnectors;

import DataBaseConnectors.ConnectionDB;
import javafx.scene.control.TreeItem;
import java.sql.*;
import java.util.ArrayList;

public class Itemsdb  {
    Connection con ;
    PreparedStatement pst ;
    ResultSet rs ;
    TreeItem<String> rootitem ;
    ArrayList<String> data = new ArrayList<>();

    public Itemsdb(){con= ConnectionDB.CDB();}


    public void additems () throws SQLException {
        String query = "SELECT Nom_Section from Section where Promo = ?";
        pst = con.prepareStatement(query);
        pst.setString(1,rootitem.getValue().toString());
        rs = pst.executeQuery();
        while (rs.next()){
            System.out.println(rs.getString(1));
            data.add(rs.getString(1));
        }
            for (int i = 0; i < data.size() ; i++ ){
                TreeItem<String> item = new TreeItem<>("Section "+data.get(i));
                rootitem.getChildren().add(item);
            }

    }



    public TreeItem<String> getrootitem() {
        return rootitem;
    }

    public void setrootitem(TreeItem<String> rrootitem) {
        rootitem = rrootitem;
    }


}
