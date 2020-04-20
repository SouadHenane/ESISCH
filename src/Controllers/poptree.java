package Controllers;

import DataBaseConnectors.Itemsdb;
import com.jfoenix.controls.JFXTreeView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.sql.SQLException;


public class poptree {
   TreeView<String> ptree ;

    poptree(){}
    public void populate (){
        for (int i =0 ; i < 5 ; i++){
            Itemsdb item = new Itemsdb();
            TreeItem<String> getitem = ptree.getTreeItem(i+1);
            item.setrootitem(getitem);
            try {
                item.additems();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            getitem = item.getrootitem();
        }
    }

    public TreeView<String> getPtree() {
        return ptree;
    }

    public void setPtree(TreeView<String> ptree) {
        this.ptree = ptree;
    }
}
