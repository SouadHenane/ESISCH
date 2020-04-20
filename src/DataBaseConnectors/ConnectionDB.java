package DataBaseConnectors;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class ConnectionDB {
    public static Connection CDB(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url ="jdbc:mysql://localhost:3306/mydb";
            String user = "root";
            String pass = "da250895";
            Connection con = DriverManager.getConnection(url,user,"");
            return con ;
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("no connection");
            return null ;

    }

    }
}